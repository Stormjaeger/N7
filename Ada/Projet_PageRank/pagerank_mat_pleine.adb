with Ada.IO_Exceptions;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Matrice_Pleine;		use Matrice_Pleine;
with Vecteur;				use Vecteur;
with Texte_A_Graphe_Pleines;    use Texte_A_Graphe_Pleines;

package body PageRank_Mat_Pleine is
    procedure Pagerank_P (File : in out Ada.Text_IO.File_Type; Taille_Graphe : in Integer; Alpha : in Float; K_max : in Integer; Epsilon : in Float; k : out Integer; Dernier_Vecteur : out T_Vecteur) is

        M_G : T_Matrice_P(1..Taille_Graphe,1..Taille_Graphe);
        Distance : Float := Epsilon + 1.0;

        Vecteurs_poids : array (0..K_max) of T_Vecteur(1..Taille_Graphe);


        begin
            k := 0;

            Creer_G(M_G, Alpha, File);

            Afficher(M_G);

            Initialiser(Vecteurs_poids(0),1.0/Float(Taille_Graphe));

            while (k < K_max and Distance > Epsilon) loop

                Produit_Vect_Mat(Vecteurs_poids(k),M_G,Vecteurs_poids(k+1));

                k := k + 1;

                Distance := Calculer_distance(Vecteurs_poids(k),Vecteurs_poids(k-1));

            end loop;

            Dernier_Vecteur := Vecteurs_poids(k);
        end Pagerank_P;
        
end PageRank_Mat_Pleine;