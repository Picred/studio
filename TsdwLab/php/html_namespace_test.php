<?php
namespace Html;

# NB: commentando il namespace sopra si causa un errore, benché il file
# incluso sotto inizi anch'esso con "namespace Html;"

# cio` illustra come "namespace" abbia effetto solo *nel medesimo* file

include "html_namespace.php";

# si noti anche la doppia funzione di "namespace Html":
# nel file incluso qualifica le classi ivi definite, p.es. Html\Table o Html\Row
# qui permette di usarle senza premettere il qualifier: Table anziche` \Html\Table

$table = new Table();
$table->title = "My table";
$table->numRows = 5;

$row = new \Html\Row();
$row->numCells = 3;

# attivando le istruzioni commentate sotto si causa un errore, che pero`
# sparirebbe commentando il namespace sopra (ma poi si avrebbero errori su Table!)

//$row = new Html\Row();
//$row->numCells = 3;

?>

<html>
<body>

<?php $table->message();?>
<?php $row->message();?>

</body>
</html>
