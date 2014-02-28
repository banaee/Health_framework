<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
    <head>
        <!--<title>JQuery Form Example</title>--> 
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#myform").validate({
                    //debug: false,
                    submitHandler: function(form) {
                        // do other stuff for a valid form
                        $.post('process.php', $("#myform").serialize(), function(data) {
                            $('#results').html(data);
                        });
                    }
                });
            });
        </script>        
    </head>
    <body>



        <?php
        $arr = array();
        foreach (glob("sleeptext/*.txt") as $entry) {
            $file = substr(strrchr($entry, "/"), 1);
            $arr += array($file => $entry);
        }
        ?>

        <form name="myform" id="myform" action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>" method="post" >
            <label for='formMeasurements[]'>Select measurement file(s):</label>

            <select multiple="multiple" name="formMeasurements[]">  

                <?php
                foreach ($arr as $k => $v) {
                    echo "<option value=\"$v\">$k</option>";
                }
                ?>                
            </select>
            <input type="submit" name="formfileSubmit" value="Submit" />
        </form>


        <!-- We will output the results from process.php here -->
        <div id="results"></div>
    </body>
</html>