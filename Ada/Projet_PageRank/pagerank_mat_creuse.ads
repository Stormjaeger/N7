with Ada.IO_Exceptions;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Matrice_Creuse;		use Matrice_Creuse;
with Vecteur;				use Vecteur;
with Texte_A_Graphe_Creuses;    use Texte_A_Graphe_Creuses;

package PageRank_Mat_Creuse is
    procedure Pagerank_C (File : in out Ada.Text_IO.File_Type; Taille_Graphe : in Integer; Alpha : in Float; K_max : in Integer; Epsilon : in Float; k : out Integer; Dernier_Vecteur : out T_Vecteur);     
end PageRank_Mat_Creuse;