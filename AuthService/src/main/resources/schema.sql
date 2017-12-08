DROP TABLE IF EXISTS app_role;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS user_role;

CREATE TABLE app_role (
	id int NOT NULL AUTO_INCREMENT,
	description varchar(255) DEFAULT NULL,
	role_name varchar(255) DEFAULT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE app_user (
	id int NOT NULL AUTO_INCREMENT,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE user_role (
	user_id int NOT NULL NOT NULL,
	role_id int NOT NULL,
	CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES app_user (id),
	CONSTRAINT FKa68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES app_role (id)
);