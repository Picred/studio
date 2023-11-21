<?php

echo "Massima dim. POST accettato dal server: " . ini_get("post_max_size") . "<BR>";
echo "Massima dim. file accettato dal server: " . ini_get("upload_max_filesize") . "<BR>";
define('MAX_SIZE_FOR_SCRIPT',10000);
echo "Massima dim. file accettato da questo script: " . MAX_SIZE_FOR_SCRIPT/1000 . "K<BR>";

echo "Massima dim. file accettato da form di upload in <code>";
echo basename($_SERVER['HTTP_REFERER']) . "</code>: ";
echo $_POST['MAX_FILE_SIZE'] / 1000000 . "M ";
echo "(dovrebbe essere &lt;" . MAX_SIZE_FOR_SCRIPT/1000;
echo "K, ma, per testare pi&ugrave; casi, provare ";
echo "invece p.es. " . 2*MAX_SIZE_FOR_SCRIPT/1000 . "K o ";
echo "2*" . ini_get("upload_max_filesize") . ")<hr>";


$upload_err_msg = ["no error",
    "errore: upload troppo grande per server",
    "errore: upload troppo grande per form sul browser"
];
