## Spring Boot with Fakestore API and Gradle (Java 17)

This README outlines how to run a Spring Boot application that consumes the product API from [fakestore.com](https://fakestoreapi.com/) and allows placing orders for products using Java 17 and Gradle.

**Note:** This guide assumes you already have a Spring Boot project set up with Gradle and Java 17 configured.

## Project Purpose

This project demonstrates consuming the Fakestore product API and simulating order placement. Key functionalities include:

*   **Fetching product data:** The application retrieves product information from the Fakestore API.
*   **Order placement:** The application provides an endpoint to simulate placing orders. Orders are stored in an in-memory `Map` acting as a simple database.

## Dependencies

The following Gradle dependencies are required for this project:

*   Spring Web for REST API communication
*   Lombok for helping reduce boilerplate code

Add these to your `build.gradle` file:

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}