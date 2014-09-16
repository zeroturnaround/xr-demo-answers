# Rebel Answers #
Questions & answers web application written in Java (EE) and Spring to showcase various XRebel features.

These are the features:

* Asking questions
* Writing and accepting answers
* User registration & authentication
* Registration with Facebook
* Commenting questions and answers
* Voting questions and answers
* Post-deploy Selenium test suite
* User profile
* Password recovery via e-mail

## Usage ##

1. Clone 
 
    ```bash
     $ git clone git://github.com/zeroturnaround/xr-demo-answers.git
     ```

2. Configure the application for deployment by editing `src/main/filters/prod.properties`
 
3. Build the WAR archive:
 
    ```bash
     $ mvn clean package
     ```
4. Deploy your `xr-demo-answers-java.war` archive to your servlet container.

We have prepared a Vagrant box for Rebel Answers to make trying it out quick and easy. Follow the instructions at https://github.com/zeroturnaround/xr-demo-provisioning.

## Directory Structure ##
The project is structured as a web app project:

    ```
    src/
      main/
        filters/
          filter.properties                  // Properties to change with your own information for deployment
        java/                                // Java sources
          ...
        resources/                           // non-Java resources, will be placed in `WEB-INF/classes` in the final WAR
          liquibase/                         // Database migrations and LiquiBase configuration properties
            ...
          facebook.properties                // properties file for Facebook integration
          jdbc.properties                    // properties file for JDBC connection
          mail.properties                    // properties file for SMTP connection
          rebel.xml                          // JRebel configuration file
        webapp/                              // Web application resources and pages
          WEB-INF/
            ...
    ```

## Development ##
Rebel Answers requires `Spring MVC 4.1.x`, `MySQL` and your favourite Java application server (e.g. Tomcat). Applying database migrations in development environment needs `liquibase` and a `MySQL JDBC driver`, but when using Maven these should be pulled in automatically.

1. Configure the application by updating the properties in `src/main/filters/dev.properties`
2. Create the database called `answers` in your MySQL server
3. Update the database by running the schema migrations with:

    ```bash
     $ mvn -Denv=dev resources:resources liquibase:update
     ```
4. Deployment options:
  1. Build a WAR and deploy it to an application server (e.g. Tomcat), using `mvn -Denv=dev clean package`
  2. Variation: use JRebel for development
  3. Other variation: open the IntelliJ IDEA project files to run the application directly in an IDE
5. Access the application through `http://localhost:8080/xr-demo-answers`

Contributions are more than welcome!

## Furtrher configuration ##

Your specific installation might require its own dedicated configuration in the property filter files, however for demo purposes, this is a typical example of `prod.properties`:

    ```properties
    facebook.clientId=383402301757345
    facebook.clientSecret=191194dfbbb921f7eca23b1e21f76bb5
    jdbc.host=localhost
    jdbc.port=3306
    jdbc.database=answers
    jdbc.username=root
    jdbc.password=
    mail.username=you@gmail.com
    mail.password=yourpassword
    ```

For Facebook connect to work, you need serve the application from `answers.xrebel.com` by for example adding the following entry to your `/etc/hosts` file:

    ```
    127.0.0.1   answers.xrebel.com
    ```

The application will then be accessible at `http://answers.xrebel.com:8080/xr-demo-answers` and you can log in with your Facebook credentials.
