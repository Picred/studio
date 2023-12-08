NB:
    la prima volta che il browser apre questa pagina è in risposta a un
    <code>GET <?php echo $_SERVER["PHP_SELF"]; ?> </code> <u>senza
    parametri</u>, da cui il <b>Warning</b>
<hr>

Scrivi una stringa e premi invio:
<form method="GET" action="<?php echo $_SERVER["PHP_SELF"]; ?>">
    <input type="text" name="una_stringa">
</form>

<?php echo "avevi scritto \"{$_GET['una_stringa']}\" nel form sopra"; ?>