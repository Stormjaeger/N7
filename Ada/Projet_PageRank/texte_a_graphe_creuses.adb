with Ada.Integer_Text_IO;	use Ada.Integer_Text_IO;

-- Module contenant les fonctions nécessaires à la création de la matrice Google

package body Texte_A_Graphe_Creuses is

    procedure Creer_S(M_S : in out T_Matrice_C; File : in out Ada.Text_IO.File_Type) is

            Taille : Integer := Obtenir_Longueur(M_S);

            Degres : array (1..Taille) of Integer;

            Som_dep : Integer;
            Som_arr : Integer;

            Val_prec : Float;

            begin

                Initialiser(M_S, 0.0);

                for i in 1..Taille loop
                    Degres(i) := 0;
                end loop;

                begin

                    while not End_Of_file (File) loop

                        Get (File, Som_dep);
                        Get (File, Som_arr);

                        Val_prec := Obtenir_valeur(M_S,Som_dep + 1, Som_arr + 1);

                        if (Val_prec < 1.0) then   
          
                            Degres(Som_dep + 1) := Degres(Som_dep + 1) + 1;
                        else null;
                        end if;

                        Definir_valeur(M_S, Som_dep + 1, Som_arr + 1, 1.0);
                        
                    end loop;

                exception
                    when End_Error => null;
                end;

                for i in 1..Taille loop
                    for j in 1..Taille loop
                        if (Degres(i) /= 0) then
                            Definir_valeur(M_S,i,j,Obtenir_valeur(M_S, i, j)/Float(Degres(i)));
                        else
                            Definir_valeur(M_S, i, j, 1.0 / Float(Taille));
                        end if;

                    end loop;
                end loop;

            end Creer_S;

    procedure Creer_G(M_G : in out T_Matrice_C; Alpha : in Float;  File : in out Ada.Text_IO.File_Type) is

        Taille : Integer := Obtenir_Longueur(M_G);

        M_E : T_Matrice_C(1..Taille);
        M_S : T_Matrice_C(1..Taille);
        M_1 : T_Matrice_C(1..Taille);
        M_2 : T_Matrice_C(1..Taille);

        begin

            Creer_S(M_S, File);

            Initialiser(M_E, 1.0);

            Mult_scal(M_S, Alpha, M_1);

            Mult_scal(M_E, (1.0 - Alpha)/Float(Taille), M_2);

            Sommer(M_1, M_2, M_G);

            Afficher(M_1);
            Afficher(M_E);
            Afficher(M_G);

        end Creer_G;
end Texte_A_Graphe_Creuses;