package body Analyse_Arguments is

    function Tableaux_Arguments return T_arg is
        Tab_Arg : T_arg;
        i : Integer := 1;
        begin
            Tab_Arg(1) := To_Unbounded_String(float'Image(0.85));
            Tab_Arg(2) := To_Unbounded_String(integer'Image(150));
            Tab_Arg(3) := To_Unbounded_String(float'Image(0.0));
            Tab_Arg(4) := To_Unbounded_String("0");
            Tab_Arg(5) := To_Unbounded_String("1");
            Tab_Arg(6) := To_Unbounded_String("default");

            while (i < Argument_Count) loop
                if (Argument(i) = "-A")then
                    Tab_Arg(1) := To_Unbounded_String(Argument(i + 1));
                elsif (Argument(i) = "-K")then
                    Tab_Arg(2) := To_Unbounded_String(Argument(i + 1));
                elsif (Argument(i) = "-E")then
                    Tab_Arg(3) := To_Unbounded_String(Argument(i + 1));
                elsif (Argument(i) = "-R")then
                    Tab_Arg(6) := To_Unbounded_String(Argument(i + 1));
                elsif (Argument(i) = "-P")then
                    Tab_Arg(4) := To_Unbounded_String("1");
                    Tab_Arg(5) := To_Unbounded_String("0");
                elsif (Argument(i) = "-C")then
                    Tab_Arg(4) := To_Unbounded_String("0");
                    Tab_Arg(5) := To_Unbounded_String("1");
                else null;
                
                end if;
                i := i+2;
            end loop;
        return Tab_Arg;
    end Tableaux_Arguments;



    function Recup_Dernier_Argument return string is
    begin
        return Argument(Argument_Count);
        end Recup_Dernier_Argument;






end Analyse_Arguments;