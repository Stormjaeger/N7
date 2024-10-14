with Ada.Text_IO;           use Ada.Text_IO;
with Matrice_Creuse;        use Matrice_Creuse;


package Texte_A_Graphe_Creuses is

    procedure Creer_S(M_S : in out T_Matrice_C; File : in out Ada.Text_IO.File_Type);

    procedure Creer_G(M_G : in out T_Matrice_C; Alpha : in Float;  File : in out Ada.Text_IO.File_Type);
    
end Texte_A_Graphe_Creuses;