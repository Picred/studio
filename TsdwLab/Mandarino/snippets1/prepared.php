 <?php

require "connect.php";


// prepare and bind
$stmt = $conn->prepare("INSERT INTO MyGuests (firstname, lastname, email, secretpassword) VALUES (?, ?, ?, ?)");
$stmt->bind_param("ssss", $firstname, $lastname, $email, $pass);

// set parameters and execute
$pass = " ";
$firstname = "John3";
$lastname = "Doe3";
$email = "john3@example.com";
$stmt->execute();

$firstname = "Mary2";
$lastname = "Moe2";
$email = "mary2@example.com";
$stmt->execute();

$firstname = "Julie2";
$lastname = "Dooley2";
$email = "julie2@example.com";
$stmt->execute();

echo "New records created successfully";

$stmt->close();
$conn->close();



?>