<!DOCTYPE html >

<?php
$nhp = 4;
$starttime = new DateTime();

$tests = 'teststring';
//$dataarr = array(array());
//$aMeasurements = $_POST['formMeasurements'];
//$aMeasurements = $_SESSION['Pathoffiles'];
//echo 'test pass data for gplot    :';
//print_r($aMeasurements);
//felan faghat yek measurement ....

$curfile = $_GET['slectfile'];
$parentpath = $_GET['parpath'];

//echo 'hello ' . $parname;
//TEST
$curpath = '';
//echo $filearray;
$curpath = $parentpath . "/" . $curfile;
//$curpath = "sleeptext/". $curfile;
//echo("</p>");
//echo 'hello ' . $curpath;
//$curpath = '';
//$curpath = $aMeasurements[0];


$lines = file($curpath);


$i = 0;
$yr = 0;
$mn = 0;
$dy = 0;
$hr = 0;
$mt = 0;
$sc = 0;

foreach ($lines as $line) {
//        $parts = explode("\t", $line);
    //echo $i;
    //echo ',';

    if ($i == 0) { //line 0
        $parts = preg_split("/[\s,-]+/", $line);
        $hpnum = $parts[0];
    } elseif ($i == 1) { //line 1
        $parts = preg_split("/[\s,-]+/", $line);
        //print_r($parts);
        $startdt = new DateTime();
        $yr = $parts[0];
        $mn = $parts[1];
        $dy = $parts[2];
        $hr = $parts[3];
        $mt = $parts[4];
        $sc = $parts[5];

        $startdt->setDate($yr, $mn, $dy);
        $startdt->setTime($hr, $mt, $sc);
    } else {
        $parts = preg_split("/[\s,]+/", $line);
        $hr_ts[$i] = $parts[0];
        $br_ts[$i] = $parts[1];
        //$third_ts[$i] = $parts[2];
        //$fourth_ts[$i] = $parts[3];


        $dataarr[$i][0] = $i;
        $dataarr[$i][1] = $hr_ts[$i];
        $dataarr[$i][2] = $br_ts[$i];
        //$dataarr[$i][3] = $third_ts[$i];
        //$dataarr[$i][4] = $fourth_ts[$i];
    }
    $i++;
}

$curhpnum = 2; //$hpnum;

$hrchecked = 2;
$brchecked = 2;


//update columns of Measurementfile table:
// - starttime
// - curhpnum
include 'db.php';

$sql2 = "UPDATE Measurementfile SET starttime = '$yr-$mn-$dy $hr:$mt:$sc', hpnum = $curhpnum WHERE filepath = '$curpath'";
mysql_query($sql2);
//echo '<br />';
//echo $sql2;


$sql3 = "SELECT * FROM Measurementfile WHERE filepath = '$curpath' LIMIT 1";
$result = mysql_query($sql3) or die(mysql_error());
$msrmntID = 0;
while ($row = mysql_fetch_assoc($result)) {
    $msrmntID = $row['id'];
}
//echo '<br />';
//echo '--inja--'.$msrmntID;
//print_r($hr_ts);
//print_r($dataarr);
//print_r($i);
?>


<html>
    <head>
        <!--[if IE]>-->
        <script type="text/javascript" src="js/excanvas.js"></script>
        <!--<![endif]-->

        <!--For production (minified) code, use:-->
        <script type="text/javascript" src="js/dygraph-combined.js"></script>


        <!--        <link rel="stylesheet" href="jquery-ui/themes/smoothness/jquery-ui.css">
                <script src="jquery-ui/jquery-1.9.1.js"></script>
                <script src="jquery-ui/ui/jquery-ui.js"></script>-->
    </head>

    <body>

        <div id="CheckBoxes">

            <input class="chckbx" type=checkbox id=0 onClick="change(this)" checked></input>
            <label for="0"> heart rate (HR) &nbsp;</label>
            <input class="chckbx" type=checkbox id=1 onClick="change(this)" checked></input>
            <label for="1"> breathing rate (BR) &nbsp;</label>
        </div>


        <div  id="div_gs" style="width:800px; height:250px; background-color: white"></div>




        <script type="text/javascript">

            var hrdata = <?php echo json_encode($hr_ts) ?>;
            var brdata = <?php echo json_encode($br_ts) ?>;
            var dataarrs = <?php echo json_encode($dataarr) ?>;   
            var pointsize = <?php echo json_encode($i - 2) ?>; 
            
            var yr =  <?php echo json_encode($yr) ?>; 
            var mn =  <?php echo json_encode($mn) ?>; 
            var dy =  <?php echo json_encode($dy) ?>; 
            var hr =  <?php echo json_encode($hr) ?>; 
            var mt =  <?php echo json_encode($mt) ?>; 
            var sc =  <?php echo json_encode($sc) ?>; 
            var msrmntid =  <?php echo json_encode($msrmntID) ?>; 
            var curpath  =  <?php echo json_encode($curpath) ?>; 

            var startdt = new Date(yr,mn,dy,hr,mt,sc,0); 
            
            var strdate = startdt.getFullYear();
            
            //document.write("<p>This is a "+ hr +" paragraph.</p>")
            //            alert(hr);
            //            alert(startdt.getHours());

            //    alert(pointsize - 2 );
       
            gs = new Dygraph(
            document.getElementById("div_gs"),
            
            function() {
                var ret = "Date,HR,BR\n";
                var zp = function(x) { if (x<10) return "0"+x; else return ""+x;};
              
                var curd = new Date(startdt);
                
                for (var sec = 0; sec < pointsize; sec++) {
                    
                    //alert(curd.getHours());
                    
                    ret += curd.getFullYear().toString() + "/" +
                        zp(curd.getMonth()) + "/" +
                        zp(curd.getDay()) + " " + 
                        zp(curd.getHours()) + ":" + 
                        zp(curd.getMinutes()) + ":" + 
                        zp(curd.getSeconds()) + "," +
                        hrdata[sec] + "," + brdata[sec] + "\n";
                    
                    //                    alter(ret);
                    
                    curd.setSeconds(curd.getSeconds()+1);
           
                }               
                return ret;
            } ,
            {
                visibility: [true, true]
            }
        );
            
            var hrchecked = 1, brchecked = 1;
            
            //$(document).ready(function() {
            //$.get("generate.php"
            //                , 
            //                {hr_checked: hrchecked, br_checked: brchecked}
            //           );
            //}); //ready
            
            
            $.get("generate.php", 
            {hr_checked: hrchecked, br_checked: brchecked, msrmntID: msrmntid, curpath:curpath},
            function(responseText){
                $("#articlediv2").html(responseText)
            } );
            
            function change(el) {
                gs.setVisibility(el.id, el.checked);
                
                if (el.checked) {
                    if (el.id == 0) {
                        hrchecked = 1;
                    }
                    else{ 
                        brchecked = 1;
                    }
                    // else if other params
                }
                else{
                    if (el.id == 0) {
                        hrchecked = 0;
                    }
                    else{ 
                        brchecked = 0;
                    }
                    // else if other params
                }
                
                //                alert(hrchecked);     
                $.get("generate.php", 
                {hr_checked: hrchecked, br_checked: brchecked, msrmntID: msrmntid, curpath:curpath},
                function(responseText){
                    $("#articlediv2").html(responseText)
                } );
 
            }
            
        </script>


        <div> 

            <?php //include 'generate.php'; ?>

        </div>

    </body>

</html>
​