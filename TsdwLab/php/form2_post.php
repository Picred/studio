<html>

<body>
    <form method="POST" action="<?= $_SERVER['PHP_SELF'] ?>">
        Name: <input type="text" name="nome_proprio">
        <input type="submit">
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        // collect value of input field
        $nomep = $_REQUEST['nome_proprio'];
        if (empty($nomep)) {
            echo "non hai scritto niente nel form";
        } else {
            echo "hai scritto \"$nomep\" nel form\n<BR>";
            echo "posso prelevarlo anche con \$_POST: " . $_POST['nome_proprio'];
        }
    }
    ?>
</body>

</html>