# DeepDownLow #
> Projet de L3 Informatique pour créer une IA pour un jeu donné.

----------

Ce projet consiste à créer une intelligence artificielle pouvant jouer (et remporter) un maximum de parties au jeu "**Les Fous-Fous**".

Ce jeu est une variante du "[Mad Bishops](http://www.marksteeregames.com/Mad_Bishops_rules.pdf)". 
Il se joue en effet sur un plateau 8x8 au lieux du 10x10 d'origine et impose, pour les IA, de devoir réfléchir au maximum 10 minutes au cours de chaque partie.

## Prérequis ##

Aucun prérequis, le projet est codé en Java et n'utilise rien d'autres que les packages de base.

Il est cependant recomandé de le lancer sur une machine 64 bits et ayant un CPU 4 coeurs (inutile d'en avoir plus) pour une question de rapiditée.

## Fonctionnement ##
### Structure de données ###

La plateau est représenté sous la forme d'un bitboard. Il est donc composé de deux *long* : l'un représente les pions blancs, l'autres les pions noirs.
Les autres classes tels que les coups et les pions stockent simplement leurs emplacements sur ce plateau (allant de 0 à 63).

Le tout est assez compact et permet surtout d'utiliser des opérations bit à bit, bien plus rapide à executer que d'exploiter un tableau d'entiers. Mais cela demande d'avoir un CPU pouvant gérer les nombres sur 64 bits, ce qui n'est pas toujours le cas.

### IA en début de partie ###

### IA en milieux de partie ###

### IA en fin de partie ###