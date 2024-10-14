with Ada.IO_Exceptions;
with Ada.Text_IO;			use Ada.Text_IO;
with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Ada.Command_line;		use Ada.Command_line;
with Matrice_Creuse;		use Matrice_Creuse;
with Matrice_Pleine;		use Matrice_Pleine;
with Vecteur;				use Vecteur;
with Texte_A_Graphe_Pleines;    use Texte_A_Graphe_Pleines;
with Analyse_Arguments;     use Analyse_Arguments;
with Ecriture_Fichier;      use Ecriture_Fichier;
with PageRank_Mat_Pleine;   use PageRank_Mat_Pleine;
with PageRank_Mat_Creuse;   use PageRank_Mat_Creuse;

procedure PageRank is

    Tab_Arg : T_arg := Tableaux_Arguments;

    Nom_Fichier_in : String := Recup_Dernier_Argument;
	File : Ada.Text_IO.File_Type;
	Alpha : Float := float'Value(To_String(Tab_Arg(1)));
    K_max : Integer := integer'Value(To_String(Tab_Arg(2)));
    Epsilon : Float := float'Value(To_String(Tab_Arg(3)));
    Nom_Fichier_out : String := To_String(Tab_Arg(6));
    Utiliser_Creuse : Integer := integer'Value(To_String(Tab_Arg(5)));
    Utiliser_Pleine : Integer := integer'Value(To_String(Tab_Arg(4)));

	function Get_Taille_Graphe (File : in out Ada.Text_IO.File_Type; Nom_Fichier : in String) return Integer is
		Taille : Integer;
	begin
		Open (File, In_File, Nom_Fichier);
		Get(File, Taille);
		return Taille;
	end Get_Taille_Graphe;

    Taille_Graphe : constant Integer := Get_Taille_Graphe(File,Nom_Fichier_in & ".net");

    Dernier_Vecteur : T_Vecteur(1..Taille_Graphe);

    V_poids : T_Vecteur(1..Taille_Graphe);
    V_indices : T_Vecteur(1..Taille_Graphe);

    k : Integer;

    begin

        if (Utiliser_Pleine = 1) then
            Pagerank_P(File,Taille_Graphe,Alpha,K_max,Epsilon,k,Dernier_Vecteur);
        else 
            Pagerank_C(File,Taille_Graphe,Alpha,K_max,Epsilon,k,Dernier_Vecteur);
        end if;

        Close(File);

        Creer_Vecteurs_Finaux(Dernier_Vecteur, V_poids, V_indices);

        creer_pr(File, Nom_Fichier_out,Taille_Graphe,V_indices);

        creer_prw(File, Nom_Fichier_out,Taille_Graphe, Alpha,K_max,V_poids);



        for i in 1..6 loop
            Put_Line(To_String(Tab_Arg(i)));
        end loop;




    end PageRank;