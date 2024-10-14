with Vecteur;				use Vecteur;
with Ada.Text_IO;			use Ada.Text_IO;

package Ecriture_Fichier is

    procedure creer_prw (F : in out Ada.Text_IO.File_Type; Nom_Fichier_out : in String; Taille_Graphe: in Integer; alpha : in Float; k : in Integer; V_poids : in T_Vecteur);

    procedure creer_pr(F : in out Ada.Text_IO.File_Type; Nom_Fichier_out : in String; Taille_Graphe: in Integer; V_indices : in T_Vecteur);

end Ecriture_Fichier;