-- Inserting data into Users
INSERT INTO Users (fullName, username, password, avatarUrl, email, phoneNumber) VALUES
                                                                                    ('John Doe', 'johndoe', 'password123', 'avatarUrl1', 'johndoe@example.com', '1234567890'),
                                                                                    ('Jane Doe', 'janedoe', 'password123', 'avatarUrl2', 'janedoe@example.com', '0987654321');

-- Inserting data into Roles
INSERT INTO Roles (roleName) VALUES
                                 ('ROLE_USER'),
                                 ('ROLE_ADMIN');

-- Assigning roles to users
INSERT INTO UserRoles (userId, roleId) VALUES
                                           (1, 1),
                                           (2, 2);

-- Inserting data into Categories
INSERT INTO Categories (categoryName) VALUES
                                          ('Electronics'),
                                          ('Books');

-- Inserting data into Products
INSERT INTO Products (title, description, price, quantity, imageUrl, userId) VALUES
                                                                                 ('Product 1', 'Description for product 1', 100.0, 10, 'imageUrl1', 1),
                                                                                 ('Product 2', 'Description for product 2', 200.0, 20, 'imageUrl2', 2);

-- Assigning categories to products
INSERT INTO ProductCategories (productId, categoryId) VALUES
                                                          (1, 1),
                                                          (2, 2);

-- Inserting data into Orders
INSERT INTO Orders (userId, productId, quantity, totalPrice, address, phoneNumber) VALUES
                                                                                       (1, 1, 1, 100.0, 'Address 1', '1234567890'),
                                                                                       (2, 2, 1, 200.0, 'Address 2', '0987654321');

-- Inserting data into News
INSERT INTO News (title, content, productId) VALUES
                                                 ('News 1', 'Content for news 1', 1),
                                                 ('News 2', 'Content for news 2', 2);