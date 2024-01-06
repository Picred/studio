<!-- escape_if_2.php -->

<!-- https://www.php.net/manual/en/language.basic-syntax.phpmode.php -->

HTML here<BR>
<?php
$x = 10;
if ($x == 0): ?>
   <B>x &egrave; <?php echo $x; ?> (then)</B>
<?php else: ?>
   <B>x &egrave; <?php echo $x; ?> (else)</B>
<?php endif; ?>
<BR>HTML again