# DeepDownLow #
> Projet de L3 Informatique visant à créer une IA sur un jeu donné.

----------

Ce projet consiste à créer une intelligence artificielle arrivant à jouer (et remporter) un maximum de parties au jeu "**Les Fous-Fous**".

Ce jeu est une variante du "[Mad Bishops](http://www.marksteeregames.com/Mad_Bishops_rules.pdf)" qui se joue sur un plateau 8x8 (au lieux du 10x10) et impose, pour les IA, un temps de réflexion maximum de 10 minutes par parties.

## Prérequis ##

Aucun prérequis n'est demandé, le projet est codé en Java et n'utilise que les packages de base, une JMV est donc tout ce qu'il faut.

Il est cependant recomandé de le lancer sur une machine 64 bits et ayant un CPU 4 coeurs (inutile d'en avoir plus) pour une question de vitesse.

## Fonctionnement ##
### Structure de données ###

La plateau est représenté sous la forme d'un bitboard. Il est composé de deux *long* : l'un représente les pions blancs, l'autres les pions noirs.
Les autres classes tels que les coups et les pions stockent simplement leurs emplacements sur ce plateau (allant de 0 à 63).

Le tout est assez compact et permet surtout d'utiliser des opérations bit à bit.
Chaque oppération est donc bien plus rapide à executer comparé à la manipulation d'un tableau d'entiers, mais cela demande d'avoir un CPU pouvant gérer les nombres sur 64 bits.

### IA en début de partie ###

### IA en milieux de partie ###

### IA en fin de partie ###

## Auteurs ##
- **Mark Steere** - Créateur du Mad Bishops
- **Philippe Chatalic** - Enseignant de l'option IA
- **Cédric Vachaudez** - Programmeur
- **Malik Kazi Aoual** - Programmeur