package com.zeroturnaround.rebelanswers.mvc.controller;

import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.mvc.exceptions.UserStorageErrorException;
import com.zeroturnaround.rebelanswers.mvc.model.RecoveryData;
import com.zeroturnaround.rebelanswers.mvc.model.RegistrationData;
import com.zeroturnaround.rebelanswers.security.UserDetailsWrapper;
import com.zeroturnaround.rebelanswers.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

  private final UserService service;
  private final ConnectionFactoryRegistry connectionRepository;
  private final MailSender mailSender;

  @Autowired
  public LoginController(final UserService service, final ConnectionFactoryRegistry connectionRepository, final MailSender mailSender) {
    this.service = service;
    this.connectionRepository = connectionRepository;
    this.mailSender = mailSender;
  }

  /*
   * Login
   */

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView loginHandler(final ModelMap modelMap, final HttpServletRequest request, final HttpSession session) {
    final ModelAndView mav = new ModelAndView("authentication/login");
    modelMap.addAttribute("registrationData", new RegistrationData());

    final boolean loginError = request.getParameter("login_error") != null;
    if (loginError) {
      mav.addObject("loginError", loginError);
      Exception e = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
      if (e instanceof BadCredentialsException) {
        //noinspection deprecation
        Authentication auth = ((BadCredentialsException) e).getAuthentication();
        Object principal = auth.getPrincipal();
        mav.addObject("lastUser", principal);
      }
    }

    return mav;
  }

  /*
   * Registration
   */

  private void loginUser(User user) {
    UserDetailsWrapper principal = new UserDetailsWrapper(user);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@ModelAttribute @Valid final RegistrationData registrationData, BindingResult result) {
    if (result.hasErrors()) {
      return "authentication/login";
    }
    else {
      User user = new User()
          .email(registrationData.getEmail())
          .name(registrationData.getName())
          .setAndEncodePassword(registrationData.getPassword());
      if (!service.store(user)) {
        throw new UserStorageErrorException(user);
      }

      loginUser(user);
      return "redirect:/";
    }
  }

  /*
   * Facebook connect
   */

  @RequestMapping(value = "/facebook/connect")
  public View facebookConnect() {
    String connected_uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/facebook/connected").build().toUriString();

    FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) connectionRepository.getConnectionFactory("facebook");
    OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
    OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
    oAuth2Parameters.setRedirectUri(connected_uri);
    String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
    return new RedirectView(authorizeUrl);
  }

  @RequestMapping(value = "/facebook/connected")
  public String facebookConnected(@RequestParam final String code) {
    String connected_uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/facebook/connected").build().toUriString();

    FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) connectionRepository.getConnectionFactory("facebook");
    OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
    AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, connected_uri, null);
    Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
    if (connection != null) {
      Facebook facebook = connection.getApi();
      FacebookProfile userProfile = facebook.userOperations().getUserProfile();
      String facebook_id = userProfile.getId();

      User user = service.findByFacebookId(facebook_id);
      if (null == user) {
        user = new User()
            .name(userProfile.getName())
            .facebookId(facebook_id);
        if (!service.store(user)) {
          throw new UserStorageErrorException(user);
        }
      }
      loginUser(user);
    }
    return "redirect:/";
  }

  /*
   * Password recovery
   */

  public ModelAndView getRecoveryModelAndView() {
    return new ModelAndView("authentication/recovery");
  }

  @RequestMapping(value = "/passwordrecovery", method = RequestMethod.GET)
  public ModelAndView recoverPassword() {
    final ModelAndView mav = getRecoveryModelAndView();
    mav.addObject(new RecoveryData());
    return mav;
  }

  @RequestMapping(value = "/passwordrecovery", method = RequestMethod.POST)
  public ModelAndView asked(@ModelAttribute @Valid final RecoveryData recoveryData, final BindingResult result) {
    final ModelAndView mav = getRecoveryModelAndView();
    if (result.hasErrors()) {
      return mav;
    }
    User user = service.findByEmail(recoveryData.getEmail());
    if (null == user) {
      result.addError(new FieldError(RecoveryData.class.getSimpleName(), "email", recoveryData.getEmail(), false, new String[] { "Unknown.recovery.email" }, null, "Unknown email"));
      return mav;
    }
    else {
      String new_password = RandomStringUtils.random(10, true, true);

      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setTo(user.getEmail());
      msg.setReplyTo("noreply@zeroturnaround.com");
      msg.setSubject("RebelAnswers password reset");
      msg.setText(
          "Dear " + user.getName() + ",\n\n"
              + "Your new password for RebelAnswers is:\n"
              + new_password);
      try {
        this.mailSender.send(msg);

        user.setAndEncodePassword(new_password);
        if (!service.store(user)) {
          throw new UserStorageErrorException(user);
        }

        return new ModelAndView("authentication/passwordsent");
      }
      catch (MailException e) {
        result.addError(new ObjectError(RecoveryData.class.getSimpleName(), e.getMessage()));
        return mav;
      }
    }
  }
}