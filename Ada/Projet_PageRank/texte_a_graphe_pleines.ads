with Ada.Text_IO;           use Ada.Text_IO;
with Matrice_Pleine;        use Matrice_Pleine;

package Texte_A_Graphe_Pleines is

    procedure Creer_S(M_S : in out T_Matrice_P; File : in out Ada.Text_IO.File_Type);

    procedure Creer_G(M_G : in out T_Matrice_P; Alpha : in Float;  File : in out Ada.Text_IO.File_Type);
    
end Texte_A_Graphe_Pleines;