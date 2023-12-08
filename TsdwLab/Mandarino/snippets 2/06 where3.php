<?php
require "connect.php";
require_once('functions/html.php');
require_once('functions/conf.php');

$id = $_GET['id'];
//$id = $_GET['id'] ?? -1;

PrintTopTitle("where");

//echo var_dump($id);
print "<h1>l'id richiesto e' $id</h1><br><br>";

//select
$sql = "SELECT * FROM MyGuests WHERE id=$id";


if (defined('DebugQuery'))
    print "<br>$sql<br>";

$result = $conn->query($sql);

if ($result->num_rows > 0)
{
    // output data of each row
    while ($row = $result->fetch_assoc())
    {
        echo "id: " . $row["id"] . " - Name: " . $row["firstname"] . " Lastname: " . $row["lastname"] . "  <a href=\"mailto:" . $row["email"] . "\">" . $row["email"] . "</a><br>\n";
    }
}
else
{
    echo "0 results";
}

$result->free();

$conn->close();

PrintBottom();


?>
