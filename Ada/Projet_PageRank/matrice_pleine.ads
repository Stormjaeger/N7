with Ada.Text_IO;           use Ada.Text_IO;
with Vecteur;               use Vecteur;

-- Définition des matrices

package Matrice_Pleine is

	type T_Matrice_P is array(Integer RANGE <>, Integer RANGE <>) of Float;


    -- Renvoie un élément M[i][j] de la matrice M donnée en argument
    function Obtenir_valeur(M : in T_Matrice_P; i : in Integer; j : in Integer) return Float;

    -- Remplace l'élément (i,j) de M par Val
    procedure Definir_valeur(M : in out T_Matrice_P; i : in Integer; j : in Integer; Val : in Float);

    -- Renvoie la taille de la matrice M
    function Obtenir_Longueur(M : in T_Matrice_P) return Integer;

	-- Initialiser une Matrice composée de x.
	procedure Initialiser(M : in out T_Matrice_P; X : in Float);

    -- Initialiser une matrice identité.
	procedure Initialiser_id(M : in out T_Matrice_P);

    -- Sommer 2 matrices
    procedure Sommer(M1 : in T_Matrice_P; M2 : in T_Matrice_P; M_out : out T_Matrice_P);

    -- Produit de 2 matrices
    procedure Produit(M1 : in T_Matrice_P; M2 : in T_Matrice_P; M_out : out T_Matrice_P);

    -- Multiplication d'une matrice par un scalaire
    procedure Mult_scal(M : in T_Matrice_P; C : in Float; M_out : out T_Matrice_P);

    -- Produit d'un vecteur ligne de taille n par une matrice nxn à droite
    procedure Produit_Vect_Mat(V : in T_Vecteur; M : in T_Matrice_P; V_out : out T_Vecteur);

    -- Affichage d'une matrice
    procedure Afficher(M : in T_Matrice_P);

end Matrice_Pleine;