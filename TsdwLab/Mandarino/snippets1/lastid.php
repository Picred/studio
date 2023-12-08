 <?php

require "connect.php";

$sql = "INSERT INTO MyGuests (firstname, lastname, email, secretpassword)
VALUES ('John1', 'Doe1', 'john1@example.com', ' ')";

if ($conn->query($sql) === TRUE) {
  $last_id = $conn->insert_id;
  echo "New record created successfully. Last inserted ID is: " . $last_id;
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();

?>