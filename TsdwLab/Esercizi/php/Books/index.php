<html>
<body>
    <?php 
        $conn = new mysqli("localhost", "root", "mysql", "Libreria");

        function homepage(){
            echo "<br><a href='index.php'>Homepage</a>";
        }

        ?> 
        <h1> Benvenuto </h1>
        
        <br>
        <!-- Form read data-->
        <form action='index.php' method='POST'>
            <input type='submit' name='readdb' value='Vedi record in tabella'>
        </form>

        <br>

        <!-- Form insert books-->
        <form action='index.php' method='POST'>
            <input type='text' name='isbn' placeholder='isbn' required>
            <input type='text' name='title' placeholder='title' required>
            <input type='text' name='author' placeholder='author' required>
            <input type='text' name='publisher' placeholder='publisher' required>
            <input type='number' name='year' placeholder='year' required>
            <input type='number' name='ranking' placeholder='ranking' required>
            <input type='submit' name='insert' value='Inserisci in tabella'>
        </form>
        <?php

        if($_SERVER["REQUEST_METHOD"] === "POST"){
            if(isset($_POST["readdb"])){
                $sql = "SELECT * from books";
                $res = $conn->query($sql);
                
                if($res->num_rows > 0){
                    echo "Tabella <code>books</code><br>";
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
                        echo "<b>isbn: </b> $isbn ";
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

                $sql = "INSERT INTO books (isbn, title, author, publisher, year, ranking) VALUES isbn='$isbn', title='$title', author='$author', publisher='$publisher', year=$year, rankinkg=$ranking";
                if($conn->query($sql)){
                    header("location: index.php");
                }
                else{
                    echo "Inserimento fallito";
                    homepage();
                }
            }

        } //end post
    ?>
</body>
</html>
