<?php
function mk_action($act)
{
    $url = "http://" . $_SERVER['HTTP_HOST'] . $_SERVER['SCRIPT_NAME'] . "$act";
    echo "<form method=\"GET\" action=\"" . $act . "\">";
    echo "<input type=\"submit\" value=\"GET\"> ";
    echo "&emsp;nel form bottone a sinistra:&ensp;<code>action=\"$act\"</code></form>\n";
}
?>

Nei form, le <code>action</code> possono essere relative, assolute nel sito o assolute verso altro sito.<BR>

<BR><BR>
<?php
mk_action("hello.html");
echo "<BR>";
mk_action("./hello.html");
echo "<BR>";
mk_action("/hello.html");
echo "<BR>";
mk_action("http://ecoanche.dmi.unict.it/tsdw/hello.html");
echo "<BR>";
?>

<hr>
Ora alcuni casi speciali<BR><BR>
<?php
mk_action(".");
echo "<BR>";
mk_action("..");
echo "<BR>";
mk_action("");

?>
