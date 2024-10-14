with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with SDA_Exceptions;         use SDA_Exceptions;
with Vecteur;               use Vecteur;

package body Matrice_Creuse is
	procedure Initialiser(M : in out T_Matrice_C; X : in Float) is

        Taille : Integer := Obtenir_Longueur(M);

	begin
        for i in 1..Taille loop
            Initialiser(M(i));
            if (X > 0.00001) then
                for j in 1..Taille loop
                    Enregistrer(M(i),j,X);
                end loop;
            end if;
        end loop;

	end Initialiser;

    procedure Initialiser_id(M : in out T_Matrice_C) is

        Taille : Integer := Obtenir_Longueur(M);

    begin

        Initialiser(M, 0.0);

        for i in 1..Taille loop
            Enregistrer(M(i),i,1.0);
        end loop;

	end Initialiser_id;

    function Obtenir_Longueur(M : in T_Matrice_C)  return Integer is 
        begin
            return M'Length;
    end Obtenir_Longueur;

    function Obtenir_valeur(M : T_Matrice_C; i : in Integer; j : in Integer) return Float is
        retour : Float;
    begin
        begin
            retour := La_Valeur(M(i),j);
        exception
            when Cle_Absente_Exception => retour := 0.0;
        end;
        return retour;
    end Obtenir_valeur;

    procedure Definir_valeur(M : in out T_Matrice_C; i : in Integer; j : in Integer; Val : in Float) is 
        begin
            Enregistrer(M(i),j,Val);
    end Definir_valeur; 

	procedure Sommer(M1 : in T_Matrice_C ; M2 : in T_Matrice_C; M_out : out T_Matrice_C) is
            Taille : Integer := Obtenir_Longueur(M1);
            elt1 : Float;            
            elt2 : Float;
        begin
            Initialiser(M_out,0.0);
        for i in 1..Taille loop
           for j in 1..Taille loop

                begin
                    elt1 := Obtenir_valeur(M1, i, j);
                exception
                    when Cle_Absente_Exception => elt1 := 0.0;
                end;
                
                begin
                    elt2 := Obtenir_valeur(M2, i, j);
                exception
                    when Cle_Absente_Exception => elt2 := 0.0;
                end;

                Enregistrer(M_out(i), j, elt1 + elt2) ;
            end loop;
        end loop;

	end Sommer;

    procedure Produit(M1 : in T_Matrice_C ; M2 : in T_Matrice_C; M_out : out T_Matrice_C) is
            Taille : Integer := Obtenir_Longueur(M1);
            elt1 : Float;            
            elt2 : Float;
            case_i_j : Float;
        begin
            Initialiser(M_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop

                    case_i_j := 0.0;

                    for k in 1..Taille loop
                    
                        begin
                            elt1 := Obtenir_valeur(M1, i, k);
                        exception
                            when Cle_Absente_Exception => elt1 := 0.0;
                        end;

                        begin
                            elt2 := Obtenir_valeur(M2, k, j);
                        exception
                            when Cle_Absente_Exception => elt2 := 0.0;
                        end;

                        case_i_j := case_i_j + elt1 * elt2;

                    end loop;

                    Enregistrer(M_out(i),j,case_i_j);

                end loop;
            end loop;

        end Produit;

    procedure Mult_scal(M : in T_Matrice_C ; C : in Float; M_out : out T_Matrice_C) is
            Taille : Integer := Obtenir_Longueur(M);
            elt : Float;
        begin
            Initialiser(M_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop
                    
                    begin
                        elt := C*Obtenir_valeur(M, i, j);
                        Enregistrer(M_out(i), j, elt) ;
                    exception
                        when Cle_Absente_Exception => null;
                    end;

                end loop;
            end loop;
        end Mult_scal;

    -- Produit d'un vecteur ligne de taille n par une matrice nxn à droite
    procedure Produit_Vect_Mat(V : in T_Vecteur; M : in T_Matrice_C; V_out : out T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V);
        begin
            Initialiser(V_out,0.0);

            for i in 1..Taille loop
                for j in 1..Taille loop

                        V_out(i) := V_out(i) + (V(j) * Obtenir_valeur(M,i,j));
                
                end loop;
            end loop;
        
        end Produit_Vect_Mat;

    procedure Afficher(M : in T_Matrice_C) is
            Taille : Integer := Obtenir_Longueur(M);
            elt : Float;
        begin
            New_Line(1);
            for i in 1..Taille loop

                New_Line(1);
                Put("|");

                for j in 1..Taille loop
                    
                    begin
                        elt := Obtenir_valeur(M, i, j);
                    exception
                        when Cle_Absente_Exception => elt := 0.0;
                    end;

                    Put(elt, 3, 3, 0);
                    Put("  ");

                end loop;

                Put("|");

            end loop;

        end Afficher;

    

end Matrice_Creuse;
