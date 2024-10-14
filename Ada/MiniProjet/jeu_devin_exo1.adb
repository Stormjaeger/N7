with Text_Io;              use Text_Io;
with Ada.Integer_Text_Io;  use Ada.Integer_Text_Io;
with Alea;

-- Auteur : Maxime LAURENT
--
-- TODO: à compléter
procedure Jeu_Devin_Exo1 is

	-- utilisation du module aléatoire
    package Mon_Alea is new Alea ( 1, 999); -- les nombres alétoires seront dans [ 1, 999 ]
    use Mon_Alea ;
    
    nb_a_trouver : Integer;  --nb que le joueur va devoir deviner
    nb_joueur : Integer ;	-- proposition du joueur
    trouve : Boolean := False ;	-- booléen indiquant si nb_a_trouver a été trouvé
    nb_tentatives : Integer := 0 ;	-- entier comptant le nb de tentatives du joueur
     
begin


    Get_Random_Number ( nb_a_trouver );
    Put("J'ai choisi un nombre entre 1 et 999");
    New_Line;
	
    loop 
	
	-- demander au joueur son choix 

	Put( "Votre choix :  ");      
	Get ( nb_joueur );
	    
	-- compter le nb de tentatives du joueur 
	nb_tentatives := nb_tentatives + 1;
	    
	-- traiter le choix du joueur
	if nb_joueur < nb_a_trouver then 
	    Put ( "Trop petit" );
	    New_Line;
	elsif nb_joueur > nb_a_trouver then
	    Put ( "Trop grand" );
	    New_Line;
	else 
	    Put ( "Trouvé" );
	    New_Line;

	    trouve := True;
	end if;
	    
    exit when trouve ; 
    end loop;
	

	Put ( "Il vous a fallu " );
	Put ( nb_tentatives );
	Put ( " Tentatives" );
	

end Jeu_Devin_Exo1;
