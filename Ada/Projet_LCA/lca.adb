with Ada.Unchecked_Deallocation;
with Ada.Text_IO;           use Ada.Text_IO;
with SDA_Exceptions; 		use SDA_Exceptions;
 

package body LCA is

	procedure Free is
		new Ada.Unchecked_Deallocation (Object => T_Cellule, Name => T_LCA);

	procedure Initialiser(Sda: out T_LCA) is
	begin
		Sda := Null;
	end Initialiser;

	procedure Detruire (Sda : in out T_LCA) is
	begin

		if (Sda /= Null) then
			Detruire(Sda.all.Suivant);
			Free(Sda);
		end if;

	end Detruire;

	procedure Afficher_Debug (Sda : in T_LCA) is
		begin
			Put("--");
			if Est_vide(Sda) then 
				Put("E");
			else 
				Put(">[");
				Afficher_Cle(Sda.all.Cle);
				Put(" : ");
				Afficher_Donnee(Sda.all.Valeur);
				Put("]");
				Afficher_Debug(Sda.all.Suivant);
			end if;


		end Afficher_Debug;


	function Est_Vide (Sda : T_LCA) return Boolean is
	begin
		return (Sda = null);
	end;

	function Taille (Sda : in T_LCA) return Integer is
	begin

		if not(Est_vide(Sda)) then
			return 1 + Taille(Sda.all.Suivant);
		else 
			return 0;
		end if;
		
	end Taille;


	procedure Enregistrer (Sda : in out T_LCA ; Cle : in T_Cle ; Valeur : in T_Valeur) is
	begin
		if Est_vide(Sda) then 
			Sda := new T_Cellule;
			Sda.all.Valeur := Valeur;
			Sda.all.Cle := Cle;
			Sda.all.Suivant := Null;

		elsif Cle = Sda.all.Cle then

			Sda.all.Valeur := Valeur;

		else
			Enregistrer(Sda.all.suivant,Cle,Valeur);
		end if;
	end Enregistrer;


	function Cle_Presente (Sda : in T_LCA ; Cle : in T_Cle) return Boolean is
	begin
		if Est_vide(Sda) then
			return False;
		else 
			if Sda.all.Cle = Cle then 
				return true;
			else
				return Cle_Presente(Sda.all.Suivant,Cle);
			end if;
		end if;
	end;


	function La_Valeur (Sda : in T_LCA ; Cle : in T_Cle) return T_Valeur is
	begin
		if Est_vide(Sda) then
			raise Cle_Absente_Exception;
		else 
			if Sda.all.Cle = Cle then
				return(Sda.all.Valeur);
			else 
				return La_Valeur(Sda.all.Suivant,Cle);
			end if;
		end if;
	end La_Valeur;

	procedure Supprimer (Sda : in out T_LCA ; Cle : in T_Cle) is

		copy_sda : T_LCA;	
		
		begin
			if Est_vide(Sda) then
				raise Cle_Absente_Exception;
			else
				if (Sda.all.Cle = Cle) then 
					copy_sda := Sda;
					Sda := Sda.all.Suivant;
					Free(copy_sda);
				else	
					Supprimer(Sda.all.Suivant,Cle);
				end if;
			end if;
	end Supprimer;


	procedure Pour_Chaque (Sda : in T_LCA) is
		begin
			if Est_Vide(Sda) then 
				Null;
			else
				begin
					Traiter(Sda.all.Cle,Sda.all.Valeur);
				exception 
					when others => 
						null;
				end;
				Pour_Chaque(Sda.all.Suivant);
			end if;
	end Pour_Chaque;


end LCA;
