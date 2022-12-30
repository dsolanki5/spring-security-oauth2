INSERT into users(username,password,email,enabled) values ('siva','secret','siva@gmail.com',true);
INSERT into users(username,password,email,enabled) values ('user','secret','user@gmail.com',true);
INSERT into users(username,password,email,enabled) values ('admin','secret','admin@gmail.com',true);
INSERT into authorities values ('user','ROLE_USER');
INSERT into authorities values ('admin','ROLE_ADMIN');
INSERT into authorities values ('siva','ROLE_ADMIN');

