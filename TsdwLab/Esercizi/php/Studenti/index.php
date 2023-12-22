<html>
<body>
    <?php $conn = new mysqli("localhost", "root", "mysql", "University");
    
    if($conn->connect_error){
        die("Errore di connessione");
    }


    function homepage(){
        echo "<br><a href='index.php'>Homepage</a>";
    }
    if(isset($_GET["cdl"])){
        $cdl = $_GET["cdl"];
        echo "<h1>Tabella <code>courses</code></h1><br>";
        $sql = "SELECT * FROM courses";
        $res = $conn->query($sql);

        if($res->num_rows > 0){
            while($row = $res->fetch_assoc()){
                $codice_corso = $row["codice_corso"];
                $nome_corso = $row["nome_corso"];
                $descrizione = $row["descrizione"];
                $crediti = $row["crediti"];
                echo "<b>Codice corso:</b> $codice_corso ";
                echo "<b>Nome corso:</b> $nome_corso ";
                echo "<b>Descrziione:</b> $descrizione ";
                echo "<b>Crediti:</b> $crediti <br>";
            }
        } else{
            echo "Tabella <code>courses</code> vuota";
        }

        //Aggiornamento corso di laurea
        ?>
        <h3>Aggiorna un corso di laurea</h3>
        <form action='index.php' method='POST'>
            <input type='number' name='codice_corso' placeholder='Codice corso da aggiornare' required>
            <input type='text' name='nome_corso' placeholder='Nome corso' required>
            <input type='text' name='descrizione' placeholder='Descrizione' required>
            <input type='number' name='crediti' placeholder='Crediti' required>
            <input type='submit' name='update' value='Aggiorna corso'>
        </form>
        <?php
        homepage();
    } else{ ?>
    <h1>Benvenuto</h1>
    
    <br>
    
    <?php if($_SERVER["REQUEST_METHOD"] === "GET"){
        echo "<h2>Lista studenti presenti nel dabatase <br></h2>";

        $sql = "SELECT * FROM students";
        $res = $conn->query($sql);

        if($res->num_rows>0){
            while($row = $res->fetch_assoc()){
                $matricola = $row["matricola"];
                $nome = $row["nome"];
                $cognome = $row["cognome"];
                $corso_di_laurea = $row["corso_di_laurea"];

                echo "<b>Matricola:</b> $matricola ";
                echo "<b>Nome:</b> $nome ";
                echo "<b>Cognome:</b> $cognome ";
                echo "<a href='index.php?cdl=$corso_di_laurea'><b>Corso di laurea:</b> $corso_di_laurea </a><br> ";
            }
        } else{
            echo "Tabella <code>students</code> vuota";
        } ?> 
        
        <br>
        <!-- Inserimento nuovo studente -->
        <form action='index.php' method='POST'>
            <input type='text' name='nome' placeholder='Nome' required>
            <input type='text' name='cognome' placeholder='Cognome' required>
            <input type='number' name='matricola' placeholder='Matricola' required>
            <input type='number' name='corso_di_laurea' placeholder='Corso di Laurea' required>
            <input type='submit' name='insert' value='Inserisci nuovo studente'>
        </form>

        <br>
        <!-- Eliminazione studente -->
        <form action='index.php' method='POST'>
            <input type='number' name='matricola' placeholder='Matricola' required>
            <input type='submit' name='delete' value='Elimina lo studente'>
        </form>
    <?php
        }
    } //END GET 
    
    if($_SERVER["REQUEST_METHOD"] === "POST"){
        if(isset($_POST["insert"])){
            $matricola = $_POST["matricola"];
            $nome = $_POST["nome"];
            $cognome = $_POST["cognome"];
            $corso_di_laurea = $_POST["corso_di_laurea"];

            $sql = "INSERT INTO students (matricola, nome, cognome, corso_di_laurea) VALUES ($matricola, '$nome', '$cognome', $corso_di_laurea)";
            if($conn->query($sql)){
                echo "Inserimento avvenuto con successo";
            }
            else{
                echo "Inserimento fallito";
            }
            homepage();
        }
        if(isset($_POST["update"])){
            $codice_corso = $_POST["codice_corso"];
            $nome_corso = $_POST["nome_corso"];
            $descrizione = $_POST["descrizione"];
            $crediti = $_POST["crediti"];
            $sql = "UPDATE courses SET nome_corso='$nome_corso', descrizione='$descrizione', crediti=$crediti WHERE codice_corso=$codice_corso";
            if($conn->query($sql)){
                echo "Aggiornamento del corso $codice_corso effettuato";
            }
            else{
                echo "Aggiornamento del corso $codice_corso fallito";
            }
            homepage();
        }

        if(isset($_POST["delete"])){
            $matricola = $_POST["matricola"];
            ?>
            <h3> Sei sicuro di voler eliminare lo studente <?= $matricola ?> ? </h3>
            <form action='index.php' method='POST'>
            <input type='hidden' name='matricola' value='<?= $matricola ?>'>
            <input type='submit' name='si' value='Si'>
            <input type='submit' name='no' value='No'>
            </form>
            <?php
            homepage();
        }

        if(isset($_POST["si"])){
            $matricola = $_POST["matricola"];
            $sql = "DELETE FROM students WHERE matricola=$matricola";
            if($conn->query($sql)){
                echo "Eliminazione effettuata";
            }
            else{
                echo "Eliminazione fallita";
            }
            homepage();
        }

        if(isset($_POST["no"])){
            echo "Eliminazione annullata";
            homepage();
        }
    } // END POST ?>
</body>
</html>