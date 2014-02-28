
<form method="POST" action=""><br />
    <input type="submit" name="button1"  value="Generate text" />      
</form>

        <!--<input type="text" id="result" size="70" value="test value of the text box not multi"/>-->


<?php
if (isset($_POST['button1'])) {

//            $to_execute = "java -jar HealthNLG.jar";
//            exec($to_execute, $output);
    require 'execjar.php';
    echo '<br />';
    echo '<br />';
    require 'submit.php';
    echo '<pre>';
}
?>     