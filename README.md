# IFC export and visualisation

* Créer une bdd qui contient des données très simples pour afficher une dalle
  * longueur
  * largeur
  * hauteur
* Créer un service backend qui aura pour but de :
  * Récupérer des données métier (depuis une bdd ou en requête http)
  * Transformer ces données en fichier IFC
  * Fournir la spec IFC à un client
    * Sous forme de fichier ou en texte brut ?
* Créer un frontend qui aura pour but de :
  * Fournir des données métier à un serveur afin qu'il génère une structure IFC
  * Récupérer la structure IFC reçue du serveur
  * Visualiser la structure IFC en 3D

BDD : mysql
Serveur de transformation IFC : Java spring boot
Frontend : Vue Js 2
