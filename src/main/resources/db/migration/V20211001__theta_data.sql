-- role
INSERT INTO role (id, name)
VALUES (1001, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (1002, 'ROLE_TENANT');
INSERT INTO role (id, name)
VALUES (1003, 'ROLE_ADMIN');

-- person: password -> 1234
INSERT INTO person (id, map_data, name, email, password, active, attachment_list_data, creator, editor,
                          created_by, created, updated_by, updated)
VALUES (1001, '{}', 'John Travolta', 'john@mail.com', '$2a$10$mtEAmwAl1SSg/cfuavxME.3wBqlsTSIv.jjdmq73k8TlHmPBTRCDi',
        true, '{}', null, null, 'system', '2021-10-04 09:42:29', 'system', '2021-10-04 09:42:29');
INSERT INTO person (id, map_data, name, email, password, active, attachment_list_data, creator, editor,
                          created_by, created, updated_by, updated)
VALUES (1002, '{}', 'Will Smith', 'will@mail.com', '$2a$10$MKtR6IhurqMaLZW4IaWdtugcqjAElDpnXcSkmG.cpHhYA2o1dOyGu', true,
        '{}', null, null, 'system', '2021-10-04 09:42:29', 'system', '2021-10-04 09:42:29');
INSERT INTO person (id, map_data, name, email, password, active, attachment_list_data, creator, editor,
                          created_by, created, updated_by, updated)
VALUES (1003, '{}', 'Jim Carry', 'jim@mail.com', '$2a$10$/JQTGUEkGGieCve9z14couDy1oy1qGD/g0UDW/J/IYqYsBrN2Abtu', true,
        '{}', null, null, 'system', '2021-10-04 09:42:29', 'system', '2021-10-04 09:42:29');
INSERT INTO person (id, map_data, name, email, password, active, attachment_list_data, creator, editor,
                          created_by, created, updated_by, updated)
VALUES (1004, '{}', 'Arnold Schwarzenegger', 'arnold@mail.com',
        '$2a$10$e0zU/jyKba32V8NuFpW07OdgnbWUIk.0UYt9UqDFXrlQuznPE31qy', true, '{}', null, null, 'system',
        '2021-10-04 09:42:29', 'system', '2021-10-04 09:42:29');

INSERT INTO person_roles (person_id, roles_id) VALUES (1001, 1001);
INSERT INTO person_roles (person_id, roles_id) VALUES (1001, 1002);
INSERT INTO person_roles (person_id, roles_id) VALUES (1002, 1002);
INSERT INTO person_roles (person_id, roles_id) VALUES (1003, 1003);
INSERT INTO person_roles (person_id, roles_id) VALUES (1004, 1001);
INSERT INTO person_roles (person_id, roles_id) VALUES (1004, 1002);
INSERT INTO person_roles (person_id, roles_id) VALUES (1004, 1003);