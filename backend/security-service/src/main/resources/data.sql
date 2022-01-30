INSERT INTO tbl_role (role, status) VALUES ('ROLE_USER','CREATED');
INSERT INTO `tbl_users` (USER_NAME, password, EMAIL, ENABLED, status, create_at) VALUES ('julio','$2a$10$Fl6xEdfU/qNtROLaxs/jZOMFHHUA8C0HZCHwl1R8RXXFKoYE73eJ6','julio@gmail.com',1,'CREATED','2022-01-29');
INSERT INTO `tbl_authorities` (user_id, role) VALUES ('julio','ROLE_USER');