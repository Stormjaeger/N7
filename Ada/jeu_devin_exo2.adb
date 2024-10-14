with Text_Io;              use Text_Io;
with Ada.Integer_Text_Io;  use Ada.Integer_Text_Io;


-- Auteur : Daria Garnier
--

procedure Jeu_Devin_Exo2 is
car : Character;		-- charactère représentant la réponse du joueur à la proposition de l'ordinateur
c : Character;			-- charactère permettant de demander si le joueur a fait son choix
nbe_essai : integer;		-- entier représentant le nb de tentatives de l'ordinateur
nbe_trouve : integer;		-- réponse de l'ordinateur finale
Joueur_Triche : boolean;	--booléen représentant si le joueur triche 
borne_inf : integer;
borne_sup : integer;

begin
    borne_inf := 0;
    borne_sup:= 999;
    Joueur_Triche := False;
    nbe_essai := 1;
    nbe_trouve := 500;
    Put ("Avez-vous choisi un nombre entre 0 et 999 ?");
    New_Line;
    Get(c);
    
    -- déterminer le nombre choisi par une méthode de dichotomie
    While c /= 'o' and c /= 'O' loop
        Put_line("J'attends...");
        Get(c);
	end loop;
	Put("Proposition 1 : 500");
	loop
	    New_Line;
	    Put("Trop (g)rand, trop (p)etit ou (t)rouvé ?");
	    New_Line;
	    Get(car);
	    If car = 'g' or car = 'G' then
	        borne_sup := nbe_trouve;
	        nbe_trouve := (borne_inf + borne_sup)/2;
	        nbe_essai := nbe_essai + 1;
	        Put("Proposition");
	        Put(nbe_essai,2);
	        Put(":");
	        Put(nbe_trouve,3);
	    elsif car = 'p' or car= 'P' then
	        borne_inf := nbe_trouve + 1;
	        nbe_trouve := (borne_inf + borne_sup)/2 ; 
	        nbe_essai := nbe_essai + 1;
	        Put("Proposition");
	        Put(nbe_essai,2);
	        Put(":");
	        Put(nbe_trouve,3);
	    elsif car = 't' or car = 'T' then
	        null;
	    else 
	        Put("Je n'ai pas compris. Merci de répondre : g si ma proposition est trop grande, p si ma proposition est trop petite, t si j'ai trouvé le nombre");
        end if;
        if borne_inf >= borne_sup then
            Joueur_Triche := True;
            Put("Vous trichez. J’arrête cette partie.");
        end if;
     exit when Joueur_Triche or car = 't' or car = 'T' ;
     end loop;
     
     -- afficher le nombre trouvé et le nombre d'essais
     if Joueur_Triche = False then
         New_Line;
         Put("J'ai trouvé ");
         Put(nbe_trouve,3);
         Put(" en");
         Put(nbe_essai,2);
         if nbe_essai = 1 then
            Put(" essai.");
         else
            Put(" essais.");
         end if;
      end if;
end Jeu_Devin_Exo2;



     
     
     
     
     
     
