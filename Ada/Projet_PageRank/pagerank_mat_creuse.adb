with Ada.IO_Exceptions;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Matrice_Creuse;		use Matrice_Creuse;
with Vecteur;				use Vecteur;
with Texte_A_Graphe_Creuses;    use Texte_A_Graphe_Creuses;

package body PageRank_Mat_Creuse is
    procedure Pagerank_C (File : in out Ada.Text_IO.File_Type; Taille_Graphe : in Integer; Alpha : in Float; K_max : in Integer; Epsilon : in Float; k : out Integer; Dernier_Vecteur : out T_Vecteur) is

        M_S : T_Matrice_C(1..Taille_Graphe);
        M_S_A: T_Matrice_C(1..Taille_Graphe);

        M_E : T_Matrice_C(1..Taille_Graphe);
        M_E_A : T_Matrice_C(1..Taille_Graphe);



        Distance : Float := Epsilon + 1.0;

        Vecteur_suivant : T_Vecteur(1..Taille_Graphe);
        Vecteur_precedent : T_Vecteur(1..Taille_Graphe);
        Vecteur_tmp_1 : T_Vecteur(1..Taille_Graphe);
        Vecteur_tmp_2 : T_Vecteur(1..Taille_Graphe);


        Vecteurs_poids : array (0..K_max) of T_Vecteur(1..Taille_Graphe);


        begin
            k := 0;

            Creer_S(M_S, File);

            Mult_scal(M_S, Alpha, M_S_A);

            Initialiser(M_E, 1.0);

            Mult_scal(M_E, (1.0 - Alpha)/Float(Taille_Graphe), M_E_A);

            Afficher(M_E_A);

            Initialiser(Vecteur_precedent,1.0/Float(Taille_Graphe));

            while (k < K_max and Distance > Epsilon) loop

                Produit_Vect_Mat(Vecteur_precedent,M_S_A,Vecteur_tmp_1);

                Produit_Vect_Mat(Vecteur_precedent,M_E_A,Vecteur_tmp_2);

                Sommer(Vecteur_tmp_1,Vecteur_tmp_2,Vecteur_suivant);

                k := k + 1;

                Distance := Calculer_distance(Vecteur_precedent,Vecteur_suivant);

                Copie(Vecteur_suivant,Vecteur_precedent);

            end loop;

            Dernier_Vecteur := Vecteur_suivant;
        end Pagerank_C;
        
end PageRank_Mat_Creuse;