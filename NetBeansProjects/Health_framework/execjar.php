<?php

//echo $curpath;
// We have $path here :D


$to_execute = "java -jar dist/HealthNLG.jar '$curpath' '$hrchecked' '$brchecked' ";
exec($to_execute, $output, $return_var);

//echo '-->' . $return_var . '====>';

//print_r($output);

for ($i = 0; $i < sizeof($output); $i++) {
    $stroutput .= $output[$i] . "\n";
}

//echo "<textarea id=\"textnlg\"  name=\"res\" rows=\"13\" cols=\"130\" readonly=\"readonly\">" . $stroutput . "</textarea>";
?>

