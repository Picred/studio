<!-- escape_for_1.php -->

<?php for ($i = 0; $i < 5; ++$i) { ?>
Hello, there!<BR>
<?php } ?>

<BR>

<!-- Ora un for che, in effetti, non controlla l'HTML seguente -->
<?php for ($i = 0; $i < 5; ++$i) ?>
Hello, there!<BR>
