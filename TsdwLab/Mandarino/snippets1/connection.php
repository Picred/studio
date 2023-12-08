<?php
require ("../Private/dati.php");

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

echo "Connected successfully<br>\n";

//alternativa
//function connect()
//{
//    // Create connection
//    $conn = new mysqli($servername, $username, $password, $dbname);
//
//    // Check connection
//    if ($conn->connect_error)
//      die("Connection failed: " . $conn->connect_error);
//
//    echo "Connected successfully<br>\n";
//
//    return $conn;
//}
?>