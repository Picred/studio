<!DOCTYPE html>
<html>
   <head>
<style>
h3 { margin-bottom:0.2ex; margin-top:4ex;}
h2 { margin-top:0.2ex; margin-bottom:0.8ex;}
h3 > span {font-size:95%; font-weight:normal;}
h2 > span {font-size:85%; font-weight:normal;}
tt {
	background-color: #E8E8E8;
	color: blue;
	font-family: "Ubuntu Mono", "Lucida Console";
	font-size: 95%;

}
</style>
</head>
<body>

<h2>L'error reporting level &egrave; posto a <tt><?= error_reporting() ?></tt> in <tt>php.ini</tt></h2>

Il valore di <i>error reporting level</i> mostrato sopra &egrave; probabilmente <tt>E_ALL</tt> (<tt><?=E_ALL?></tt>), 
cio&egrave; il default in <tt>php.ini</tt>.
Con questo livello di error reporting, php visualizza sul browser sia warning che errori.

<h3>Ora si invoca <tt>include</tt> su un file inesistente</h3>
(Doppio warning: falliscono l'<tt>open</tt> del file e poi l'<tt>include</tt>).
<BR>
<?php include 'noSuchFileExists.php'; ?>

<h3>Ora si invoca <tt>require</tt> su un file inesistente 
<span>(si ha un <b>Fatal error</b>, l'elaborazione PHP abortisce)</span></h3>
(Inoltre, warning: fallisce l'<tt>open</tt> del file).
<BR>
<?php require 'noSuchFileExists.php'; ?>

<br><h3>Does not get here</h3>

</body>
</html>

