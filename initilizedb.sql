CREATE DATABASE IF NOT EXISTS facebook_lite;
CREATE USER IF NOT EXISTS'db_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT ALL PRIVILEGES ON *.'facebook_lite' TO 'db_user'@'localhost';

USE facebook_lite;
CREATE TABLE IF NOT EXISTS users (
              id INT AUTO_INCREMENT, 
              first_name TINYTEXT NOT NULL,
              last_name TINYTEXT NOT NULL,
              email VARCHAR(50) NOT NULL, 
              username TEXT NOT NULL,
              age INT NOT NULL,
              password TINYTEXT NOT NULL,
              status TINYTEXT NOT NULL,
              age_visibility boolean DEFAULT 1,
              status_visibility boolean DEFAULT 1,
              friends_visibility boolean DEFAULT 1,
              post_visibility boolean DEFAULT 1,
              PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS posts (
              id INT AUTO_INCREMENT, 
              user_id INT NOT NULL,
              content TEXT NOT NULL,
              PRIMARY KEY (id),
              FOREIGN KEY (user_id) REFERENCES users(id));

CREATE TABLE IF NOT EXISTS friends (
              id INT AUTO_INCREMENT, 
              user_id1 INT NOT NULL,
              user_id2 INT NOT NULL,
              PRIMARY KEY (id),
              FOREIGN KEY (user_id1) REFERENCES users(id),
              FOREIGN KEY (user_id2) REFERENCES users(id));
