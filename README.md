# codeGenApi

Cette application a été générée à l'aide de JHipster 5.8.1. Vous pouvez trouver de la documentation et de l'aide à l' adresse [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).

Il s'agit d'une application "microservice" destinée à faire partie d'une architecture de microservice.

Cette application est configurée pour la découverte et la configuration de services avec le registre JHipster. Au lancement, il refusera de démarrer s'il ne parvient pas à se connecter au registre JHipster à l' adresse http: // localhost: 8761 .

## Développement

Pour démarrer l'api , exécutez simplement:

```bash
./mvnw
```

La valeur du port HTTP sur lequel s'exécute le serveur est 8082 par défaut

## Explications projet 

### Entités 

Pour notre API on a les classes de domaines suivantes :    
    - **baseClass**  : id , nom , imports  
    - **defaultCode** : id , boilerplate , ref_language_id , code_id , is_delete  
    - **fonctions** : id , nom , type , base_class_id   
    - **arguments** : id , nom , type , fonctions_id  
    - **langages**: id , nom , code , base_class_id  
    - **properties** : id , nom , type , base_class_id  
    - **refLangage** : id , langage  


### Fonctionnement du service : 

Pour le service on a opté d'utiliser la classe **DefaultCodeServiceImp** qui hérite de la classe **DefaultCodeService** . On lui passe une chaine de caractère ( Contenu de notre fichier Json ) .   
Dans cette classe on fait 2 choses :    
    - l'enregistrement des informations dans nos classes 
    - On fais l'appel aux services  : *ConvertToJava* , *ConvertToJS* , *ConvertToPHP*  qui nous retourne la chaine de caractère qui sera enregistrer, dans notre table **defaultCode**, avec l'id du langage approprié. 

### Le code : 

Pour le reste du code en utilisant des interfaces et leurs implementations pour avoir un **couplage faible**  .

Nous avons utilser un interface dans service *Convert* qui contient les 3 méthodes de convertions *ConvertToJava* , *ConvertToJS* , *ConvertToPHP* . Pour faciliter la modification du code à tous moment en définissant un contrat dans notre interface .







   




        


