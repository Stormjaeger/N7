-- Définition de structures de données associatives sous forme d'une table de hachage
-- table de hachage (TH).
with LCA;

generic
    Capacite : Integer;
    type T_Valeur_th is private;
    type T_Cle_th is private;
    with function hachage(Cle : T_Cle_th) return Integer;

package TH is

	-- Définition du type LCA générique
    package LCA_th is 
        new LCA(T_Cle => T_Cle_th, T_Valeur => T_Valeur_th);
    use LCA_th;

	type T_TH is limited private;

	-- Initialiser une Sda.  La Sda est vide.
	procedure Initialiser_th(Sda: out T_TH) with
		Post => Est_Vide_th (Sda);


	-- Détruire une Sda.  Elle ne devra plus être utilisée.
	procedure Detruire_th (Sda : in out T_TH);


	-- Est-ce qu'une Sda est vide ?
	function Est_Vide_th (Sda : T_TH) return Boolean;


	-- Obtenir le nombre d'éléments d'une Sda. 
	function Taille_th (Sda : in T_TH) return Integer with
		Post => Taille_th'Result >= 0
			and (Taille_th'Result = 0) = Est_Vide_th (Sda);


	-- Enregistrer une valeur associée à une Clé dans une Sda.
	-- Si la clé est déjà présente dans la Sda, sa valeur est changée.
	procedure Enregistrer_th (Sda : in out T_TH ; Cle : in T_Cle_th ; Valeur : in T_Valeur_th) with
		Post => Cle_Presente_th(Sda, Cle) and (La_Valeur_th(Sda, Cle) = Valeur)   -- valeur insérée
				and (not (Cle_Presente_th(Sda, Cle)'Old) or Taille_th(Sda) = Taille_th(Sda)'Old)
				and (Cle_Presente_th (Sda, Cle)'Old or Taille_th (Sda) = Taille_th (Sda)'Old + 1);

	-- Supprimer la valeur associée à une Clé dans une Sda.
	-- Exception : Cle_Absente_Exception si Clé n'est pas utilisée dans la Sda
	procedure Supprimer_th (Sda : in out T_TH ; Cle : in T_Cle_th) with
		Post =>  Taille_th (Sda) = Taille_th (Sda)'Old - 1 -- un élément de moins
			and not Cle_Presente_th (Sda, Cle);         -- la clé a été supprimée


	-- Savoir si une Clé est présente dans une Sda.
	function Cle_Presente_th (Sda : in T_TH; Cle : in T_Cle_th) return Boolean;


	-- Obtenir la valeur associée à une Cle dans la Sda.
	-- Exception : Cle_Absente_Exception si Clé n'est pas utilisée dans l'Sda
	function La_Valeur_th (Sda : in T_TH ; Cle : in T_Cle_th) return T_Valeur_th;


	-- Appliquer un traitement (Traiter) pour chaque couple d'une Sda.
	generic
		with procedure Traiter_th (Cle : in T_Cle_th; Valeur: in T_Valeur_th);
	procedure Pour_Chaque_th (Sda : in T_TH);

	-- Afficher la Sda en révélant sa structure interne.
	-- Voici un exemple d'affichage.
	-- -->["un" : 1]-->["deux" : 2]-->["trois" : 3]-->["quatre" : 4]--E
	generic
		with procedure Afficher_Cle_th (Cle : in T_Cle_th);
		with procedure Afficher_Donnee_th (Valeur : in T_Valeur_th);
	procedure Afficher_Debug_th (Sda : in T_TH);


private

	-- Définition du type T_TH
	type T_TH is array (1..Capacite) of T_LCA;

end TH;
