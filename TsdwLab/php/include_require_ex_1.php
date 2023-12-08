<!DOCTYPE html>
<html>
<head>
<style>
h3 { margin-top:4ex; margin-bottom:0.2ex; }
h2 { margin-top:0.2ex; margin-bottom:0ex;}
h3 > span {font-size:95%; font-weight:normal;}
h2 > span {font-size:85%; font-weight:normal;}
hr {margin-top:4ex; margin-bottom:0.5ex;}
tt {
	background-color: #E8E8E8;
	color: blue;
	font-family: "Ubuntu Mono", "Lucida Console";
	font-size: 95%;

}
</style>
</head>
<body>

<?php error_reporting(0); ?>
<h2>Error reporting level &egrave; ora <tt>0</tt></h2>

<h3>Ora PHP invoca <tt>include</tt> su un file inesistente</h3>
(con error reporting level <tt>0</tt>, PHP non riporta warning qui sotto)
<?php include 'noSuchFileExists.php'; ?>
<br>

<hr>
<?php error_reporting(E_WARNING); ?>
<h2>Error reporting level &egrave; ora <tt>E_WARNING</tt>
<span>(PHP riporta warning, ma non errori, qui sotto)</span>
</h2>

<h3>Ora PHP invoca <tt>include</tt> su un file inesistente</h3>
(Doppio warning: fallisce l'<tt>open</tt> del file e poi l'<tt>include</tt>).
<BR>
<?php include 'noSuchFileExists.php'; ?>

<h3>Ora PHP invoca <tt>require</tt> su un file inesistente 
<span>(causa errore fatale, la computazione abortisce)</span></h3>
Con l'error reporting level <tt>E_WARNING</tt>, qui sotto non si vede l'errore, 
ma solo il warning (fallisce l'<tt>open</tt> del file).
<BR>
<?php require 'noSuchFileExists.php'; ?>

<br><h3>Does not get here</h3>

</body>
</html>

