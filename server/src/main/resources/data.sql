DELETE FROM book;
ALTER TABLE book AUTO_INCREMENT = 1001;

DELETE FROM category;
ALTER TABLE category AUTO_INCREMENT = 1001;

INSERT INTO `category` (`name`) VALUES ('Comedy'),('Biography'),('Mystery'),('Thriller'),('Fiction'),('Kids');


INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('This Plot is Bananas!', 'J.P.Valentine', '', 1299, 0, FALSE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('How to Write Funny', 'Scott Dikkers', '', 1499, 0, FALSE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The New Comedy Bible', 'Judy Carter', '', 1099, 0, TRUE, FALSE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Humor, Seriously', 'Jennifer Aaker & Naomi Bagdonas', '', 1599, 0, FALSE, TRUE, 1001);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('How to Make People Laugh', 'James W. Williams', '', 1399, 0, TRUE, FALSE, 1001);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('George Washington', 'John R. Alden', '', 2599, 0, FALSE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Churchill', 'Andrew Roberts', '', 2499, 0, FALSE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Harry', 'Angela Levin', '', 3099, 0, TRUE, TRUE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Tom Hanks Enigma', 'David Gardner', '', 2599, 0, TRUE, FALSE, 1002);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Queen of Our Times', 'Robert Hardman', '', 2099, 0, FALSE, FALSE, 1002);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('A Village Murder', 'Frances Esham', '', 1099, 0, TRUE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Many a Twist', 'Sheila Connolly', '', 1599, 0, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Secret Adversary', 'Agatha Christie', '', 1299, 0, TRUE, TRUE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('When you Find Me', 'P.J. Vernon', '', 1799, 0, FALSE, FALSE, 1003);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('A Purely Private Matter', 'Darcie Wilde', '', 2099, 0, FALSE, FALSE, 1003);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The House Keeper', 'Natalie Barelli', '', 599, 0, FALSE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('To Catch a Killer', 'Sheryl Scarborough', '', 599, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Bone Farm', 'Dean Koontz', '', 599, 0, FALSE, TRUE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Missing Molly', 'Natalie Barelli', '', 599, 0, TRUE, FALSE, 1004);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('One of us is Dead', 'Jeneva Rose', '', 599, 0, FALSE, FALSE, 1004);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Last Dog on Earth', 'Adrian J. Walker', '', 1299, 0, FALSE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Rising', 'Daniel Greene', '', 3099, 0, FALSE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Implied Spaces', 'Walter Jon Williams', '', 2599, 0, TRUE, FALSE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Alita: Battle Angel', 'Pat Cadigan', '', 1099, 0, FALSE, TRUE, 1005);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Big Lies in a Small Town', 'Diane Chamberlain', '', 1599, 0, TRUE, FALSE, 1005);

INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Last Kids on Earth', 'Max Brallier', '', 799, 0, TRUE, FALSE, 1006);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('Mission Unstoppable', 'Dan Gutman', '', 899, 0, FALSE, TRUE, 1006);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Prince and the Goblin', 'Rory Madge & Bryan Huff', '', 1099, 0, FALSE, FALSE, 1006);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('How Tiger got his stripes', 'Steve Hofstetter', '', 1199, 0, TRUE, FALSE, 1006);
INSERT INTO `book` (title, author, description, price, rating, is_public, is_featured, category_id) VALUES ('The Mysterious Garden', 'PJ Ryan', '', 1599, 0, FALSE, FALSE, 1006);

