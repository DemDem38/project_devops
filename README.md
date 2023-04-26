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

# Choix des outils
Nous utilisons l'environnement de développement Visual Studio Code.  
Le projet est un projet Maven.  
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
