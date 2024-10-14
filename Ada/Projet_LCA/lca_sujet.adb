with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with LCA;                         

procedure lca_sujet is 

	-- Définition du type LCA avec des Unbounded String comme clés et des entiers comme valeurs.
    package LCA_String_Integer is
		new LCA (T_Cle => Unbounded_String, T_Valeur => Integer);
	use LCA_String_Integer;


    function Avec_Guillemets (S: Unbounded_String) return String is
	begin
		return '"' & To_String (S) & '"';
	end;

    	-- Afficher une Unbounded_String et un entier.
    procedure Afficher (S : in Unbounded_String; N: in Integer) is
	begin
		Put (Avec_Guillemets (S));
		Put (" : ");
		Put (N, 1);
		New_Line;
	end Afficher;

	-- Afficher la Sda.
	procedure Afficher is
		new Pour_Chaque (Afficher);
        


    liste : T_LCA;

    begin   
		-- Initialise la LCA
        Initialiser(liste);
		-- Ajoute des couples clé/valeur à liste
        Enregistrer(liste,To_Unbounded_String("un"),1);
        Enregistrer(liste,To_Unbounded_String("deux"),2);
		-- Afficher la stucture interne de liste
        Afficher(liste);

    end lca_sujet;
