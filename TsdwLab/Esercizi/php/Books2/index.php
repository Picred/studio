<html>
<body>

    <?php if($_SERVER["REQUEST_METHOD"] === "GET"){ ?>
    <h1> Benvenuto sul database <code>books2</code></h1>
    <h3> Inserimento di un nuovo autore e paese di pubblicazione </h3>

    <form action="index.php" method="POST">
        <p>Seleziona il Nome da inserire in tabella. </p>
        <select name="firstname">
        <option value="Name1">Name1</option>
        <option value="Name2">Name2</option>
        </select>

        <select name="lastname">
            <option value="Lastname1">Lastname1</option>
            <option value="Lastname2">Lastname2</option>
        </select>
        <br>
        <p>Seleziona paese. </p>
        <select name="country">
            <option value="Country1">Country1</option>
            <option value="Country2">Country2</option>
        </select>

        <input type="hidden" name="insert" value="insert">
        <input type="submit" value="Seleziona">
    </form>


<?php } //END GET
 else { //POST
    if(isset($_POST["insert"])){
        $firstname = $_POST["firstname"];
        $lastname = $_POST["lastname"];

        echo "Hai selezionato $firstname e $lastname <br>";


    }
}

?>

</body>
</html>