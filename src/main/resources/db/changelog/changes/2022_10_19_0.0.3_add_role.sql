insert into role(role) values
    ('ROLE_ADMIN'),('ROLE_USER'),('ROLE_MODERATOR');
insert into users_roles(users_id, roles_id) VALUES
    (1,1),(1,2),(1,3);