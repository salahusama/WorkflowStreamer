create table users (
	user_id INT auto_increment,
	email longtext,
    username varchar(20) unique,
    password longtext,
    primary key (user_id)
);

create table tasks (
	task_id INT auto_increment,
    title varchar(50),
    description longtext,
    creator_id INT,
    created_at datetime,
    PRIMARY KEY (task_id),
    FOREIGN KEY (creator) REFERENCES users (user_id)
);