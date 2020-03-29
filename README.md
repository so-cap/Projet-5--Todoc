# **Projet  Todoc**

Todoc est une application de gestion de tâches. Mon objectif était de gérer la persistance de données 
en utilisant une base données.

Cette application permet :
- De créer des tâches selon 3 projets
- D'afficher la liste des tâches
- De les filtrer par nom de projet 
- De les trier par date de création
- Et de les supprimer.

Dans la branche "employee", se trouve une version de l'app proposant une connexion avec un compte employe.e 
ou un compte administrateur dans le cas où on voudrait gérer plus tard les données sur un serveur.
Vous pouvez trouver cette version sur Google PlayStore :
https://play.google.com/store/apps/details?id=com.cleanup.sophieca.todoc
Ci-dessous, les logins pour utiliser cette version:

  Login employés par défaut:
   - Compte 1 - email : sophie@email.com || mot de passe : mdp

   - Compte 2 - email: john@email.com
                || mot de passe : mdp
  
  Login Administrateur:
     email : admin@email.com
     || mot de passe : mdp


## Prerequis 
**Obtenir le projet:** 
Clicker sur "Clone or Download" en haut à droite du projet sur Github, télécharger et extraire
le fichier zip sur votre ordinateur.

**Logiciel nécessaire:**
Android Studio ([Comment installer Android Studio](https://developer.android.com/studio/install) )

Assurez vous d'installer un émulateur ou de configurer votre smartphone Android en mode développeur afin de pouvoir 
lancer l'application.

## Lancer le projet

Dans Android Studio, ouvrez le projet que vous venez de télécharger
et, si ce n'est pas fait automatiquement, cliquez sur "Sync project with Gradle Files" (menu "File")
puis clickez sur "Build Project" (menu "Build"). 

**Lancer l'application:** Cliquez sur le bouton "play" ou sélectionnez "Run 'app' " dans le menu "Run".

## Lancement des tests unitaires et UI

Vous trouverez les test unitaires dans le dossier "test" et les tests instrumentalisés
dans le dossier "androidTest". 


**Lancer tous les tests d'une même classe:** Click droit sur le nom de la classe
et appuyez sur "Run 'NomDeLaClasse'".  

**Lancer les tests un par un:** Click droit sur le nom de la méthode @Test que vous désirez lancer,
et appuyez sur ""Run 'NomDeLaMethode'". 