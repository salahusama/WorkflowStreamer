CREATE DATABASE workflowstreamer;
USE workflowstreamer;

CREATE TABLE users (
	user_id		INT AUTO_INCREMENT,
	email		VARCHAR(100) UNIQUE,
	username	VARCHAR(50) UNIQUE,
	password	LONGTEXT,
	PRIMARY KEY (user_id)
);

CREATE TABLE teams (
	team_id 	INT AUTO_INCREMENT,
    name		VARCHAR(50),
	description	LONGTEXT,
    PRIMARY KEY (team_id)
);

CREATE TABLE roles (
	role_id 	INT AUTO_INCREMENT,
    title	 	VARCHAR(20),
	description	LONGTEXT,
    PRIMARY KEY (role_id)
);

CREATE TABLE user_teams (
	team_id 	INT,
    user_id 	INT,
	role_id		INT,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams (team_id)
);

CREATE TABLE projects (
	project_id 	INT AUTO_INCREMENT,
	creator_id 	INT,
	team_id     INT,
	name 		VARCHAR(50),
	description LONGTEXT,
	PRIMARY KEY (project_id),
	FOREIGN KEY (creator_id) REFERENCES users (user_id),
	FOREIGN KEY (team_id) REFERENCES teams (team_id)
);

CREATE TABLE stages (
	stage		VARCHAR(50),
	view_order  INT,
	PRIMARY KEY (stage)
);

CREATE TABLE tasks (
	task_id	    INT AUTO_INCREMENT,
	project_id	INT,
	creator_id	INT,
	stage		VARCHAR(50),
	title		LONGTEXT,
	description	LONGTEXT,
	priority    VARCHAR(10),
	est_work    INT,
	due_date    DATETIME,
	created_at	DATETIME,
	PRIMARY KEY (task_id),
	FOREIGN KEY (creator_id) REFERENCES users (user_id),
	FOREIGN KEY (project_id) REFERENCES projects (project_id),
	FOREIGN KEY (stage) REFERENCES stages (stage)
);

CREATE TABLE task_comments (
	comment_id INT AUTO_INCREMENT,
    task_id INT,
    creator_id INT,
    text LONGTEXT,

    PRIMARY KEY (comment_id),
    FOREIGN KEY (task_id) REFERENCES tasks (task_id),
    FOREIGN KEY (creator_id) REFERENCES users (user_id)
);

INSERT INTO stages VALUES
    ('Ice Box', 1),
    ('Back Log', 2),
    ('Ready', 3),
    ('In Progress', 4),
    ('In Review', 5),
    ('Done', 6),
    ('Closed', 7)
;

INSERT INTO roles (title, description) VALUES
    ('Manager', 1),
    ('Contributor', 2)
;