<?php

$dataDirectoryname = "Measurements"; // manually from directory
//$measurements = array(array());
//$foldernamesarray = array('person1', 'person2'); //keys

$foldernamesarray = array_filter(glob($dataDirectoryname . '/*'), 'is_dir');

print_r($foldernamesarray);
echo '------------';

foreach ($foldernamesarray as $k) {
    echo 'test-----' . $k . '--';
    $filearr = array();
    $namearr = array();

    $i = 0;
    foreach (glob($k . "/*.txt") as $entry) {
        $file = substr(strrchr($entry, "/"), 1);
        //$filearr += array($file => $entry);
        $namearr[$i] = $file;
        $i++;
    }

    $k = substr(strrchr($k, "/"), 1);
    $measurements[$k] = $namearr;
}

echo '--';
print_r($measurements);
echo '------------here--';
echo $measurements['person2'][3];
?>