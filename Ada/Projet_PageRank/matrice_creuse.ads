with Ada.Text_IO;           use Ada.Text_IO;
with Vecteur;               use Vecteur;
with LCA;
-- Définition des matrices

package Matrice_Creuse is

    package LCA_int_float is
        new LCA (T_Cle=>Integer, T_Valeur => Float);
    use LCA_int_float;

	type T_Matrice_C is array(Integer RANGE <>) of T_LCA;


    -- Renvoie un élément M[i][j] de la matrice M donnée en argument
    function Obtenir_valeur(M : in T_Matrice_C; i : in Integer; j : in Integer) return Float;

    -- Remplace l'élément (i,j) de M par Val
    procedure Definir_valeur(M : in out T_Matrice_C; i : in Integer; j : in Integer; Val : in Float);

    -- Renvoie la taille de la matrice M
    function Obtenir_Longueur(M : in T_Matrice_C) return Integer;

	-- Initialiser une Matrice composée de x.
	procedure Initialiser(M : in out T_Matrice_C; X : in Float);

    -- Initialiser une matrice identité.
	procedure Initialiser_id(M : in out T_Matrice_C);

    -- Sommer 2 matrices
    procedure Sommer(M1 : in T_Matrice_C; M2 : in T_Matrice_C; M_out : out T_Matrice_C);

    -- Produit de 2 matrices
    procedure Produit(M1 : in T_Matrice_C; M2 : in T_Matrice_C; M_out : out T_Matrice_C);

    -- Multiplication d'une matrice par un scalaire
    procedure Mult_scal(M : in T_Matrice_C; C : in Float; M_out : out T_Matrice_C);
    
    -- Produit d'un vecteur ligne de taille n par une matrice nxn à droite
    procedure Produit_Vect_Mat(V : in T_Vecteur; M : in T_Matrice_C; V_out : out T_Vecteur);

    -- Affichage d'une matrice
    procedure Afficher(M : in T_Matrice_C);

end Matrice_Creuse;