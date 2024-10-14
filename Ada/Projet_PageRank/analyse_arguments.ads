with Ada.Command_line;		use Ada.Command_line;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;

package Analyse_Arguments is

    type T_arg is array (1..6) of Unbounded_String;

    function Recup_Dernier_Argument return string;

    function Tableaux_Arguments return T_arg;





end Analyse_Arguments;