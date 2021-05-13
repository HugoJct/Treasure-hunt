# PI4 - Chasse au trésor

**Ce dépôt Gitlab est dédié au développement du projet de programmation du semestre 4 (PI4).**

## Présentation

L'objectif de ce projet était de réaliser un logiciel composé de deux parties permettant de jouer à un jeu de chasse au trésor en réseau. Les deux parties du logiciel sont les suivantes: 
*  Une partie serveur chargée de gérer les connexions des clients, de générer les cartes et partie de jeu.
*  Une partie client pouvant se connecter au serveur afin de participer à la chasse au trésor.

Ce projet a été réalisé entièrement en JAVA avec Gradle comme outil de développement.

## Principe: 
La chasse au trésor est modélisée par un plateau de jeu rectangulaire composé de cases sur lesquelles sont placées trois types de tuiles. Les différents types de tuiles sont les suivants:
*  Les murs: le joueur ne peut pas marcher sur ces tuiles. Elles ont pour but de définir le labyrinthe dans lequel évoluent les joueurs.
*  Les trous: le joueur meurt lorsqu'il marche sur ces tuiles.
*  Les trésors: le joueur récupère la valeur du trésor en marchant dessus, ce après quoi le trésor disparaît.

Chaque case peut également être vide pour permettre au joueur d'évoluer sur la carte.

Les joueurs ne peuvent se déplacer que verticalement ou horizontalement.

La partie se termine lorsque tous les trésors ont été récupérés ou lorsque tous les joueurs sont morts.

    ![alt text](ReadMeAttachments/ "Plateau de jeu et contrôleur de jeu")

## Modes de jeu:

Le serveur propose de créer des parties avec des modes de jeu différents:
*  Le mode **Speeding-Contest**: chaque joueur se déplace quand il veut, il suffit d'être le plus rapide pour gagner
*  Le mode **Tour-par-tour**: Les joueurs jouent chacun leur tour et un joueur ne peut se déplacer que lors de son tour.

##Interface graphique:

Le projet possède une interface graphique permettant de visualiser et de se repérer sur la carte le plus agréablement possible (voir plus haut), il possède également une interface permettant de contrôler les déplacements du personnage (voir plus haut) et une interface permettant de lister les parties existantes sur le serveur, les rejoindre ou bien en créer une toute nouvelle.

    ![alt text](ReadMeAttachments/game_management_UI.PNG "Interface de gestion des parties")

## Instructions de compilation

 **Compilation du serveur**:
*  `make server`: lance le serveur sur le port par défaut (12345)
*  `./gradlew :server:run --console=plain --args="<lien vers le fichier>"`: lance le serveur avec le fichier de configuration spécifié
*  Syntaxe du fichier de configuration du serveur: 

```
{
  "port": 12345
}
```

**Compilation du client**:
*  `make client`: lance le client avec le fichier de configuration par défaut
*  `./gradlew :client:run --console=plain --args="<lien vers le fichier>"`: lance le client avec le fichier de configuration spécifié
*  Syntaxe du fichier de configuration du client:
```
{
  "name": "Example",
  "ip": "127.0.0.1",
  "port": 12345
}
```

##Commandes: 

