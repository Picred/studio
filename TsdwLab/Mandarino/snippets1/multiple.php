 <?php

require "connect.php";


$sql = "INSERT INTO MyGuests (firstname, lastname, email, secretpassword)
VALUES ('John', 'Doe', 'john@example.com', ' ');";
$sql .= "INSERT INTO MyGuests (firstname, lastname, email, secretpassword)
VALUES ('Mary', 'Moe', 'mary@example.com', ' ');";
$sql .= "INSERT INTO MyGuests (firstname, lastname, email, secretpassword)
VALUES ('Julie', 'Dooley', 'julie@example.com', ' ')";


if ($conn->multi_query($sql) === TRUE) {
  $last_id = $conn->insert_id;
  echo "New record created successfully. Last inserted ID is: " . $last_id;
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();

?>