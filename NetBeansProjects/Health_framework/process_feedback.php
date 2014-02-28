<?php

//$wasusefulErr = $wasrightErr = $waseasyErr = $ageErr = $genderErr = $commentErr = "";
$wasuseful = $wasright = $waseasy = $age = $gender = $comment = "";

$wasuseful = $_POST['wasuseful'];
$waseasy = $_POST['waseasy'];
$wasright = $_POST['wasright'];
$comment = $_POST['comment'];
$role = $_POST['droprole'];
$age = $_POST['dropage'];
$gender = $_POST['gender'];
$textID = $_POST['textid'];

//echo $age[0];
//echo $role[0];

//echo 'process feedback page';
//echo '<br />';
//echo $textID;
include 'db.php';
$sql6 = "INSERT INTO Feedback (submitdt, role, age, gender, comment, isuseful, iseasy, isright, textgenid) 
                                   VALUES ( now() , '$role[0]', '$age[0]', $gender,'$comment', $wasuseful, $waseasy, $wasright, $textID )";
//echo $sql6;
mysql_query($sql6) or die(mysql_error());

echo "<p style=\"color: green\"> Your feedback is submitted successfully at ". date("Y-m-d H:i:s"). "</p>";
echo "<p style=\"color: green\"> Please select another measurement or generate a new text for the current measurement </p>";
    
//echo "<br>";
//echo "was right: " . $wasright;
//
//echo "<br>";
//echo "was easy: " . $waseasy;
//
//echo "<br>";
//echo "was useful: " . $wasuseful;
//
//
//echo "<br>";
//echo $age[0];
//
//echo "<br>";
//echo $gender;
//
//echo "<br>";
//echo $comment;

//}
?>
