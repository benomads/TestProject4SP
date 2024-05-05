
# TestProject4SP

This is a Spring Boot application developed by `benomads`. The application provides authentication services, including user registration and login.

## Technologies Used

- Java 17
- PostgreSQL 15
- Spring Boot 3.2.5
- Maven 3.9.3

## Features

- User registration
- User login

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

- POST `/api/v1/auth/login`: Login a user.
- POST `/api/v1/auth/register`: Register a new user.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)


Remember to replace the repository URL in the `git clone` command with the actual URL of your repository.
