
# TestProject4SP

This is a Spring Boot application developed by `benomads`. The application provides authentication services, including user registration and login.

## Technologies Used

- Java 17
- PostgreSQL 15
- Spring Boot 3.2.5
- Maven 3.9.3


## Features

- **User Registration**: New users can register for an account.
- **User Login**: Registered users can log in to their account.
- **User Management**: Admins can fetch all users, fetch a specific user by their ID or username, update a specific user by their ID, and delete a specific user by their ID.
- **Product Management**: Users can fetch all products, fetch a specific product by its ID, fetch products by their category, create a new product, update a specific product by its ID, and delete a specific product by its ID.
- **Order Management**: Users can fetch all orders, fetch orders by a specific user ID, fetch a specific order by its ID, fetch orders by their category, create a new order, update a specific order by its ID, and delete a specific order by its ID.
- **News Management**: Users can fetch all news, fetch a specific news by its ID, create a new news, update a specific news by its ID, and delete a specific news by its ID.

  
## Setup

To run this project, you need to have Java and Maven installed on your machine.

1. Clone the repository:

```bash
git clone https://github.com/benomads/TestProject4SP.git
```

2. Navigate to the project directory:

```bash
cd TestProject4SP
```

3. Build the project:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

The application will start running at `http://localhost:8080`.

## API Endpoints

### AuthController
- POST `/api/v1/auth/login`: Login a user.
- POST `/api/v1/auth/register`: Register a new user.

### UserController
- GET `/api/v1/users`: Get all users. Access: 'ADMIN' authority.
- GET `/api/v1/users/{id}`: Get a specific user by their ID. Access: 'ADMIN' authority.
- GET `/api/v1/users/username/{username}`: Get a specific user by their username. Access: 'ADMIN' authority.
- PUT `/api/v1/users/{id}`: Update a specific user by their ID. Access: 'ADMIN' or 'USER' authority.
- DELETE `/api/v1/users/{id}`: Delete a specific user by their ID. Access: 'ADMIN' authority.

### ProductController
- GET `/api/v1/products`: Get all products.
- GET `/api/v1/products/{id}`: Get a specific product by its ID.
- GET `/api/v1/products/category/{category}`: Get products by their category.
- POST `/api/v1/products`: Create a new product. Access: 'ADMIN' or 'USER' authority.
- PUT `/api/v1/products/{id}`: Update a specific product by its ID. Access: 'ADMIN' or 'USER' authority.
- DELETE `/api/v1/products/{id}`: Delete a specific product by its ID. Access: 'ADMIN' authority.

### OrderController
- GET `/api/v1/orders`: Get all orders. Access: 'ADMIN' authority.
- GET `/api/v1/orders/user/{userId}`: Get orders by a specific user ID. Access: 'ADMIN' or 'USER' authority.
- GET `/api/v1/orders/{id}`: Get a specific order by its ID. Access: 'ADMIN' or 'USER' authority.
- GET `/api/v1/orders/category/{category}`: Get orders by their category. Access: 'ADMIN' or 'USER' authority.
- POST `/api/v1/orders`: Create a new order. Access: 'ADMIN' or 'USER' authority.
- PUT `/api/v1/orders/{id}`: Update a specific order by its ID. Access: 'ADMIN' or 'USER' authority.
- DELETE `/api/v1/orders/{id}`: Delete a specific order by its ID. Access: 'ADMIN' authority.

### NewsController
- GET `/api/v1/news`: Get all news.
- GET `/api/v1/news/{id}`: Get a specific news by its ID.
- POST `/api/v1/news`: Create a new news. Access: 'ADMIN' or 'USER' authority.
- PUT `/api/v1/news/{id}`: Update a specific news by its ID. Access: 'ADMIN' or 'USER' authority.
- DELETE `/api/v1/news/{id}`: Delete a specific news by its ID. Access: 'ADMIN' authority.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)


Remember to replace the repository URL in the `git clone` command with the actual URL of your repository.
