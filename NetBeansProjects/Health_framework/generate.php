
<?php
//        echo 'test here in generate php--';

$hrchecked = $_GET['hr_checked'];
$brchecked = $_GET['br_checked'];
$msrmntID = $_GET['msrmntID'];
$curpath = $_GET['curpath'];
?>  

<html>
    <head>
        <!--<title>JQuery Form Example</title>-->

        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>  


    </head>

    <body>

        <?php //echo 'hello gen ' . $curpath;  ?>

        <form name="generateform" id="generateform" method="POST" action=""><br />
            <!--<input type="text" name="name" id="name111" size="40" value="......................"/>-->

            <input type="hidden" name="name1" id="name111"  value="<?php echo $curpath ?>"/>
            <input type="hidden" name="msrmntid" id="name111"  value="<?php echo $msrmntID ?>"/>
            <input type="hidden" name="hrchecked" id="name111"  value="<?php echo $hrchecked ?>"/>
            <input type="hidden" name="brchecked" id="name111"  value="<?php echo $brchecked ?>"/>

            <input id="generate_btn" type="submit" name="generate_btn"  value="Generate text" /><br></br>   
            <!--<img id='loading' src='img/loading.gif' width="30" height="30" style='visibility: hidden;' >-->

        </form>

        <!-- We will output the results from process_generate.php here -->
        <div id="results"></div>


        <script>
            
            var hrchecked =  <?php echo json_encode($hrchecked) ?>; 
            var brchecked  =  <?php echo json_encode($brchecked) ?>; 
            
            
            $( "#generateform" ).submit(function( event ) { 
                
                event.preventDefault();

                if (hrchecked==0 && brchecked == 0) {
                    alert("please check one of the parameters");
                }
                else{
                    $.post('process_generate.php', $("#generateform").serialize(), function(data) {
                        $('#results').html(data);
                    });
                }
                
                return;
                
                
            }
        );//submit
            
        </script>

    </body>
</html>