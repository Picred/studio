<?php

error_reporting(E_ERROR);    # NB: sopprime warning in risposta a GET

//error_reporting(~E_ALL);   # sopprime ogni warning ed errori
?>

Scrivi una stringa e premi invio:
<form method="POST" action=" <?php echo $_SERVER["PHP_SELF"]; ?>">
    <input type="text" name="una_stringa">
</form>

<?php
echo "hai scritto {$_POST['una_stringa']}<BR><BR>";
printf("%x\n<BR>%x\n<BR>%x\n<BR>%x\n<BR>%x\n", E_ALL, ~E_ALL, E_STRICT, E_ERROR, E_CORE_ERROR);
?>