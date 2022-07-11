# create table categories
# (
#     category_id int not null AUTO_INCREMENT,
#     category_name varchar(255) not null,
#     decription    varchar(255) not null,
#     image_url     varchar(255) not null,
#     PRIMARY KEY (category_id)
# );
#
# create table games
# (
#     game_id INT not null AUTO_INCREMENT,
#     name        varchar(255) not null,
#     description varchar(255) not null,
#     image_url   varchar(255) not null,
#     price       decimal      not null,
#     category_id int          not null,
#     PRIMARY KEY (game_id),
#     FOREIGN KEY (category_id) REFERENCES categories(category_id)
# );
#
# create table roles
# (
#     role_id int not null AUTO_INCREMENT,
#     role_name varchar(255) not null,
#     PRIMARY KEY (role_id)
# );
#
# create table users
# (
#     user_id int not null AUTO_INCREMENT,
#     email      varchar(255) not null,
#     first_name varchar(255) not null,
#     last_name  varchar(255) not null,
#     password   varchar(255) not null,
#     role       varchar(255) not null,
#     role_id    int          not null,
#     PRIMARY KEY (user_id),
#     FOREIGN KEY (role_id) REFERENCES roles(role_id)
# );