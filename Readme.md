# Nom de projet 
CodeGen
## Informations générales
Ce projet est un générateur de code (Class POJO : JAVA,JavaScript,PHP) à partir de JSON
	
## Les technologies
Le projet est créé avec:
* Spring boot  :2.0.8
* Jhipster     :5.8.2
* Maven        :3.3.9

	
## Installer
Pour exécuter ce projet :
importer les deux base de donnée (convertapp.sql && jhipster.sql )dans MYSQL. 
installer localement MAVEN.
lancez la commande : $npm install , Dans tous les dossiers du projet
`` `
$ cd jhipster-registry && mvnw   (port utilisé : 8761)
$ cd convert-app && mvnw         (port utilisé : 8082)
$ cd gatway && mvnw              (port utilisé : 8083)
`` `
