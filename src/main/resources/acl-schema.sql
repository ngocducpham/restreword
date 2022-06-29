create table IF NOT EXISTS system_message
(
    id      integer not null,
    content varchar(255),
    primary key (id)
    );

-- create table if not exists my_role
-- (
--     id   integer not null,
--     name varchar(255),
--     primary key (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS my_user
-- (
--     id       integer not null auto_increment,
--     username varchar(255),
--     password varchar(255),
--     primary key (id)
-- );
--
-- create table if not exists user_role
-- (
--     user_id integer not null,
--     role_id integer not null
-- );

CREATE TABLE IF NOT EXISTS acl_sid
(
    id        int          NOT NULL AUTO_INCREMENT,
    principal int          NOT NULL,
    sid       varchar(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_uk_1 UNIQUE (sid, principal)
    );

CREATE TABLE IF NOT EXISTS acl_class
(
    id    int          NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_uk_2 UNIQUE (class)
    );

CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  int NOT NULL AUTO_INCREMENT,
    acl_object_identity int NOT NULL,
    ace_order           int NOT NULL,
    sid                 int NOT NULL,
    mask                int NOT NULL,
    granting            int NOT NULL,
    audit_success       int NOT NULL,
    audit_failure       int NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order)
    );

CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 int NOT NULL AUTO_INCREMENT,
    object_id_class    int NOT NULL,
    object_id_identity int NOT NULL,
    parent_object      int DEFAULT NULL,
    owner_sid          int DEFAULT NULL,
    entries_inheriting int NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity)
    );

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

-- alter table user_role
--     add foreign key (user_id) references my_user (id);
--
-- alter table user_role
--     add foreign key (role_id) references my_role (id);