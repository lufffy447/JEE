Membres du groupe:
	- Quentin Gagnaire
	- Maxime Sabbioni
	- Gustave Haulotte

Se connecter à l’url:
	http://localhost:8080/stepByStep/index.jsf

	utilisateur lambda : (test, test)
	utilisateur admin : (admin, password)	

	Les sources java sont dans la step 5 

FAIT : 
	
	- template avec header + body + footer
	- page d’index
	- page de menu (choisir l’option Recipes = recherche)
	- page de recherche de recette (si validation immédiate = tous les résultats)
	- page de résultats de recherche (pagination + accès au détail)
	- page de détail de la recette (possibilité de lire les commentaires + en poster si connecté)
	- page d’inscription (validators inclus)
	
	- Connexion à partir de toutes les pages dans le header
		- affichage de l’utilisateur connecté 
		- affichage du nombres d’utilisateurs actuellement connectés
	- devenir membre à partir de toutes les pages si non connecté
	
	- page d’administration (http://localhost:8080/stepByStep/admin.jsf)
		- utilisateur (admin, password)
	- page de choix de gestion des utilisateurs et recettes
	- page d’édition d’utilisateur (supprimer, éditer, créer)
	- page d’édition de recettes (supprimer, éditer, créer)
	- dernière connexion admin affichée


NON FAIT :

	- message temporaire ajax de connexion réussie ou non
	- Les deux boutons Emergency et Shop n’ont pas été implémentés (Clicker dessus ne fera rien on vous aura prévenu)
	- Pour la page menu.jsf (ainsi que pour le reste) on s’excuse on est pas des designers	
	