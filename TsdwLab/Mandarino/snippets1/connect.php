 <?php
require "../Private/dati.php";


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
  print "\n<br>Connection failed: " . $conn->connect_error . "\n";
}
echo "Connected successfully";
?> 