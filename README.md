# Primary back end automation tests exercises

 Automated tests for [MercadoLibre API](https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas)

## Technologies used:

* [Java v1.8.0_221](https://www.oracle.com/technetwork/java/javase/8u221-relnotes-5480116.html)
* [Gradle v6.0.1](https://docs.gradle.org/current/userguide/userguide.html)
* [JUnit v5.4.2](https://junit.org/junit5/docs/current/api/)
* [REST-assured v2.9.0](http://rest-assured.io/)
* [Hamcrest v1.3](http://hamcrest.org/JavaHamcrest/javadoc/1.3/)



## Install and usage:

1. Clone the project using the command `git clone https://github.com/fakars/backend-test-exercise.git`.
2. At the project's root run the command `./gradlew clean test`, some optional parameters are available using 
    the project flag `-D{parameterName=arguments}`.
3. Optional parameters that can be passed:

| ParamName     | Description                                                                  | Available values                                   |
| ------------- | ---------------------------------------------------------------------------- | -------------------------------------------------- |
| `environment` | Sets the environment from which to run the tests                             | N/A, *Default: 'production'*                       |                             
| `site`        | Sets the ${SITE_ID} part of the request url to run test for selected country | arg, br, ve, etc, *Default: 'arg'*                 |             
| `tag`         | It permits to run tests annotated with the @Tag                              | regression, search, items, *Default: 'regression'* |