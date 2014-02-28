<?php

$dataDirectoryname = "Measurements"; // manually from directory

$foldernamesarray = array_filter(glob($dataDirectoryname . '/*'), 'is_dir');


foreach ($foldernamesarray as $k) {
    
    
    foreach (glob($k . "/*.txt") as $entry) {
        //$file = substr(strrchr($entry, "/"), 1);
        
        $sql1 = "INSERT IGNORE INTO Measurementfile (filepath) VALUES ( '$entry' )";
        mysql_query($sql1);
    }
}

?>
