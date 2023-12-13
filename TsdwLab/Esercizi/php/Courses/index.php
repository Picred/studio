<!DOCTYPE HTML>
<html>
<body>
    <h1>Database <code>courses</code></h1>
    <?php 
    if($_GET['codice_corso'] != ""){
        $codice_corso = $_GET['codice_corso'];
        ?>
        <form action="script.php" method="POST">
            <input type="text" name="nome_corso" placeholder="Nome corso" required>
            <input type="text" name="descrizione" placeholder="Descrizione" required>
            <input type="number" name="crediti" placeholder="Crediti" required>
            <input type="hidden" name="codice_corso" value=" <?=$codice_corso?>">
            <input type="submit" name="update" value="Aggiorna corso n.<?=$codice_corso?>">
        </form>

        <br>
        <form action="script.php" method="POST">
            <input type="hidden" name="codice_corso" value=" <?=$codice_corso?>">
            <input type="submit" name="delete" value="Elimina corso n.<?=$codice_corso?>">
        </form>
        <br>
        <a href="index.php">Homepage</a>
    
    <?php } else{ ?>
    
    <h3>Read:</h3>
    <form action="script.php" method="GET">
        <input type="submit" name="read" value="Visualizza tabella courses">
    </form>

    <h3>Insert:</h3>
    <form action="script.php" method="POST">
        <input type="text" name="nome_corso" placeholder="Nome corso" required>
        <input type="text" name="descrizione" placeholder="Descrizione">
        <input type="number" name="crediti" placeholder="Crediti">
        <input type="submit" name="insert" value="Inserisci">
    </form>
    <?php } ?>
</body>
</html>