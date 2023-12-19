<HTML>
<HEAD>
<style>
code {background-color: lightgray;}
</style>
<title>Server info</title>
</HEAD>

<BODY>

<?php

function show_server_info($key)
{
    echo "$key = ";
    if  (isset($_SERVER[$key]))
      echo "$_SERVER[$key]";
   else
      echo "<b>undefined</b>";
    echo "<BR>\n";
}

function self_link($stub, $key=null, $val=null){
   $url = "http://".$_SERVER['HTTP_HOST'].$_SERVER['SCRIPT_NAME']."$stub";
   echo "<form method=\"GET\" action=\"" . $url . "\">";
   echo "<input type=\"submit\" value=\"GET\"> ";
   if (isset($key)) {
      echo "name=\"$key\" value=\"$val\"";
   }
   if (isset($key)) {
      $url .= "?$key=$val";
   }
   echo "<code>$url</code></form>\n";
}
?>

<?php self_link(null,null,null) ?>
<BR>
<?php self_link("/path1/path2") ?>
<BR>
<?php self_link("/path1/path2","autore","Verga")?>

<HR>

<h2>Il server ha ricevuto una richiesta su una
   connessione aperta da un cliente...
</h2>

L'IP del cliente visto dal server<BR style="margin-bottom:0.7em;">
<?php
show_server_info("REMOTE_ADDR");
?>

<BR>Identificazione del server e della connessione aperta dal cliente<BR style="margin-bottom:0.7em;">
<?php
show_server_info("HTTP_HOST");
show_server_info("SERVER_NAME");
show_server_info("SERVER_PORT");
?>
<BR>IP del server (solo <i>Apache</i>, non <i>php -S</i>)<BR style="margin-bottom:0.2em;">
<?php
show_server_info("SERVER_ADDR");
?>


<BR>Analisi della richiesta &nbsp;
<code>
<?php echo $_SERVER['REQUEST_METHOD'] . " " . $_SERVER['REQUEST_URI']; ?>
</code>&nbsp;
ricevuta dal server (provare i bottoni in alto)<BR style="margin-bottom:0.7em;">
<?php
show_server_info("REQUEST_METHOD");
show_server_info("REQUEST_URI");
show_server_info("PHP_SELF");
show_server_info("SCRIPT_NAME");
show_server_info("PATH_INFO");
?>

<BR>Esempio di parametro &ndash; se la richiesta corrente <code><?= $_SERVER['REQUEST_URI'] ?></code>
termina con <code>?var=</code><i>valore</i><BR style="margin-bottom:0.7em;">
var &Equal;
<?php
if (isset($_REQUEST['var']))
   echo $_REQUEST['var'];
else
   echo "<b>undefined</b>\n"
?>

<BR><BR>Dove si trova lo script sul server?<BR style="margin-bottom:0.7em;">
<?php
show_server_info("SCRIPT_FILENAME");
show_server_info("DOCUMENT_ROOT");
?>

</BODY>
</HTML>
