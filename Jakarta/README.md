# JEE: Jakarta

Partie du projet fait avec Jakarta.

## Base de données

Il est nécéssaire de créer une database "jee" et un utilisateur "jee":
```sql
CREATE DATABASE 'jee';
CREATE USER 'jee'@'jee' IDENTIFIED BY 'jee';
GRANT ALL PRIVILEGES ON jee.* TO 'jee'@'localhost';
```
## Nécéssités

- Maven
- Tomcat server v10+

## Lancer le projet 

### À la main

Il suffit de compiler:
```sh
mvn package
```
Puis de lancer le fichier [target/jee-ing2-gsi3-jakarta.war](./target/jee-ing2-gsi3-jakarta.war) avec tomcat.

À chaque modification il faut refaire cette manipulation.

### Avec IntelliJ Ultimate

Ouvrez le projet dans IJ Ultimate.  
Allez dans la structure du projet.
- Dans **Project Settings->Project**, sélectionnez **SDK** en `25`, ou installez `Oracle OpenJDK 25`.
- Dans **Project Settings->facets**:
  - Ajoutez une nouvelle facette `Hibernate`, avec une référence vers [/src/main/resources/hibernate.cfg.xml](./src/main/resources/hibernate.cfg.xml).
  - Ajoutez une nouvelle facette `web`, avec une référence vers [/src/main/webapp/WEB-INF/web.xml](./src/main/webapp/WEB-INF/web.xml). Ajoutez dans **Web Resource Directories** le dossier [/src/main/webapp](./src/main/webapp/). Dans **Source Roots**, cochez tout.
- Dans **Project Settings->Modules**, ajoutez un nouveau module nommé `jee`:
  - Ajoutez un module `web`. Dans **Deployment descriptors**, ajouter l'option `web.xml` qui devrait être proposé.
  - Ajoutez un module `Hibernate`, qui devrait correspondre à ce que vous avez remplis dans **facets**.
- Dans **project Settings->Artifacts**, ajoutez un nouvel artéfact `Web Application: Exploded > From modules` et sélectionnez le module `jee`. Nommez l'artéfact `jee:war exploded`.

Allez dans configuration **Run/Debug**, et ajoutez l'option `Tomcat Server`. Précisez votre dossier Tomcat. Notez l'URL dans la catégorie **Open browser**, ou mettez `http://localhost:8080/jee_war_exploded`. **JRE** en `Default`. **HTTP port** en `8080` et **JMX port** en `1099`. Dans **Before launch**, ajoutez un `Build`, puis ajoutez un `Build artifact` et sélectionnez `jee:war exploded`.  