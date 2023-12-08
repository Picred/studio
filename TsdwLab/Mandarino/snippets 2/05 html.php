<?php
function PrintTop()
{
    print "<!DOCTYPE html>\n<html>\n    <body>\n";    
}

function PrintTopTitle($title = "")
{
    print "<!DOCTYPE html>\n";
    print "<html>\n";
    print "    <head>\n";
    print ($title != "" ? "        <title>$title</title>\n" : "");
    print "    </head>\n";
    print "    <body>\n";
}

function PrintBottom()
{
    print "\n    </body>\n";
    print "</html>";
}

?>