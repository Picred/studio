<!-- escape_if_1.php -->

<!-- https://www.php.net/manual/en/language.basic-syntax.phpmode.php -->

HTML here<BR>
<?php
$x = 10;
if ($x == 0): ?>
   <B>condizione vera</B>
<?php else: ?>
   <B>condizione falsa</B>
<?php endif; ?>
<BR>HTML again
