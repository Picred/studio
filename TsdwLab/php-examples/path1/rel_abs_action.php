<?php
function mk_action($act)
{
    $url = "http://" . $_SERVER['HTTP_HOST'] . $_SERVER['SCRIPT_NAME'] . "$act";
    echo "<form method=\"GET\" action=\"" . $act . "\">";
    echo "<input type=\"submit\" value=\"GET\"> ";
    echo "&emsp;NB: nel form bottone a sinistra:&ensp;<code>action=\"$act\"</code></form>\n";
}
?>
<h2 style="margin-bottom: 0.4em;">
Nei form, il path della <code>action</code> può essere relativo o assoluto</h2>

NB: per gli esempi sotto, il file <code>minions.jpeg</code>
<ul style="margin-top: 0;">
<li>non si trova nella document root del server, ma</li>
<li>
si trova, rispetto alla document root, nella stessa subdirectory 
<code><?= pathinfo($_SERVER['SCRIPT_NAME'])['dirname'] . "/" ?></code>
di questo documento <code><?= pathinfo($_SERVER['SCRIPT_NAME'])['basename'] ?></code></li>
</ul>

<hr style="margin-bottom: -0.5em; margin-top: 2em; ">
<h3>Qui <code>action</code> punta a un file</h3>
<?php
mk_action("minions.jpeg");
mk_action("./minions.jpeg");
mk_action("/minions.jpeg");
mk_action("/hello.html");
?>

<hr style="margin-bottom: -0.5em; margin-top: 2em; ">
<h3 style="margin-bottom: 0.4em;">
Alcuni casi speciali per la URL della <code>action</code>
(qui <code><?= $_SERVER['PHP_SELF'] ?></code>)</h3>
<ul style="margin-top: 0;">
<li><code>action="."&nbsp;</code>
manda al path della URL corrente
privato del nome del file  </li>

<li><code>action=".."</code>
manda al path della URL corrente privato dell'ultima directory e del nome del file  </li>

<li><code>action=""&nbsp;&nbsp;</code>
manda di nuovo alla URL corrente, ma &egrave; un comportamento (non-standard) del server;
&egrave; pi&ugrave; sicuro usare <code>_SERVER['PHP_SELF']</code>  </li>

</ul>
<?php
mk_action(".");
mk_action("..");
mk_action("");

?>
