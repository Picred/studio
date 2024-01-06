<?php

# https: //www.php.net/manual/en/function.header.php
# header() invia uno header HTTP

$file = '_client_store/small.mp4';

if (file_exists($file)) {
    header('Content-Description: File Transfer');
    header('Content-Type: application/octet-stream');
    header('Content-Disposition: attachment; filename="'.basename($file).'"');
    header('Expires: 0');
    header('Cache-Control: must-revalidate');
    header('Pragma: public');
    header('Content-Length: ' . filesize($file));
    readfile($file);
    exit;
}
echo "il file $file non esiste"
?>

