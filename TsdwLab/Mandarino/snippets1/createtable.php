 <?php
require "connect.php";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// sql to create table
$sql = "CREATE TABLE MyGuests
(
    id INT(6) UNSIGNED AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    randomcode VARCHAR(10),
    status VARCHAR(1) DEFAULT 'p',
    secretpassword VARCHAR(256) NOT NULL,
    superuser TINYINT(1) DEFAULT 0,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id, email)
)";


if ($conn->query($sql) === TRUE) {
  echo "Table MyGuests created successfully";
} else {
  echo "Error creating table: " . $conn->error;
}

$conn->close();
?> 