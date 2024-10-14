with Ada.Text_IO;           use Ada.Text_IO;

-- Définition des vecteurs

package Vecteur is

	type T_Vecteur is array(Integer RANGE <>) of Float;

    -- Renvoie un élément V[i] du vecteur V donné en argument
    function Obtenir_valeur(V : in T_Vecteur; i : in Integer) return Float;

    -- Remplace l'élément i de V par Val
    procedure Definir_valeur(V : in out T_Vecteur; i : in Integer; Val : in Float);

    -- Renvoie la taille du vecteur V
    function Obtenir_Longueur(V : in T_Vecteur) return Integer;

	-- Initialiser un Vecteur composé de x.
	procedure Initialiser(V : in out T_Vecteur; X : in Float);

    -- Sommer 2 vecteurs
    procedure Sommer(V1 : in T_Vecteur; V2 : in T_Vecteur; V_out : out T_Vecteur);

    -- Multiplication d'un vecteur par un scalaire
    procedure Mult_scal(V : in T_Vecteur; C : in Float; V_out : out T_Vecteur);

    -- Ecart entre 2 vecteurs
    function Calculer_distance(V1 : in T_Vecteur; V2 : in T_Vecteur) return Float;

    -- Crreer le vecteur trié et le vecteur des indices
    procedure Creer_Vecteurs_Finaux(V : in T_Vecteur; V_poids : out T_Vecteur; V_indices : out T_Vecteur);

    --Copier un vecteur dans un autre
    procedure Copie(V1 : in T_Vecteur; V2 : in out T_Vecteur);
    
    -- Affichage d'un vecteur
    procedure Afficher(V : in T_Vecteur);



end Vecteur;