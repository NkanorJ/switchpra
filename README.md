# SwitchPra

### Project Overview

This project implements a microservice architecture with four modules:

### API Gateway:

Acts as a router for all incoming requests.

#### Role:

The API Gateway acts as a router, managing the routing of requests to the appropriate microservices. It is responsible
for API gateway functionalities such as load balancing, security, and request forwarding.
Key Responsibilities:
Routing: Forwards incoming requests to the appropriate microservices (User, Bill Payment, Transaction).
Load Balancing: Distributes incoming requests across different instances of the services.
Security: Provides basic security configurations, ensuring that only authorized users can access certain routes.
Cloud Gateway: Uses Spring Cloud Gateway as the core technology to handle routing, security, and load balancing.

### User Service:

Manages user-related functionalities such as account creation, login, KYC validation, JWT token authorization, and
authentication.

#### Role:

The User Service is responsible for all user-related actions, such as account creation, login, and validation.
Key Responsibilities:
Account Creation: Manages the creation of user accounts and stores user information securely.
Login and JWT Authentication: Handles login requests and generates JWT tokens for user sessions.
KYC Validation: Implements markers and checkers for KYC (Know Your Customer) validation to ensure the authenticity of
users.
Authorization: Validates JWT tokens to authorize users for protected routes and services.

### Bill Payment Service:

Handles bill payment operations, integrates with the User Service for user validation, and protects endpoints using
bearer tokens.

#### Role

The Bill Payment Service manages bill payment functionality, including retrieving bill categories and making payments.
Key Responsibilities:
Bill Categories: Provides a list of available bill categories (e.g., electricity, water, internet) for users to choose
from.
User Validation: Uses FeignClient to communicate with the User Service for user validation before proceeding with bill
payment.
Authorization and Token Validation: Protects endpoints by requiring valid JWT tokens from the user. The Bill Payment
Service verifies the token by calling the User Service before granting access to sensitive endpoints.

### Transaction Service:

Manages transactions, implementing similar authorization and authentication mechanisms as the User and Bill Payment
Services.

#### Role

Role: The Transaction Service handles all transaction-related processes, including transaction creation, updates, and
status management.
Key Responsibilities:
Transaction Processing: Manages the creation and updating of transactions.
Authorization and Authentication: Similar to the User and Bill Payment Services, the Transaction Service validates JWT
tokens and ensures that only authorized users can perform transactions.
Transaction History: Provides users with access to their transaction history and status.

## Security and Authentication

JWT Authentication: All services require the user to authenticate using JWT tokens. The token is used to verify the
identity of the user and grant access to protected endpoints.
Bearer Token Validation: Services like Bill Payment and Transaction validate the bearer token on every request to ensure
that the user is authenticated and authorized.

##Technology Stack
Spring Boot: Used for building the microservices.
Spring Cloud Gateway: Used for API Gateway routing and security.
FeignClient: Used for communication between microservices, particularly for user validation in the Bill Payment Service.
JWT: Used for securing the services and validating users via tokens.
