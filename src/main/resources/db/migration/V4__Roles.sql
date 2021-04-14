ALTER TABLE users."user"
ADD COLUMN password varchar(40);

UPDATE users."user" SET role = 'ROLE_USER';
UPDATE users."user" SET role = 'ROLE_ADMIN' WHERE id = 1;

UPDATE users."user" SET username = 'admin' WHERE id = 1;
UPDATE users."user" SET password = 'pass'  WHERE id = 1;

