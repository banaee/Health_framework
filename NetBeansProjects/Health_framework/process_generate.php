<?php

//if (isset($_POST['generate_btn'])) {
//            $to_execute = "java -jar HealthNLG.jar";
//            exec($to_execute, $output);
//$aMeasurements = $_SESSION['Pathoffiles'];
//echo '<br />';
//echo 'test pass data for generate process   ';
//print_r($aMeasurements);
//print "path here :   <b>".$_POST['name']."    here";
//from form, ...
$curpath = $_POST['name1'];
$msrmntID = $_POST['msrmntid'];
$hrchecked = $_POST['hrchecked'];
$brchecked = $_POST['brchecked'];

//echo '--msrmnt in process--' . $msrmntID;


$stroutput = '';

//echo 'hello process gen ' . $curpath; 
// OK, what are the selected health parameters :)
// 'hr': hr
// 'br': br
// 'hr,br': hr and br
//...
if ($hrchecked == 1 && $brchecked == 1) {
    $healthparametes = 'hr,br';
} elseif ($hrchecked == 1) {
    $healthparametes = 'hr';
} else {
    $healthparametes = 'br';
}


//If text exist in database with exact parameters just retreive it, otherwise execute jar file and insert new text file to db:)
//parameters: 1- measurementfileid, health parameters
$num_rows = 0;

include 'db.php';
$sqlsrt = "SELECT * FROM Textgen WHERE measurementfileid = $msrmntID AND healthparametes = '$healthparametes' LIMIT 1";
//echo $sqlsrt;
$result = mysql_query($sqlsrt) or die(mysql_error());

$num_rows = mysql_num_rows($result);

if ($num_rows > 0) {     //NOT working now :|
//    echo "teeest exist ';
    $textcol = '';
    while ($row = mysql_fetch_assoc($result)) {
        $textcol = $row['text'];
    }
    $stroutput = $textcol;
} else {
    include 'execjar.php';  //$stroutput will be filled
    //insert generated text  to the DB

    $sql4 = "INSERT INTO Textgen (issuetime, text, measurementfileid, healthparametes) 
                                       VALUES ( now() , '$stroutput', $msrmntID, '$healthparametes' )";
//    echo $sql4;
    mysql_query($sql4);
}

//create "text box" and print $stroutput
echo "<textarea id=\"textnlg\"  name=\"res\" rows=\"15\" cols=\"110\" readonly=\"readonly\">" . $stroutput . "</textarea>";


$sql5 = "SELECT * FROM Textgen WHERE measurementfileid = $msrmntID and text = '$stroutput' LIMIT 1";
$result5 = mysql_query($sql5) or die(mysql_error());
$textID = 0;
while ($row = mysql_fetch_assoc($result5)) {
    $textID = $row['id'];
}
mysql_free_result($result5);
//echo '<br />';
//echo $textID;



echo '<br />';
echo '<br />';


require 'submitfeedback.php';
echo '<pre>';

//}
?>
