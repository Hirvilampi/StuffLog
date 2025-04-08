-- Database: stufflog

-- DROP DATABASE IF EXISTS stufflog;

CREATE DATABASE stufflog
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'fi-FI'
    LC_CTYPE = 'fi-FI'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

	DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS category_subcategory;
DROP TABLE IF EXISTS subcategory;
DROP TABLE IF EXISTS condition;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS size;
DROP TABLE IF EXISTS state;

CREATE TABLE user_account
(userid BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL,
password VARCHAR(100) NOT NULL,
firstname VARCHAR(50) NOT NULL,
surname VARCHAR(50),
email VARCHAR(100) NOT NULL,
role VARCHAR(20) NOT NULL
);

CREATE TABLE state
(state_id BIGSERIAL PRIMARY KEY,
state_name VARCHAR(20),
entry_date VARCHAR(10)
);

CREATE TABLE size
(sizeof_id BIGSERIAL PRIMARY KEY,
sizename VARCHAR(20)
);

CREATE TABLE location
(location_id BIGSERIAL PRIMARY KEY,
locationname VARCHAR(50) NOT NULL
);

CREATE TABLE condition
(condition_id BIGSERIAL PRIMARY KEY,
condition_name VARCHAR(20)
);

CREATE TABLE subcategory
(subcategory_id BIGSERIAL PRIMARY KEY,
subcategory_name VARCHAR(50) NOT NULL
);

CREATE TABLE category
(category_id BIGSERIAL PRIMARY key,
category_name VARCHAR(50) NOT NULL
);


CREATE TABLE category_subcategory 
( id BIGSERIAL PRIMARY KEY,
subcategory_id BIGINT,
category_id BIGINT,
FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory_id),
FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE item (
    item_id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR(30) NOT NULL,
    description VARCHAR(500),
    purchase_price FLOAT8,
    selling_price FLOAT8,
    rental_price FLOAT8,
    locationinfo VARCHAR(100),
    category_id BIGINT,
    location_id BIGINT,
    sizeof_id BIGINT,
    condition_id BIGINT,
    state_id BIGINT,
    userid BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (location_id) REFERENCES location(location_id),
    FOREIGN KEY (sizeof_id) REFERENCES size(sizeof_id),
    FOREIGN KEY (condition_id) REFERENCES condition(condition_id),
    FOREIGN KEY (state_id) REFERENCES state(state_id),
    FOREIGN KEY (userid) REFERENCES user_account(userid)
);


INSERT INTO condition (condition_name)
VALUES 
    ('New'), 
    ('Like new'), 
    ('Unused'), 
    ('Used'), 
    ('Good'), 
    ('Fair'), 
    ('Poor'), 
    ('Broken');

INSERT INTO location (locationname)
VALUES ('No location'), ('Study'), ('Living Room'), 
('Master bedroom'), ('Hall'), ('Storage');

INSERT INTO size (sizename) VALUES
  ('NONE'), ('XXXS'), ('XXS'), ('XS'), ('S'), ('M'), ('L'), ('XL'), ('XXL'), ('XXXL'),
  ('20'), ('21'), ('22'), ('23'), ('24'), ('25'), ('26'), ('27'), ('28'), ('29'),
  ('30'), ('31'), ('32'), ('33'), ('34'), ('35'), ('36'), ('37'), ('38'), ('39'),
  ('40'), ('41'), ('42'), ('43'), ('44'), ('45'), ('46'), ('47'), ('48'), ('49'),
  ('50'), ('51'), ('52'), ('53'), ('54'), ('55'), ('56'), ('57');

INSERT INTo category(category_name) VALUES 
('No category'), ('Apparel'),('Art'), ('Baby'),('Cars'),('Clothes'),
('Collectibles & Antiques'),('Fabrics'),('Hobbies'), ('Home & Living'),
('Music'), ('Outdoor'), ('Sports'), ('Travel'), ('Tools');

INSERT INTO subcategory (subcategory_name)
VALUES
  ('No subcategory'), ('Ice Hockey'), ('Soccer'), ('Tennis'), ('Squash'), ('Floor ball'), ('Skiing'),
  ('Mom''s clothes'), ('Children'), ('Outdoor'), ('Dad''s clothes'),
  ('Drawing'), ('Riding'), ('Bicycle'),
  ('Wood'), ('Iron'), ('Lawn'), ('Car'),
  ('Cd:s'), ('Instruments');

INSERT INTO condition (condition_name) VALUES
('New'), ('Like new'), ('Good'), ('Used'), ('Poor'),('Broken');

INSERT INTO state (state_name) VALUES
('In use') ('For sale'), ('Sold'), ('For rent'), ('Standby'), ('Available'),;

INSERT INTO user_account (username, password, firstname, surname, email, role) VALUES
('admin','$2a$10$Xl187lOiHVJgG8cLRrRUveuQzOZx5InzgJB6u.iAY0KkJ7oDiD8Zi','Timo','Lampinen','lampinen.timo@gmail.com','ADMIN'),
('user','$2y$10$I0SGrzr25KfMLIH96VS7rOjHH0ugfkC9/UW9Y6l44qDh2EQSVB5A.','Satu','Lampinen','satu.lampinen81@gmail.com','USER'),
('test','$2y$10$I1FjJ9VCyYE9Qq3Tvox19.dCzgRxRypln27ueXf8YTC7s67qftx3i','TestName','UserSurname','noemail@email.no','TEST');

-- Insert items for admin user (userid = 1)
INSERT INTO item 
(item_name, description, purchase_price, selling_price, rental_price, 
category_id, location_id, sizeof_id, condition_id, state_id, userid)
VALUES 
('Märkäimuri', 'Hyvä laite. Kärcher', 200.0, 100.0, 30.0, 1, 6, 1, 1, 1, 1), -- admin user (userid = 1), category_id = 1, location_id = 6, size_id = 1, condition_id = 1, state_id = 1
('Soccer ball', '', NULL, NULL, NULL, 1, NULL, NULL, 1, 1, 1),  -- admin user (userid = 1), category_id = 1
('Drill', '', NULL, NULL, NULL, 1, 2, NULL, 1, 1, 1),  -- admin user (userid = 1), category_id = 1, location_id = 2
('Hockey Stick', 'Farrow flex60', NULL, NULL, NULL, 1, 1, NULL, 1, 1, 1),  -- admin user (userid = 1), category_id = 1, location_id = 1
('Vacuum cleaner', 'Quiet hoover', 30.0, NULL, NULL, 1, NULL, NULL, 1, 2, 2), -- regular user (userid = 2), category_id = 1, state_id = 2
('Sewing machine', 'Simens', 20.0, NULL, NULL, 1, NULL, NULL, 1, 2, 2), -- regular user (userid = 2), category_id = 1, state_id = 2
('Full Face bicycle helmet', 'Bell, kids size', 170.0, 80.0, NULL, 1, NULL, NULL, 1, 2, 3), -- regular user (userid = 2), category_id = 1, state_id = 3
('Tent', '4 people, all weather', 35.0, NULL, NULL, 1, NULL, NULL, 1, 3, 2), -- test user (userid = 3), category_id = 1, state_id = 2
('Packraft', '2 people, MRS-packraft with 2 paddles', 1450.0, 999.0, NULL, 1, NULL, NULL, 1, 3, 3), -- test user (userid = 3), category_id = 1, state_id = 3
('Ice Skates', 'size 36', NULL, NULL, NULL, 1, 6, NULL, 1, 2, 3), -- test user (userid = 3), category_id = 1, location_id = 6
('Ice Skates', 'size 37', NULL, NULL, NULL, 1, 6, NULL, 1, 2, 3), -- test user (userid = 3), category_id = 1, location_id = 6
('Ice Skates', 'size 38', NULL, NULL, NULL, 1, 6, NULL, 1, 2, 3), -- test user (userid = 3), category_id = 1, location_id = 6
('Ice Skates', 'size 39', NULL, NULL, NULL, 1, 6, NULL, 1, 2, 3), -- test user (userid = 3), category_id = 1, location_id = 6
('Ice Skates', 'size 40', NULL, NULL, NULL, 1, 6, NULL, 1, 2, 3); -- test user (userid = 3), category_id = 1, location_id = 6
