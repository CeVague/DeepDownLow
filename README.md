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

En début de partie c'est un dictionnaire d'ouverture qui est utilisé (pour les deux premiers coups), puis une recherche AlphaBeta à faible profondeur avec une heuristique simple cherchant à regrouper le plus possible des pions d'une couleur.

### IA en milieux de partie ###

En milieux de partie c'est un AlphaBeta incrémental qui est utilisé, exploitant le dernier coup trouvé pour élaguer un peu plus.
L'heuristique quant à elle est un mélange du nombre de pions, de leur alignement et leur regroupement.

### IA en fin de partie ###

À partir du moment où on sait que l'on va gagner, l'heuristique ne renvoie que 0, sauf si on est sur une feuille gagnante ou perdante (dans le but d'élaguer le plus possible).

Si on est perdant, on cherchera à chaque coup pour voir si on est toujours perdant. Si ce n'est pas le cas on joue le coup gagnant, sinon on se contente de jouer un bon coup à une profondeur assez faible pour éviter de voir qu'on est perdant.

## Auteurs ##
- **Mark Steere** - Créateur du Mad Bishops
- **Philippe Chatalic** - Enseignant de l'option IA
- **Cédric Vachaudez** - Programmeur
- **Malik Kazi Aoual** - Programmeur