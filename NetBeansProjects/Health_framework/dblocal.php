<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$conn = mysql_connect('localhost', 'root', '');
//if (!$conn)
if (!is_resource($conn)) {
    die('Could not connect: ' . mysql_error());
} else {
    //echo "connected";
}
mysql_select_db("ReMOTE_db", $conn);
?>
