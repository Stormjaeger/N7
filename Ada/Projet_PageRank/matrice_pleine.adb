with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Vecteur;               use Vecteur;

package body Matrice_Pleine is
	procedure Initialiser(M : in out T_Matrice_P; X : in Float) is

        Taille : Integer := Obtenir_Longueur(M);

	begin
        for i in 1..Taille loop
            for j in 1..Taille loop
                M(i,j) := X;
            end loop;
        end loop;

	end Initialiser;

    procedure Initialiser_id(M : in out T_Matrice_P) is

        Taille : Integer := Obtenir_Longueur(M);

    begin

        Initialiser(M, 0.0);

        for i in 1..Taille loop
            M(i,i) := 1.0;
        end loop;

	end Initialiser_id;

    function Obtenir_Longueur(M : in T_Matrice_P)  return Integer is 
        begin
            return M'Length;
    end Obtenir_Longueur;

    function Obtenir_valeur(M : T_Matrice_P; i : in Integer; j : in Integer) return Float is
    begin
        return M(i, j);
    end Obtenir_valeur;

    procedure Definir_valeur(M : in out T_Matrice_P; i : in Integer; j : in Integer; Val : in Float) is 
        begin
            M(i,j) := Val;
    end Definir_valeur; 

	procedure Sommer(M1 : in T_Matrice_P ; M2 : in T_Matrice_P; M_out : out T_Matrice_P) is
            Taille : Integer := Obtenir_Longueur(M1);
        begin
            Initialiser(M_out,0.0);
        for i in 1..Taille loop
           for j in 1..Taille loop
                M_out(i,j) := M1(i,j) + M2(i,j);
            end loop;
        end loop;

	end Sommer;

    procedure Produit(M1 : in T_Matrice_P ; M2 : in T_Matrice_P; M_out : out T_Matrice_P) is
            Taille : Integer := Obtenir_Longueur(M1);
        begin
            Initialiser(M_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop
                    for k in 1..Taille loop

                        M_out(i,j) := M_out(i,j) + (M1(i,k) * M2(k,j));

                    end loop;
                end loop;
            end loop;

        end Produit;

    procedure Mult_scal(M : in T_Matrice_P ; C : in Float; M_out : out T_Matrice_P) is
            Taille : Integer := Obtenir_Longueur(M);
        begin
            Initialiser(M_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop
                    M_out(i,j) := C * M(i,j); 
                end loop;
            end loop;
        end Mult_scal;

    -- Produit d'un vecteur ligne de taille n par une matrice nxn à droite
    procedure Produit_Vect_Mat(V : in T_Vecteur; M : in T_Matrice_P; V_out : out T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V);
        begin
            Initialiser(V_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop

                        V_out(i) := V_out(i) + (V(j) * Obtenir_valeur(M,i,j));
                
                end loop;
            end loop;
        
        end Produit_Vect_Mat;

    procedure Afficher(M : in T_Matrice_P) is
            Taille : Integer := Obtenir_Longueur(M);
        begin
            New_Line(1);
            for i in 1..Taille loop

                New_Line(1);
                Put("|");

                for j in 1..Taille loop

                    Put(M(i,j), 3, 3, 0);
                    Put("  ");

                end loop;

                Put("|");

            end loop;

        end Afficher;

    

end Matrice_Pleine;
