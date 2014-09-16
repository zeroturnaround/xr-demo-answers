package com.zeroturnaround.rebelanswers.security;

import com.zeroturnaround.rebelanswers.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsWrapper implements UserDetails {

  private final static Collection<SimpleGrantedAuthority> DEFAULT_AUTHORITIES;

  static {
    DEFAULT_AUTHORITIES = Collections.unmodifiableList(Arrays.asList(new SimpleGrantedAuthority(StandardAuthorities.USER)));
  }

  private final User delegate;

  public UserDetailsWrapper(final User delegate) {
    this.delegate = delegate;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    if ((null == delegate.getFacebookId() && null == delegate.getEmail()) || null == delegate.getId())
    {
      return Collections.emptyList();
    }

    return DEFAULT_AUTHORITIES;
  }

  public String getPassword() {
    return delegate.getPassword();
  }

  public String getUsername() {
    String facebook_id = delegate.getFacebookId();
    if (facebook_id != null) {
      return facebook_id;
    }
    return delegate.getEmail();
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }

  public User getDelegate() {
    return delegate;
  }

}
