with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;



package body TH is


	procedure Initialiser_th(Sda: out T_TH) is
	begin
        for i in Sda'Range loop
            Initialiser(Sda(i));
        end loop;

	end Initialiser_th;


	procedure Detruire_th (Sda : in out T_TH) is
	begin

		for i in Sda'Range loop
            Detruire(Sda(i));
        end loop;

	end Detruire_th;


	procedure Afficher_Debug_th (Sda : in T_TH) is

		procedure Afficher_debug_lca is 
			new Afficher_Debug(Afficher_Cle_th,Afficher_Donnee_th);

		begin

			for i in Sda'Range loop
                Put(i);
                Put(" : ");
                Afficher_Debug_lca(Sda(i));
				New_Line;
            end loop;

		end Afficher_Debug_th;


	function Est_Vide_th (Sda : T_TH) return Boolean is
        vide : Boolean := True;
	begin
        for i in Sda'Range loop
            vide := vide or Est_vide(Sda(i));
        end loop;
        return vide;
	end Est_vide_th;


	function Taille_th (Sda : in T_TH) return Integer is
        t : Integer := 0;
        begin 
            for i in Sda'Range loop
                t := t + Taille(Sda(i));
            end loop;
        return t;
	end Taille_th;


	procedure Enregistrer_th (Sda : in out T_TH ; Cle : in T_Cle_th ; Valeur : in T_Valeur_th) is
        new_cle : Integer;
	begin

        new_cle := hachage(Cle);
        Enregistrer(Sda(new_cle),Cle,Valeur);

	end Enregistrer_th;


	function Cle_Presente_th (Sda : in T_TH ; Cle : in T_Cle_th) return Boolean is
        new_cle : Integer;
	begin
		new_cle := hachage(Cle);
        return Cle_Presente(Sda(new_cle), Cle);

	end Cle_Presente_th;


	function La_Valeur_th (Sda : in T_TH ; Cle : in T_Cle_th) return T_Valeur_th is
        new_cle : Integer;
	begin
		new_cle := hachage(Cle);
        return La_Valeur(Sda(new_cle), Cle);

	end La_Valeur_th;


	procedure Supprimer_th(Sda : in out T_TH ; Cle : in T_Cle_th) is
    new_cle : Integer;
	begin
		new_cle := hachage(Cle);
        Supprimer(Sda(new_cle), Cle);
	end Supprimer_th;


	procedure Pour_Chaque_th (Sda : in T_TH) is
		procedure Pour_Chaque_lca is 
			new Pour_Chaque(Traiter_th);

		begin
			for i in Sda'Range loop
                Pour_Chaque_lca(Sda(i));
            end loop;
	end Pour_Chaque_th;


end TH;
