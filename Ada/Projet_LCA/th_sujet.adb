with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with TH;

procedure th_sujet is 

    Capacite : constant Integer := 11;

    -- fonction de hachage 
    function hachage_str(s : Unbounded_String) return Integer is
        begin
            return ( (length(s) mod Capacite) + 1);
        end hachage_str;

    -- affiche une clé de type Unbounded_String
    procedure Afficher_Cle_sujet(cle : Unbounded_String) is
        begin
            Put(To_String(cle));
        end Afficher_Cle_sujet;

    -- affiche une valeur de type entier
    procedure Afficher_Donnee_sujet(val : Integer) is
        begin
            Put(val,1);
        end Afficher_Donnee_sujet;
        
    -- Défintion de la TH avec des Unbounded_String comme clés et des entiers comme valeurs
    package TH_String_Integer is
            new TH (T_Cle_th => Unbounded_String, T_Valeur_th => Integer,hachage => hachage_str, Capacite => Capacite);
    use TH_String_Integer;
    
    -- Affiche la Sda
    procedure Afficher is
        new Afficher_Debug_th(Afficher_Cle_sujet,Afficher_Donnee_sujet);


    tableau : T_TH;

    begin

        -- Initialise la TH
        Initialiser_th(tableau);

        -- Ajoute des couples clé/valeur à tableau
        Enregistrer_th(tableau, To_Unbounded_String("un"),1);
        Enregistrer_th(tableau, To_Unbounded_String("deux"),2);
        Enregistrer_th(tableau, To_Unbounded_String("trois"),3);
        Enregistrer_th(tableau, To_Unbounded_String("quatre"),4);
        Enregistrer_th(tableau, To_Unbounded_String("cinq"),5);
        Enregistrer_th(tableau, To_Unbounded_String("quatre-vingt_dix-neuf"),99);
        Enregistrer_th(tableau, To_Unbounded_String("vingt-et-un"),21);

        -- Affiche la structure interne de tableau
        Afficher(tableau);

end th_sujet;
