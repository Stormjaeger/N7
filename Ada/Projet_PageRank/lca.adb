with SDA_Exceptions;         use SDA_Exceptions;
with Ada.Text_IO;            use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Ada.Unchecked_Deallocation;

package body LCA is

	--Instancier la procédure Free
	procedure Free is
			new Ada.Unchecked_Deallocation (Object => T_Cellule, Name => T_LCA);

	-- Initialiser une Sda.
	procedure Initialiser(Sda: out T_LCA) is
	begin
		--Initialiser la SDA comme un pointeur null.
		Sda := Null;
	end Initialiser;

	-- Détruire une Sda.
	procedure Detruire (Sda : in out T_LCA) is
	begin
		--Fonctionnement par récurrence
		if (Sda /= Null) then
			--Appel de la fonction sur la case suivante.
			Detruire(Sda.all.Suivant);

			--Liberation de la mémoire contenant la case courante.
			Free(Sda);
		end if;

	end Detruire;

	--Afficher la structure interne de la SDA.
	procedure Afficher_Debug (Sda : in T_LCA) is
	begin
		--Fonctionnement par récurrence.
		if (Sda = Null) then
			--Affichage de la fin de la SDA.
			Put("Null");

		else
			--Affichage de la case courante.
			Put("[");
			Afficher_Cle(Sda.all.Cle);
			Put(" : ");
			Afficher_Donnee(Sda.all.Valeur);
			Put("]-->");

			--Affichage de la suite de la SDA.
			Afficher_Debug(Sda.all.Suivant);
		end if;
	end Afficher_Debug;

	--Vérifier si une Sda est vide.
	function Est_Vide (Sda : T_LCA) return Boolean is
	begin
		--Renvoie True si la SDA est le pointeur null.
		return (Sda = Null);
	end;

	-- Obtenir le nombre d'éléments d'une Sda.
	function Taille (Sda : in T_LCA) return Integer is
	begin
		--Fonctionnement par récurrence.
		if (Est_Vide(Sda)) then
			return 0;
		else
			--Renvoi 1 + la taille de la suite de la SDA.
			return (1 + Taille(Sda.all.Suivant));
		end if;
	end Taille;

	-- Enregistrer une valeur associée à  une Clé dans une Sda.
	procedure Enregistrer (Sda : in out T_LCA ; Cle : in T_Cle ; Valeur : in T_Valeur) is
	begin
		--Fonctionnement par récurrence pour parcourir toute la SDA et ajouter l'élément à
		--la fin si il n'est pas déjà présent.
		if (Est_Vide(Sda)) then
			--Création de la cellule.
			Sda := new T_Cellule;

			--Remplissage de la cellule.
			Sda.all.Valeur := Valeur;
			Sda.all.Cle := Cle;
			Sda.all.Suivant := Null;
		else
			if (Sda.all.Cle = Cle) then
				--Remplacement de la valeur si la clé est déjà présente.
				Sda.all.Valeur := Valeur;
			else
				--Appel de la fonction sur la suite de la SDA si la clé n'est
				--pas présente.
				Enregistrer(Sda.all.Suivant,Cle,Valeur);
			end if;
		end if;
	end Enregistrer;

	-- Savoir si une Clé est présente dans une Sda.
	function Cle_Presente (Sda : in T_LCA ; Cle : in T_Cle) return Boolean is
	begin
		--Fonctionnement par récurrence.
		if (Est_Vide(Sda)) then
			--Cas où la clé n'a pas été trouvée.
			return False;
		else
			if (Sda.all.Cle = Cle) then
				--Cas où la clé est trouvée.
				return True;
			else
				--Appel de la fonction sur la suite de la SDA.
				return Cle_Presente(Sda.all.Suivant, Cle);
			end if;
		end if;
	end;

	-- Obtenir la valeur associée à  une Cle dans la Sda.
	function La_Valeur (Sda : in T_LCA ; Cle : in T_Cle) return T_Valeur is
	begin
		if (Est_Vide(Sda)) then
			--Cas où la clé n'a pas été trouvée.
			raise Cle_Absente_Exception;
		else
			if (Sda.all.Cle = Cle) then
				--Cas où la clé est trouvée.
				return Sda.all.Valeur;
			else
				--Appel de la fonction sur la suite de la SDA.
				return La_Valeur(Sda.all.Suivant, Cle);
			end if;
		end if;
	end La_Valeur;

	-- Supprimer la valeur associée à  une Clé dans une Sda.
	procedure Supprimer (Sda : in out T_LCA ; Cle : in T_Cle) is
		old_Sda : T_LCA;
	begin
		--Fonctionnement par récurrence.
		if (Est_Vide(Sda)) then
			--Cas où la clé n'a pas été trouvée.
			raise Cle_Absente_Exception;
		else
			if (Sda.all.Cle = Cle) then
				--Cas où la clé est trouvée.

				--Déclaration d'une variable tampon.
				old_Sda := Sda;

				--Raccordement du prédécéceur de la case à supprimer à son enfant.
				Sda := Sda.all.Suivant;

				--Libération de la mémoire.
				Free (old_Sda);
			else
				--Appel de la fonction sur la suite de la SDA.
				Supprimer(Sda.all.Suivant,Cle);
			end if;
		end if;

	end Supprimer;

	-- Appliquer un traitement (Traiter) pour chaque couple d'une Sda.
	procedure Pour_Chaque (Sda : in T_LCA) is
	begin
		--Fonctionnement par récurrence.
		if (Est_Vide(Sda)) then
			null;
		else
			begin
				--Traitement de la case.
				Traiter(Sda.all.Cle,Sda.all.Valeur);
			exception
				--Poursuite de programme si des exceptions sont élevées.
				when others =>
					null;
			end;
			--Appel de la fonction sur la suite de la SDA.
			Pour_Chaque(Sda.all.Suivant);
		end if;
	end Pour_Chaque;
end LCA;
