<?php
    $conn = new mysqli("localhost", "newuser", "password", "University");
    if($conn->connect_error){
        die("Errore durante la connessione con il database");
    }

    function homepage(){
        echo "<br><a href=index.php>Homepage</a>";
    }

    function readCourses($conn){
        $sql = "SELECT * FROM courses;";
        $result = $conn->query($sql);
        if($result->num_rows>0){
            while($row = $result->fetch_assoc()){
                $codice_corso = $row['codice_corso'];
                echo "<a href='index.php?codice_corso=" . $codice_corso . "'>$codice_corso</a> | " . $row['nome_corso'] . " | " . $row['descrizione'] . " | " . $row['crediti'] . "<br>"; 
            }
        }
        else
            echo "<h2>Tabella vuota</h2>";

        homepage();
    }

    function insert($conn, $nome_corso, $descrizione, $crediti){
        $sql = "INSERT INTO courses (nome_corso, descrizione, crediti) VALUES ('$nome_corso', '$descrizione',";

        $crediti != "" ? $sql .= " '$crediti')" : $sql .= " 0)";

        if($crediti == "") 
            echo "<i>Attenzione: non hai inserito i crediti e quindi sono stati impostati a <code>0</code></i><br>";

        if($conn->query($sql))
            echo "Inserimento avvenuto";
        else
            echo "Inserimento fallito";
        homepage();
    }


    function updateCourse($conn, $codice_corso, $nome_corso, $descrizione, $crediti){
        $sql = "UPDATE courses SET nome_corso='$nome_corso', descrizione='$descrizione', crediti='$crediti' WHERE codice_corso='$codice_corso'";

        if($conn->query($sql))
            echo "Aggiornamento effettuato";
        else
            echo "Errore nell'aggiornamento";
        homepage();
    }

    if($_SERVER["REQUEST_METHOD"] === "GET"){
        if(isset($_GET['read'])){
            readCourses($conn);
        }
    }


    function deleteCourse($conn, $codice_corso){
        $sql = "DELETE FROM courses WHERE codice_corso='$codice_corso'";
        if($conn->query($sql))
            echo "Eliminazione avvenuta con successo";
        else
            echo "Eliminazione fallita";
        homepage();
    }
    if($_SERVER["REQUEST_METHOD"] === "POST"){
        if(isset($_POST['insert']))
            insert($conn, $_POST['nome_corso'], $_POST['descrizione'], $_POST['crediti']);

        if(isset($_POST['update']))
            updateCourse($conn, $_POST['codice_corso'], $_POST['nome_corso'], $_POST['descrizione'], $_POST['crediti']);

        if(isset($_POST['delete']))
            deleteCourse($conn, $_POST['codice_corso']);
    }
?>