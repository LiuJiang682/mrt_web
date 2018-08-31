# MRT Web


## Quickstart
To run the app you just need to:

    git clone https://github.com/LiuJiang682/mrt_web.git 
    cd mrt_web
    mvn spring-boot:run

To check everything is running you can:

    # Visit the homepage
    http://localhost:8080
    
    # Go to the sample REST endpoint
    http://localhost:8080/template/123
    

## Start developing
The Java code is available at `src/main/java` as usual, and the frontend files are in 
`src/main/frontend`.

### Running the backend
Run `StarterMain` class from your IDE.

### Running the frontend
Go to `src/main/frontend` and run `npm start`. (Run `npm install` before that if it's the first time)

Now we should work with `localhost:9090` (this is where we'll see our live changes reflected)
 instead of `localhost:8080`.

### Database
The database connections are configured in 
[DatabaseConfig](src/main/java/com/dlizarra/starter/DatabaseConfig.java)
where we can find a working H2 embedded database connection for the default profile, and the staging and production configurations examples for working with an external database.


### Unit and integration testing
For **unit testing** we included Spring Test, JUnit, Mockito and AssertJ as well as an [AbstractUnitTest](src/test/java/com/dlizarra/starter/support/AbstractUnitTest.java) class that we can extend to include the boilerplate annotations and configuration for every test. [UserServiceTest](src/test/java/com/dlizarra/starter/user/UserServiceTest.java) can serve as an example.

To create integration tests we can extend [AbstractIntegrationTest](src/test/java/com/dlizarra/starter/support/AbstractIntegrationTest.java) and make use of Spring `@sql` annotation to run a databse script before every test, just like it's done in [UserRepositoryTest](src/test/java/com/dlizarra/starter/user/UserRepositoryTest.java).

### Other ways to run the app
#### Run everything from Maven

    mvn generate-resources spring-boot:run

The Maven goal `generate-resources` will execute the frontend-maven-plugin to install Node
and Npm the first time, run npm install to download all the libraries  that are not 
present already and tell webpack to generate our `bundle.js`. It's the equivalent of running `npm run build` or `npm start` on a terminal.

#### Run Maven and Webpack separately (no hot-reloading)

    mvn spring-boot:run
In a second terminal:
    
    cd src/main/frontend
    npm run build

## Tech stack and libraries
### Backend
- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring MVC](http://docs.spring.io/autorepo/docs/spring/3.2.x/spring-framework-reference/html/mvc.html)
- [Spring Data](http://projects.spring.io/spring-data/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Test](http://docs.spring.io/autorepo/docs/spring-framework/3.2.x/spring-framework-reference/html/testing.html)
- [JUnit](http://junit.org/)
- [Mockito](http://mockito.org/)
- [AssertJ](http://joel-costigliola.github.io/assertj/)
- [Lombok](https://projectlombok.org/)
- [Orika](http://orika-mapper.github.io/orika-docs/)
- [Maven](https://maven.apache.org/)

### Frontend
- [Node](https://nodejs.org/en/)
- [React](https://facebook.github.io/react/)
- [Redux](http://redux.js.org/)
- [Webpack](https://webpack.github.io/)
- [Axios](https://github.com/mzabriskie/axios)
- [Babel](https://babeljs.io/)
- [ES6](http://www.ecma-international.org/ecma-262/6.0/)
- [ESLint](http://eslint.org/)

---
