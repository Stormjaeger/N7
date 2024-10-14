with Vecteur;               use Vecteur;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;

package body Ecriture_Fichier is

procedure creer_prw (F : in out Ada.Text_IO.File_Type; Nom_Fichier_out : in String; Taille_Graphe: in Integer; alpha : in Float; k : in Integer; V_poids : in T_Vecteur) is
    begin
    Create (F, Out_File, Nom_Fichier_out & ".prw");

        Put(F, Taille_Graphe, 1);
        Put(F, Alpha, 2, 15, 0);
        Put(F, k, 4);
        
        for i in 1..Taille_Graphe loop
            New_Line(F);
            Put(F,V_poids(i), 1, 15, 0);
        end loop;
            
        Close(F);
end creer_prw;

procedure creer_pr(F : in out Ada.Text_IO.File_Type; Nom_Fichier_out : in String; Taille_Graphe: in Integer; V_indices : in T_Vecteur) is 
    begin
    Create (F, Out_File, Nom_Fichier_out & ".pr");
        
        for i in 1..Taille_Graphe loop
            Put(F,Integer(V_indices(i)),1);
            New_Line(F);
        end loop;
            
        Close(F);
end creer_pr;

end Ecriture_Fichier;