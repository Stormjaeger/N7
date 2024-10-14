with Ada.Float_Text_IO;		use Ada.Float_Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;

package body Vecteur is

    -- Renvoie un élément V[i] du vecteur V donné en argument
    function Obtenir_valeur(V : in T_Vecteur; i : in Integer) return Float is
        begin
            return V(i);
        end Obtenir_valeur;

    -- Remplace l'élément i de V par Val
    procedure Definir_valeur(V : in out T_Vecteur; i : in Integer; Val : in Float) is
        begin
            V(i) := Val;
        end Definir_valeur;

    -- Renvoie la taille du vecteur V
    function Obtenir_Longueur(V : in T_Vecteur) return Integer is
        begin
            return V'Length;
        end Obtenir_Longueur;

	-- Initialiser un Vecteur composé de x.
	procedure Initialiser(V : in out T_Vecteur; X : in Float) is
        Taille : Integer := Obtenir_Longueur(V);
        begin
            for i in 1..Taille loop
                V(i) := X;
            end loop;
        end Initialiser;

    -- Sommer 2 vecteurs
    procedure Sommer(V1 : in T_Vecteur; V2 : in T_Vecteur; V_out : out T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V1);
        begin
            Initialiser(V_out, 0.0);

            for i in 1..Taille loop
                V_out(i) := V1(i) + V2(i);
            end loop;
        end Sommer;


    -- Multiplication d'un vecteur par un scalaire
    procedure Mult_scal(V : in T_Vecteur; C : in Float; V_out : out T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V);
        begin
            Initialiser(V_out, 0.0);

            for i in 1..Taille loop

                V_out(i) := V(i) * C;
            
            end loop;
        
        end Mult_scal;

    
    function Calculer_distance(V1 : in T_Vecteur; V2 : in T_Vecteur) return Float is
        Taille : Integer := Obtenir_Longueur(V1);

        Distance : Float;
        Distance_max : Float := 0.0;
        begin
            for i in 1..Taille loop
                Distance := abs (V1(i) - V2(i));
                if (Distance > Distance_max) then
                    Distance_max := Distance;
                else null;
                end if;
            end loop;

            return Distance_max;
        end Calculer_distance;

    procedure Creer_Vecteurs_Finaux(V : in T_Vecteur; V_poids : out T_Vecteur; V_indices : out T_Vecteur) is

        Indice_max : Float;
        Indice_val_max : Integer;
        Val_max : Float;
        Taille : Integer := Obtenir_Longueur(V);

        begin
            V_poids := V;

            for i in 0..(Taille-1) loop
                V_indices(i+1) := Float(i);
            end loop;


            for i in 1..Taille loop
                Val_max := 0.0;
                for j in i..Taille loop
                    if (V_poids(j) > Val_max) then
                        Indice_val_max := j;
                        Val_max := V_poids(Indice_val_max);
                    else null;
                    end if;
                end loop;
                
                Indice_max := V_indices(Indice_val_max);

                V_poids(Indice_val_max) := V_poids(i);
                V_poids(i) := Val_max;

                V_indices(Indice_val_max) := V_indices(i);
                V_indices(i) := Indice_max;
                
            end loop;

        end Creer_Vecteurs_Finaux;

    procedure Copie(V1 : in T_Vecteur; V2 : in out T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V1);
        begin
            for i in 1..Taille loop
                Definir_valeur(V2,i,Obtenir_valeur(V1,i));
            end loop;
        end Copie;

    -- Affichage d'un vecteur
    procedure Afficher(V : in T_Vecteur) is
        Taille : Integer := Obtenir_Longueur(V);
        begin
            New_Line(1);


            for i in 1..Taille loop
                New_Line(1);
                Put("|");
                Put(V(i), 3, 3, 0);
                Put(" |");

            end loop;
        end Afficher;
end Vecteur;