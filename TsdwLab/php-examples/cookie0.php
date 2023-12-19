<!DOCTYPE html>
<?php
$cookie_name = "user0";
$cookie_value = "John Doe 0";
setcookie($cookie_name, $cookie_value);

?>
<html>
<body>

<?php
if (!isset($_COOKIE[$cookie_name])) {
     echo "Cookie named '" . $cookie_name . "' is not set!";
} else {
     echo "Cookie '" . $cookie_name . "' is set!<br>";
     echo "Value is: " . $_COOKIE[$cookie_name];
}
?>
<BR><BR>
<?php var_dump($_COOKIE) ?>

<p><strong>Note:</strong> You might have to reload the page to see the value of the cookie.</p>

</body>
</html>
