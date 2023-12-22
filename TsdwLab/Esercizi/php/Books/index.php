<html>
<body>
    <?php 
        $conn = new mysqli("localhost", "root", "mysql", "Libreria");

        function homepage(){
            echo "<br><a href='index.php'>Homepage</a>";
        }

        if(isset($_GET["id"])){
            $id = $_GET["id"];
            ?> <!-- Form insert books-->
            <form action='index.php' method='POST'>
                <h2>
                    Aggiorna il record con id <?= $id?>
                </h2>
                <input type='hidden' name='id' value='<?= $id ?>'>

                <input type='text' name='isbn' placeholder='isbn' required>
                <input type='text' name='title' placeholder='title' required>
                <input type='text' name='author' placeholder='author' required>
                <input type='text' name='publisher' placeholder='publisher' required><br>
                <input type='number' name='year' placeholder='year' required>
                <input type='number' name='ranking' placeholder='ranking' required>
                <input type='number' name='price' placeholder='price' required>
                <input type='submit' name='update' value='Aggiorna'>
            </form>

            <br>
            <h2>
                Elimina il record con id <?= $id?>
            </h2>
            <form action='index.php' method='POST'>
                <input type='hidden' name='id' value='<?= $id ?>'>
                <input type='submit' name='delete' value='Elimina'>

            </form>
        <?php }

        else{
            ?> 
            <h1> Database <code>Books</code> </h1>

            <!-- Form read data-->
            <form action='index.php' method='POST'>
                <input type='submit' name='readdb' value='Vedi record in tabella'>
            </form>

            <br>

            <!-- Form insert books-->
            <h2> Inserimento nuovo libro</h2>
            <form action='index.php' method='POST'>
                <input type='text' name='isbn' placeholder='isbn' required>
                <input type='text' name='title' placeholder='title' required>
                <input type='text' name='author' placeholder='author' required>
                <input type='text' name='publisher' placeholder='publisher' required>
                <input type='number' name='year' placeholder='year' required>
                <input type='number' name='ranking' placeholder='ranking' required>
                <input type='number' name='price' placeholder='price' required>
                <input type='submit' name='insert' value='Inserisci in tabella'>
            </form>
        
<?php }

        if($_SERVER["REQUEST_METHOD"] === "POST"){
            if(isset($_POST["readdb"])){
                $sql = "SELECT * from books";
                $res = $conn->query($sql);
                
                if($res->num_rows > 0){
                    echo "<h3><br><br>Tabella <code>books</code></h3><br>";
                    while($row = $res->fetch_assoc()){
                        $id = $row["id"];
                        $isbn = $row["isbn"];
                        $title = $row["title"];
                        $author = $row["author"];
                        $publisher = $row["publisher"];
                        $year = $row["year"];
                        $ranking = $row["ranking"];
                        $price = $row["price"];

                        echo "<b>id: </b> $id ";
                        echo "<a href='index.php?id=$id'><b>isbn: </b> $isbn </a>";
                        echo "<b>title: </b> $title ";
                        echo "<b>author: </b> $author ";
                        echo "<b>publisher: </b> $publisher ";
                        echo "<b>year: </b> $year ";
                        echo "<b>ranking: </b> $ranking ";
                        echo "<b>price: </b> $price <br>";
                    }
                }else{
                    echo "Tabella vuota";
                    homepage();
                }
            }

            if(isset($_POST["insert"])){
                $isbn = $_POST["isbn"];
                $title = $_POST["title"];
                $author = $_POST["author"];
                $publisher = $_POST["publisher"];
                $year = $_POST["year"];
                $ranking = $_POST["ranking"];
                $price = $_POST["price"];


                $sql = "INSERT INTO books (isbn, title, author, publisher, ranking, year, price) VALUES ('$isbn', '$title', '$author', '$publisher', $ranking, $year, $price)";
                if($conn->query($sql)){
                    header("location: index.php");
                }
                else{
                    echo "Inserimento fallito";
                    homepage();
                }
            }


            if(isset($_POST["update"])){
                $id = $_POST["id"];
                $isbn = $_POST["isbn"];
                $title = $_POST["title"];
                $author = $_POST["author"];
                $publisher = $_POST["publisher"];
                $year = $_POST["year"];
                $ranking = $_POST["ranking"];
                $price = $_POST["price"];

                $sql = "UPDATE books SET isbn='$isbn', title='$title', author='$author', publisher='$publisher', ranking=$ranking, year=$year, price=$price WHERE id=$id";
                if($conn->query($sql)){
                    header("location: index.php");
                }
                else{
                    echo "Aggiornamento fallito";
                    homepage();
                }

            }

            if(isset($_POST["delete"])){
                $id =$_POST["id"];
                $sql = "DELETE FROM books WHERE id=$id";
                if($conn->query($sql)){
                    header("location: index.php");
                }
                else{
                    echo "ELiminazione fallita";
                    homepage();
                }
            }

        } //end post
    ?>
</body>
</html>
