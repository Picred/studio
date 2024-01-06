<!-- escape_mix.php -->

<!-- https://www.php.net/manual/en/language.basic-syntax.phpmode.php -->

<?php $temperatura = 3 ?>

Come va il tempo?<BR><BR>

<?php if ($temperatura > 10) { ?>
Fa caldo!
Fa caldo!<BR>
<?php } else { ?>
Fa freddo!
Fa freddo!<BR>
<?php } ?>

<?php
{
    goto a;
    echo 'Grandina!<BR>'; ?>

Questo HTML sta tra due script PHP (e dentro un blocco di graffe)...

<?php
a:
    echo 'Piove!<BR>';
}
?>

<?php {
  echo "<BR>Abbiamo tramesso il meteo<BR>";
}
?>