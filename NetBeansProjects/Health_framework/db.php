<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$conn = mysql_connect('sql3.freesqldatabase.com:3306', 'sql330798', 'aX2!bA6%');
//if (!$conn)
if (!is_resource($conn)) {
    die('Could not connect: ' . mysql_error());
} else {
    //echo "connected";
}
mysql_select_db("sql330798", $conn);
?>
