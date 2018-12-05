CREATE DATABASE IF NOT EXISTS facebook_lite;
CREATE USER IF NOT EXISTS'db_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT ALL PRIVILEGES ON facebook_lite.* TO 'db_user'@'localhost';

USE facebook_lite;
CREATE TABLE IF NOT EXISTS users (
              id INT AUTO_INCREMENT, 
              first_name TINYTEXT NOT NULL,
              last_name TINYTEXT NOT NULL,
              email VARCHAR(50) NOT NULL, 
              username TEXT NOT NULL,
              age INT NOT NULL,
              password BLOB NOT NULL,
              salt BLOB NOT NULL,
              status TINYTEXT,
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

INSERT INTO users (first_name, last_name, email, username, age, password, salt) VALUES ("John", "Brehm", "brehmjohn3@gmail.com", "cooljohnny3", 23, 1, 1);
INSERT INTO users (first_name, last_name, email, username, age, password, salt) VALUES ("Peter", "Brehm", "brehmpeter@gmail.com", "profduke49", 23, 1, 1);
INSERT INTO users (first_name, last_name, email, username, age, password, salt) VALUES ("Robert", "Bedrosian", "rob.bedrosian@gmail.com", "mrconfus3d", 23, 1, 1);
INSERT INTO users (first_name, last_name, email, username, age, password, salt) VALUES ("Armine", "Khachatryan", "armine.Khachatryan@gmail.com", "arminek", 23, 1, 1);
INSERT INTO users (first_name, last_name, email, username, age, password, salt) VALUES ("Willy", "Alicon", "willy.Alicon@gmail.com", "willyA", 23, 1, 1);

INSERT INTO friends (user_id1, user_id2) VALUES (1, 2);
INSERT INTO friends (user_id1, user_id2) VALUES (2, 1);
INSERT INTO friends (user_id1, user_id2) VALUES (1, 3);
INSERT INTO friends (user_id1, user_id2) VALUES (3, 1);
INSERT INTO friends (user_id1, user_id2) VALUES (1, 5);
INSERT INTO friends (user_id1, user_id2) VALUES (5, 1);
INSERT INTO friends (user_id1, user_id2) VALUES (3, 4);
INSERT INTO friends (user_id1, user_id2) VALUES (4, 3);
INSERT INTO friends (user_id1, user_id2) VALUES (3, 5);
INSERT INTO friends (user_id1, user_id2) VALUES (5, 3);

INSERT INTO posts (user_id, content) VALUES (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices lorem quis ornare ornare. Phasellus iaculis turpis nec enim finibus, ac cursus purus tristique. Nunc ut rhoncus ipsum. Cras non.');
INSERT INTO posts (user_id, content) VALUES (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices lorem quis ornare ornare. Phasellus iaculis turpis nec enim finibus, ac cursus purus tristique. Nunc ut rhoncus ipsum. Cras non.');
INSERT INTO posts (user_id, content) VALUES (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices lorem quis ornare ornare. Phasellus iaculis turpis nec enim finibus, ac cursus purus tristique. Nunc ut rhoncus ipsum. Cras non.');
INSERT INTO posts (user_id, content) VALUES (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices lorem quis ornare ornare. Phasellus iaculis turpis nec enim finibus, ac cursus purus tristique. Nunc ut rhoncus ipsum. Cras non.');
INSERT INTO posts (user_id, content) VALUES (5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices lorem quis ornare ornare. Phasellus iaculis turpis nec enim finibus, ac cursus purus tristique. Nunc ut rhoncus ipsum. Cras non.');
