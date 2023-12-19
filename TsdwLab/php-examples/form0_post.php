NB:
    Quando il browser carica questa pagina dalla barra degli indirizzi, invia un
    <code>GET <?php echo $_SERVER["PHP_SELF"]; ?> </code> <u>senza
    parametri</u>, da cui il <b>Warning</b>
    <BR><BR>
    Quando invece si preme invio nel form testo, il browser invia 
    <code>POST <?php echo $_SERVER["PHP_SELF"]; ?> </code> e invia 
    (vedi codice) il parametro <code>una_stringa</code>
<hr>

Scrivi una stringa nel form e premi invio:
<form method="POST" action="<?= $_SERVER["PHP_SELF"] ?>">
    <input type="text" name="una_stringa">
</form>

<hr>nel form sopra avevi scritto <?php echo "\"{$_POST['una_stringa']}\""; ?> 
(valore arrivato col parametro <code>una_stringa</code>).
