# Java Template Service

This is a template service, pleae do not add business code. Use this project as a template from Github to start your own project.

# Getting Started

* Replace all occurrences of `java-template-service` with `your-service-name`. `tip:` `CTRL + SHIFT + R` (Windows and Linux) | `Command + SHIFT + R` (Mac) 
* Rename `JavaTemplateServiceApplication` with `{YourServiceName}Application`.
* Rename your package to something meaningful to your service. (if you're creating a fibonacci service, you can name `ca.levio.fibonacci`) Make sure this is also done for your `test` packages too.
* Remove classes and tests you won't use
* Follow the steps at  to [CLOUD_DEPLOY](CLOUD_DEPLOY.md) make the first deployment of your application.
* << Only >> If you're wearing the devOps hat and want to create the cloud environment from scratch, follow  [CLOUD_CONFIG](CLOUD_CONFIG.md) !

## The API
[API DOC](./openapi/openapi.md)

* This is auto-generated as long as you run `mvn clean package -Popenapidoc` . Which will enable the specified profile and generate your documentation.
* Don't forget to run this command, otherwise your documentation will be outdated (This will be an automated task under Jenkins Pipeline)

## Cloud Config
[CLOUD_CONFIG](CLOUD_CONFIG.md) 

* Cloud Configuration DOC - DevOps only (No need to run this many times if you're not creating a new environment)
* Use this if you're creating the cloud environment from scratch and you need the basic configuration for this service.

## Cloud Deploy
[CLOUD_DEPLOY](CLOUD_DEPLOY.md)

* Cloud Deployment DOC - Basic steps to configure your local environment, build and deploy to a cloud environment)
* Use this if you're a developer looking for creating a deployment for this service on the cloud and the cloud configuration already exists.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web.reactive)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
