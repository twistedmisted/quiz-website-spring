delete from user;

insert into user (id, email, login, password, access_level) values
(default, 'user@gmail.com', 'user', '$2a$10$G8R8cDDA3GD2Jv9CM.cv8eSAYbsbGr66TXNSwn6Gl0LuqZxHFWuKq', 'user'),
(default, 'ban@gmail.com', 'ban', '$2a$10$G8R8cDDA3GD2Jv9CM.cv8eSAYbsbGr66TXNSwn6Gl0LuqZxHFWuKq', 'banned');