with Text_Io;              use Text_Io;
with Jeu_Devin_Exo1;
with Jeu_Devin_Exo2;

-- Auteur : Maxime LAURENT
procedure Jeu_Devin_Exo3 is

    version : Character; -- charactère permettant de determiner à quelle version du jeu on jouera
    quitter : Boolean := False;		-- booléen permettant de quitter le jeu
begin

    loop 
    
    	-- lire la version 
        Put ("A quelle version du jeu voulez vous jouer ?");
        New_Line;
	Put( "1- L'ordinateur choisit un nombre et vous le devinez");
        New_Line;
        Put ("2- Vous choisissez un nombre et l'ordinateur le devine");
        New_Line;
        Put("0- Quitter le programme");
        New_Line;
        
        get(version);
        
        -- traiter le choix du joueur
        if version = '1' then 
            Jeu_Devin_Exo1;
            quitter := true;
            
        elsif version = '2' then      
            Jeu_Devin_Exo2;
            quitter := true;
            
        elsif version = '0' then
           quitter := True;
            
        else 
            Put( "Choix incorrect");
            
        
        end if;
    exit when quitter;
    end loop;
    


end Jeu_Devin_Exo3;
