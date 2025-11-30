# JEE: SpringBoot

Partie du projet fait avec SpringBoot.

## Nécéssités

- Maven
- NodeJS 22.20.0
- npm 11.6.2

## Base de données

Il est nécéssaire de créer une database "jeee" et un utilisateur "jee":
```sql
CREATE DATABASE 'jeee';
CREATE USER 'jee'@'jee' IDENTIFIED BY 'jee';
GRANT ALL PRIVILEGES ON jee.* TO 'jee'@'localhost';
```

## Mise en place

```sh
# installation des dépendances maven
mvn install
# installation du runner d'Angular
npm i -g @angular/cli
# installation des dépendances de NodeJS
npm i
```

## Lancer le projet

```sh
# lance le backend
mvn spring-boot:run
# dans un autre terminal, lance le frontend
ng serve
```