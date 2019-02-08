CREATE TABLE users (
	user_id		INT,
	email		VARCHAR(100) UNIQUE,
	username	VARCHAR(50) UNIQUE,
	password	LONGTEXT,
	PRIMARY KEY (user_id)
);

CREATE TABLE user_stages (
	user_id		INT,
	stage		VARCHAR(50),
	view_order  INT,
	PRIMARY KEY (user_id, stage)
);

CREATE TABLE projects (
	project_id	INT,
	creator_id	INT,
	name		VARCHAR(50),
	description	LONGTEXT,
	PRIMARY KEY (project_id),
	FOREIGN KEY (creator_id) REFERENCES users (user_id)
);

CREATE TABLE tasks (
	task_id	    INT,
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
	FOREIGN KEY (creator_id, stage) REFERENCES user_stages (user_id, stage)
);
