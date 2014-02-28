<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HealthFramework</title>

    </head>

    <body>


        <form action="<?php echo htmlentities($_SERVER['PHP_SELF']); ?>" method="post" >
            <label for='formMeasurements[]'>Select a measurement:</label><br></br>

            <select  multiple="multiple" name="formMeasurements[]" size="8" >  

                <?php
                foreach ($filearr as $k => $v) {
                    echo "<option value=\"$v\">$k</option>";
                    //insert msrmntfiles to db, if it is not there
                }
                ?>                
            </select>
            <input type="submit" name="formfileSubmit"  value="Submit"/>
        </form>


        <?php ?>
    </body>
</html>
