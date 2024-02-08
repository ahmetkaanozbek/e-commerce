This Repository includes backend part of the Spring Boot based E-Commerce application. Users can select products which they desire to buy from several categories and add to their carts. After adding, deleting, or updating the quantity of the items, they can buy these product through payment service API provided by <a href=https://craftgate.io/>Craftgate</a>.

The application uses dockerized PostgresSQL database and Hibernate as JPA implementation to get benefit from ORM.

For security, I implemented my own security settings different from the default settings which are provided by Spring Security. Application uses JWT token to authenticate a user. Also, it uses refresh token to prevent users to authenticate themselves repeatedly and improve security. For more enhanced security, application uses role-based access mechanism along with `@PreAuthorize` annotation. Application includes three different roles which are `“ROLE_USER”`, `“ROLE_ADMIN”` and `“ROLE_SUPER_ADMIN”`. Users who have `“ROLE_ADMIN”` have access to the product and the category related APIs which means they can made changes on products and categories. On the other hand, only `“SUPER_ADMIN”` role can use APIs related to user and roles which means only `“SUPER_ADMIN”` can create new roles, change someone’s role and delete an existing role. `“USER”` role is the default role. All endpoints excluding these: `“/api/auth/register“`, `“/api/auth/login“` require authentication. Therefore, only a registered users can benefit from e-commerce services which is provided by this application. The application uses `SecurityContextHolder` to get authenticated user and add products to her/his special cart. 

I have created custom Exception classes to handle edge cases such as when there is no existing `Product` or `Category` with that `id`. Also these exception classes under `exception` package return informative responses to the client. With using, `@RestControllerAdvice` annotation for `GlobalControllerExceptionHandler.class` and `@ExceptionHandler` annotation for related methods, returned responses inform clients about what happened which means what caused to an error. Also, with using Slf4j logging, server is also informed about what cause to that error.

To test the application, unit and integration tests has been created with JUnit5, Mockito and AssertJ. For now, only `CartController`, `CartRepository` and `CartService` classes methods has been tested. To test repositories, application uses embedded H2 database.

To integrate a payment service, I have used Craftgate API due to it’s well-structured integration design. To pay the price of whole `cartItem`s which means to make payments, a user can send her/his card information to the server (Application only have test API keys so only test cards will be accepted. One could get test cards from <a href="https://developer.craftgate.io/en/test-cards/">here</a>.). If the payment doesn’t accepted, `CraftgateException` will be thrown with the appropriate message. Also application doesn’t persist card information in the database. To get more information about Craftgate API, you can click <a href="https://developer.craftgate.io/en/">here</a>.

The program is still under development. Therefore, I will add more features in the future.

If you encounter any issues or have suggestions or want to contribute code, I would be very happy. Thank you for your interest in my project, and I appreciate your support!