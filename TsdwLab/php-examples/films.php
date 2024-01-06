<?php
header("Cache-Control: no-cache, no-store, must-revalidate"); // HTTP 1.1.

/* Questo file tenta di salvare il valore di un array (films[]) su uno scope
   che va oltre la gestione della singola richiesta HTTP e non usa la sessione
   (che non sembra adatta per dati grossi e strutturati).
   Ho usato l'accorgimento di salvare su file l'istruzione che assegna alla 
   variabile il valore corrente e di includere il file all'inizio.
   Funziona, ma pare che la scrittura su disco (malgrado fflush() che non dovrebbe
   servire) a volte sia così lenta che un refresh veloce trova il vecchio contenuto
   del file.
   Un modo meno smart ma più pulito è salvare sul file il json encoding dell'array
   e rileggerlo con jsondecode(); sarebbe una sorta di poor man's DB;
   per quest'ultimo caso ho la soluzione di Valerio (film_mandarino)
 */

include "films_state.php";

//print_r($films);
//echo "<BR>updating... <BR>";
$n = rand();
$films["Regista$n"] = "Titolo$n";
print_r($films);

$outfile = fopen("films_state.php", "w");
fwrite($outfile, "<?php \n\$films = " . var_export($films, true) . ";\n");
fflush($outfile);
fclose($outfile);
sleep(3);
