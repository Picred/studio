<?php
require "connect.php";
require_once('functions/html.php');
require_once('functions/conf.php');

PrintTop();

//select
$sql = "SELECT id, firstname, lastname FROM MyGuests ORDER BY id";
if (defined('DebugQuery'))
    print "<br>$sql<br>";

$result = $conn->query($sql);

if ($result->num_rows > 0)
{
  while($row = $result->fetch_assoc()) 
  {
    print "id: " . $row["id"]. " - Nome: " . $row["firstname"]. " - Cognome: " . $row["lastname"]. "<br>\n";
  }
}

$result->free();

$conn->close();

PrintBottom();

?>
