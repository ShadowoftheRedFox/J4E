# JEE: Jakarta

Partie du projet fait avec Jakarta.

## Sommaire
- [JEE: Jakarta](#jee-jakarta)
  - [Sommaire](#sommaire)
  - [Base de données](#base-de-données)

## Base de données

Il est nécéssaire de créer une database "jee" et un utilisateur "jee":
```sql
CREATE DATABASE 'jee';
CREATE USER 'jee'@'jee' IDENTIFIED BY 'jee';
GRANT ALL PRIVILEGES ON jee.* TO 'jee'@'localhost';
```

Table:
```sql
CREATE TABLE rank (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    description VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_rank_user FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE user (
    id int not null,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,

    PRIMARY KEY ("id")
);

CREATE TABLE department (
    id INT  PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE project (
    id INT  PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status ENUM('ongoing', 'completed', 'canceled') NOT NULL
);

CREATE TABLE payslip (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hour DECIMAL(5,2) NOT NULL,
    wage DECIMAL(10,2) NOT NULL,
    bonus DECIMAL(10,2) DEFAULT 0,
    malus DECIMAL(10,2) DEFAULT 0,
    payment_date DATE NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user(id)
);
```

- rank (#id, description)
- user (#id, firstname, lastname)
- department (#id, name)
- project (#id, name, status(**ENUM**))
- payslip (#id, user_id, hour, wage, bonus, malus, date)

- user_rank (#id, user_id, rank_id)
- user_department (#id, department_id, user_id, role(**ENUM**))
- user_project (#id, project_id, user_id, role(**ENUM**))