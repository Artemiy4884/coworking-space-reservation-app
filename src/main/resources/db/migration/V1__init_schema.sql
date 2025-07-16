CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'customer') NOT NULL
);

CREATE TABLE coworking_spaces (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    room_details TEXT,
    price DECIMAL(10, 2),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    space_id INT,
    start_time DATETIME,
    end_time DATETIME,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (space_id) REFERENCES coworking_spaces(id)
);
