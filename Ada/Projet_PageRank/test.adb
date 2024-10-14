with Ada.IO_Exceptions;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Matrice_Creuse;		use Matrice_Creuse;
with Vecteur;				use Vecteur;
with Texte_A_Graphe_Pleines;	use Texte_A_Graphe_Pleines;

procedure test is

	Nom_Fichier : String := "test.txt";
	File : Ada.Text_IO.File_Type;
	Alpha : Float := 0.85;

	function Get_Taille_Graphe (File : in out Ada.Text_IO.File_Type; Nom_Fichier : in String) return Integer is
		Taille : Integer;
	begin
		open (File, In_File, Nom_Fichier);
		Get(File, Taille);
		return Taille;
	end Get_Taille_Graphe;


	Taille_Graphe : Integer := Get_Taille_Graphe(File, Nom_Fichier);

	M : T_Matrice_C (1..Taille_Graphe);

	M_out : T_Matrice_C (1..Taille_Graphe);

	V : T_Vecteur (1..Taille_Graphe);

	V_out : T_Vecteur (1..Taille_Graphe);

	
begin
	Initialiser_id(M);

	Afficher(M);

	Put(Obtenir_Longueur(M));

	Put(Obtenir_valeur(M,2,3));

	Definir_valeur(M,2,3,3.3);

	Afficher(M);

	Sommer(M,M,M_out);

	Afficher(M_out);

	Mult_scal(M,3.0, M_out);

	Afficher(M_out);

	Initialiser(V,2.0);

	Produit_Vect_Mat(V,M,V_out);

	Afficher(V_out);

end test;