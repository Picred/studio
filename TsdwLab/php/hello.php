<HTML>
<HEAD>
<style>
code {background-color: lightgray;}
</style>
</HEAD>
<BODY>
   <H3>Hello world!</H3>
<?php
function show_server_info($key) {
   echo "$key = $_SERVER[$key]<BR>";
}
?>

<BR> L'IP del cliente visto dal server<BR style="margin-bottom:0.7em;">
<?php
show_server_info("REMOTE_ADDR");
?>


<BR>La richiesta ricevuta dal server<BR style="margin-bottom:0.7em;">
<?php
?>

<BR>La specifica del server nella richiesta<BR style="margin-bottom:0.7em;">
<?php
show_server_info("HTTP_HOST");
show_server_info("SERVER_NAME");
show_server_info("SERVER_PORT");
?>
<BR>IP del server (Apache)<BR style="margin-bottom:0.2em;">
<?php
show_server_info("SERVER_ADDR");
?>


<BR>Analisi della richiesta &nbsp;<> 
GET /.../</<i>questo-script-php</i>/.../var?=...
</code>&nbsp;
ricevuta dal server<BR style="margin-bottom:0.7em;">
<?php
show_server_info("REQUEST_METHOD");
show_server_info("REQUEST_URI");
show_server_info("PHP_SELF");
show_server_info("SCRIPT_NAME");
show_server_info("PATH_INFO");
?>

<BR>Dove si trova lo script sul server?<BR style="margin-bottom:0.7em;">
<?php
show_server_info("SCRIPT_FILENAME");
show_server_info("DOCUMENT_ROOT");
?>

<BR>Esempio di parametro &ndash; se la richiesta corrente <code><?= $_SERVER['REQUEST_URI'] ?></code>
termina con <code>?var=</code><i>valore</i><BR style="margin-bottom:0.7em;">
var=<?=$_REQUEST['var']?>

</BODY>
</HTML>
