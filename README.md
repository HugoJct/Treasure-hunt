#Ce dépôt Gitlab est dédié au développement du projet de programmation du semestre 4 (PI4).

"Chasse au trésor en réseau" by Vincent Cheval:
    Jeu de chasse au trésor sur une carte 2D, en réseau.
    
#Objectif : 
    L’objectif de ce projet est de créer un logiciel permettant de jouer à une chasse au trésor en réseau. 
    Le logiciel inclura:
-       une partie serveur qui génère les parties, accepte les connexions de client "humain" et peut aussi générer des joueurs "Ordinateurs".
-       une partie client permettant de se connecter à un serveur et de participer à la chasse au trésor.

##Réalisation technique : 
    Le projet doit normalement être réalisé en Java.
    Cela peut être discuté.

##Principe : 
    La chasse au trésor est modélisé par un plateau de jeu rectangulaire avec un nombre prédéfini de cases. Une case peut soit être vide, soit contenir un trésor, soit correspondre à un mur ou enfin correspondre à un
    trou. Les trésors ont plusieurs valeurs possibles (5, 10, 15 et 20).
    Au lancement de la partie, le serveur place aléatoirement sur des cases vides les joueurs. Les joueurs doivent alors se déplacer sur le plateau de jeu pour aller récupérer le plus de trésors avec les conditions suivantes :
-       Les déplacements ne peuvent être que verticaux ou horizontaux
-       Les joueurs ne peuvent traverser un mur
-       Deux joueurs ne peuvent se trouver sur la même case
-       Si un joueur tombe dans un trou, il est éliminé
-       Lorsque tous les trésors ont été récupérés, la partie est terminée ; le joueur ayant le plus de point gagne.

On considéra plusieurs modes de jeu:
-   Le speeding contest : Dans ce mode de jeu, tout le monde a la vision complète du plateau avec les trous, murs et trésors. Il faut juste être le plus rapide pour gagner. On interdira le mix entre joueurs "Humain" et "Ordinateur" pour ce mode.
    
-   Le tour par tour : A nouveau, tout le monde a la vision complète du plateau mais on doit attendre son tour pour jouer.
    
-   Le brouillard de guerre : Dans ce mode de jeu, les joueurs connaissent uniquement la position et la valeur des trésors. Chaque joueur peut voir les murs et joueurs dans un rayon de deux cases autour de lui. En revanche, il ne connait que le nombre de trous autour de lui (dans un rayon d’une case). Pour aider, les joueurs peuvent dépensés des points de trésors pour révéler :
            — les pièges autour de lui pendant 5 tours
            — une partie de la carte pendant 3 tours. Pendant ces trois 3 tours, le joueur peut voir la position des joueurs, les tours et les trésors encore présents.
        Le prix des deux commandes pourra être modifié à la génération de la carte.

##Méthodologie : 
    On commencera par l’élaboration des modes de jeu speeding contest et tour par tour.
    On donnera une spécification pour les communications entre clients et serveurs qu’il faudra suivre scrupuleusement. Cela permettra en l’occurrence de pouvoir utiliser des clients et serveurs implémentés par différentes personnes de participer au même jeu. Par example, la spécification inclue les points suivants :
-       la communication entre clients et serveurs se fera via socket ;
-       il n’y aura pas de communication directement entre clients, uniquement client-serveur ;
-       un serveur devra être capable d’accepter un nombre arbitraire de client avant de démarrer la partie ;
-       les clients et serveurs ne communiqueront que via une liste prédéfinie de commandes qui sera donnée au début du projet.
-       ...

    Vous serez libre de programmer comme vous le souhaitez la structure interne du client et du serveur, DU MOMENT QUE le client et le serveur satisfont la spécification. Néanmoins, certaines librairies, structures de données et algorithmes seront proposés/conseillés pour vous faciliter la tâche.
    Une représentation graphique du jeu ne sera pas obligatoire (visualisation en texte conviendra) mais elle sera la bienvenue.

##Objectifs minimaux : 
-   Il sera donc demandé au minium d’implémenter un serveur et un client (pour humain) qui suivront la spécification qui auquel on pourra jouer. 
-   Il faudra implémenter le speeding contest et le tour par tour.

##Objectifs supplémentaires : 
    Vous pourrez ensuite implémenter le mode de jeu brouillard de guerre, une représentation graphique et les joueurs "Ordinateur". Pour ces derniers, vous êtes libre de choisir comment implémenter l’intelligence artificielle des joueurs "Ordinateur". Si vous avez plusieurs idées,
vous pouvez alors faire des compétitions entre joueurs "Ordinateur" et évaluer vos performances. Dans ce cas, il ne sera pas nécessaire d’implémenter tous les modes de jeu.