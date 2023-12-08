<?php
require "connect.php";
require_once 'functions/html.php';
require_once 'functions/conf.php';

PrintTop("select");


//select
$sql = "SELECT lastname, id FROM MyGuests ORDER BY id";
if (defined('DebugQuery'))
    print "<br>$sql<br>\n";

$result = $conn->query($sql);

if ($result->num_rows > 0)
{
?>
    <table>
    <?php while($row = $result->fetch_assoc())
    {
    ?>
        <tr>
            <td><?php print $row["lastname"]; ?></td>
            <td><?php print $row["id"]; ?></td>
            <td><a href="where3.php?id=<?php print $row["id"]; ?>">view</a></td>
        </tr>
    <?php        
   }
    ?>
    </table>
<?php   
}

$conn->close();
PrintBottom();

?>