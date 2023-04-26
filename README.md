# Projet DevOps
![example workflow](https://github.com/perringe/project_devops/actions/workflows/action_onmain.yml/badge.svg)
![example workflow](https://github.com/perringe/project_devops/actions/workflows/action_onbranch.yml/badge.svg)

## Table of Contents
1. [Fonctionnalitées](#fonctionnalitées)
2. [Choix des outils](#choix-des-outils)
3. [Workflow git](#workflow-git)
4. [FeedBack](#feedback)

# Fonctionnalitées
Notre service fourni toutes les fonctionnalités obligatoires demandés:
- Création de dataframe
- Affichage
- Sélection
- Statistiques

## Création de dataframe:  
Les labels des colonnes et des lignes peuvent être des int, des float, des chars ou des strings.  
Par constructeur sans spécifier d'index pour les lignes (par défaut: 0,1,2...)  
```java
Dataframe dataframe;
String[] labelColumns;
Object[][] objects;
labelColumns = new String[]{"Age", "Nationalité", "Sexe"};
objects = new Object[][]{ {35, 63, 39},{"Français", "Belge", "Suisse"},{'H', 'F', 'H'} };
dataframe = new Dataframe(labelColumns, objects);
```
Par constructeur en spécifiant des index pour les lignes (ici des strings: Personne 1, Personne2, Personne 3)
```java
Dataframe dataframe;
String[] labelColumns;
String[] labelLines;
Object[][] objects;
labelColumns = new String[]{"Age", "Nationalité", "Sexe"};
labelLines = new String[]{"Personne 1", "Personne 2", "Personne 3"};
objects = new Object[][]{ {35, 63, 39},{"Français", "Belge", "Suisse"},{'H', 'F', 'H'} };
dataframe = new Dataframe(labelColumns, objects, labelLines);
```
Par fichier csv en spécifiant un séparateur qui sépare les éléments dans le fichier
```java
Dataframe dataframe;
dataframe = Dataframe.read_csv("chemin_vers_fichier", "sep");
```
Par fichier csv où le séparateur est ","
```java
Dataframe dataframe;
dataframe = Dataframe.read_csv("chemin_vers_fichier");
```
Pour pouvoir lire le fichier csv, la structure est la suivante :
  - 1re ligne : nom des colonnes
  - 2e ligne : 1re ligne des éléments dans les colonnes
  - 3e ligne : 2e ligne des éléments dans les colonnes
  - Etc...


Exemple : On crée le dataframe suivant
```java
Dataframe dataframe;
String[] labelColumns;
Object[][] objects;
labelColumns = new String[]{"Age", "Nationalité", "Sexe"};
objects = new Object[][]{ {35, 63, 39},{"Français", "Belge", "Suisse"},{'H', 'F', 'H'} };
dataframe = new Dataframe(labelColumns, objects);
```
Pour obtenir le même dataframe. La structure du fichier sera donc
```csv
Age,Nationalite,Sexe
35,Français,H
63,Belge,F
39,Suisse,H
```
## Affichage d'un dataframe:  
Afficher l'intégralité d'un dataframe
```java
System.out.println(dataframe);
```
Afficher uniquement les n premières lignes
```java
System.out.println(dataframe.printFirstLines(n));
```

Afficher uniquement les n dernières lignes
```java
System.out.println(dataframe.printLastLines(n));
```
## Sélection dans un dataframe:
### Par sélection d'index ou de labels : iloc() et loc()
Sélectionner un sous-ensemble de lignes et/ou de colonnes à partir d'index
```java
// Sélectionne les colonnes d'indices 0 et 1 et la ligne d'indice 2
Dataframe dfi = dataframe.iloc([0, 1], [2]);
```
Sélectionner un sous-ensemble de lignes et/ou de colonnes à partir de labels
```java
// Sélectionne les colonnes de label "Age" et "Nationalité" et la ligne de label "Personne 3"
Dataframe dfl = dataframe.loc(["Age", "Nationalité"], ["Personne 3"]);
```

Pour sélectionner un sous-ensemble de lignes, le tableau d'index/labels des colonnes doit être vide et préciser les index/labels des lignes
```java
// Sélectionne la ligne d'indice 2
Dataframe dfi = dataframe.iloc([], [2]);
// Sélectionne la ligne de label "Personne 3"
Dataframe dfl = dataframe.loc([], ["Personne 3"]);
```
Pour sélectionner un sous-ensemble de colonnes, faire l'opération inverse
```java
// Sélectionne les colonnes d'indices 0 et 1
Dataframe dfi = dataframe.iloc([0, 1], []);>>>>>>> main
// Sélectionne les colonnes de labels "Age" et "Nationalité"
Dataframe dfl = dataframe.loc(["Age", "Nationalité"], []);
```
### Par filtrage de données : filterData()
Spécifier un label de colonnes, un comparateur parmi {"==", "!=", "<", ">", "<=", ">="} et une valeur de même type que les données que la colonne
```java
// Sélectionne les lignes où l'âge est inférieur ou égale à 55
Dataframe df = dataframe.filterData("Age", "<=", 55);
```

## Statistiques dans un dataframe: 
Ces trois méthodes renvoient le bon résultat uniquement si le label de la colonne spécifiée contient des entiers.  
Récupérer l'entier maximal à partir du label de la colonne  
```java
// L'âge de la personne la plus vieille
int maxValue = dataframe.findMaxIntegerValueOfColumn("Age");
```
Récupérer l'entier minimal à partir du label de la colonne  
```java
// L'âge de la personne la plus jeune
int minValue = dataframe.findMinIntegerValueOfColumn("Age");
```
Récupérer la moyenne d'âge à partir du label de la colonne  
```java
// L'âge moyen
float mean = dataframe.findIntegerMeanOfColumn("Age");
```

Il existe aussi trois autres fonctions permettant de faire la même chose sur des colonnes contenant des floats.
```java
findMaxFloatValueOfColumn(Object labelColumn);
findMaxFloatValueOfColumn(Object labelColumn);
findFloatMeanOfColumn(Object labelColumn);
```

# Choix des outils
Nous utilisons l'environnement de développement Visual Studio Code.  
Le projet est un projet Maven.  
GitHub Actions permet la création du Pipeline d'automatisation des tests. 
La couverture de code se fait à l'aide de Jacoco.  

# Workflow git
Mise en place d'une branche "dev" et de plusieurs branches features.  
Le but est de développer sur les branches features.   
A l'issu de l'implémentation de la fonctionnalité et de ses tests, nous poussons les modifications effectus sur la branche feature.  
A ce moment là, un pipeline qui build et exécute les tests est déclenché.  
Si les tests n'échouent pas, nous effectuons une pull request de la branche feature vers la branche dev.  
Une autre personne que le codeur passe en revue le code écrit, et fais des commentaires si besoin.  
La pull request est acceptée lorsque les tests sont validés et que l'ensemble des fonctionnalités désirés sont testés.  

Lorsque le contenu de la branche dev est une version qui est stable (c'est à dire, où tout les tests passent et ou le code coverage dépasse un certain pourcentage), nous déterminons qu'il est judicieux de merge la branche dev dans main.

note: Nous nous sommes rendu compte de l'utilité de la branche dev après avoir créer et push une première version de la création des dataframes.
C'est la raison pour laquelle il y a une pull request de dataframe_creation vers main la première semaine.  

# FeedBack
Les différents outils utilisés durant le projet sont nécessaires au bon fonctionnement et permettent le bon déroulement de ce dernier. Pour travailler dans les meilleures conditions.

Maven permet vraiment de choisir ou de créer sa propre architecture et de mettre en place au sein d'une entreprise une méthode de travail qui est adaptée à son besoin. 

GitHub Action permet non seulement d'automatiser les tests, mais aussi de forcer une réflexion importante sur les fonctionalitées pour pouvoir écrire les tests en amont.
