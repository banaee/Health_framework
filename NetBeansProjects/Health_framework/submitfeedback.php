<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
    <head>
            <!--<title>JQuery Form Example</title>--> 
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

        <link type="text/css" rel="stylesheet" href="css/feedback.css" /> 


        <script>
            $(function() {
                $( document ).tooltip();
            });                        
        </script>

    </head>
    <body>

        <div id="topbar"> 
            <form name="feedbackform" id="feedbackform" method="post" action="">
                <fieldset class="feedback">
                    <legend style="background: white">Feedback</legend>
                    <table width="90%">
                        <tr>
                            <th></th>
                            <th>strongly agree</th>
                            <th>agree</th>
                            <th>neutral</th> 
                            <th>disagree</th>
                            <th>strongly disagree</th>
                            <th></th>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">The text was useful:</td>
                            <td><input type="radio" name="wasuseful" id="useful5" value="5"></td>
                            <td><input type="radio" name="wasuseful" id="useful4" value="4"></td>
                            <td><input type="radio" name="wasuseful" id="useful3" value="3"></td>
                            <td><input type="radio" name="wasuseful" id="useful2" value="2"></td>
                            <td><input type="radio" name="wasuseful" id="useful1" value="1"></td>
                            <td><span id="swasuseful"></span></td>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">The text was easy to understand:</td>
                            <td><input type="radio" name="waseasy" id="easy5" value="5"></td>
                            <td><input type="radio" name="waseasy" id="easy4" value="4"></td>
                            <td><input type="radio" name="waseasy" id="easy3" value="3"></td>
                            <td><input type="radio" name="waseasy" id="easy2" value="2"></td>
                            <td><input type="radio" name="waseasy" id="easy1" value="1"></td>
                            <td><span id="swaseasy"></span></td>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">The right level of detail was given in the text:</td>
                            <td><input type="radio" name="wasright" id="right5" value="5"></td>
                            <td><input type="radio" name="wasright" id="right4" value="4"></td>
                            <td><input type="radio" name="wasright" id="right3" value="3"></td>
                            <td><input type="radio" name="wasright" id="right2" value="2"></td>
                            <td><input type="radio" name="wasright" id="right1" value="1"></td>
                            <td><span id="swasright"></span></td>
                        </tr>                        
                    </table>

                </fieldset>
                <p></p>
                <fieldset class="info">
                    <legend style="background: white">Info</legend>

                    <label for="role" id="role_label" title="We ask for your role only for statistical purposes.">Role:&nbsp;</label>  
                    <select id="selectrole" name=droprole[] title="We ask for your role only for statistical purposes.">
                        <option value=0 selected>Select a role&nbsp;</option>
                        <option value=1>Doctor</option>
                        <option value=2>Health professional</option>
                        <option value=3>Patient</option> 
                        <option value=4>Other</option> 
                    </select>
                    <span id="srole"></span>
                    <p></p>

                    <label for="age" id="age_label" title="We ask for your age only for statistical purposes.">Age:&nbsp;</label>  
                    <select id="selectage" name=dropage[] title="We ask for your age only for statistical purposes.">
                        <option  value=0 selected>Select your age&nbsp;</option>
                        <option value=1>20 or younger</option>
                        <option value=2>21-30</option>
                        <option value=3>31-40</option>
                        <option value=4>41-50</option>
                        <option value=5>51-60</option>
                        <option value=6>61-70</option>
                        <option value=7>71 or older</option>
                    </select>
                    <span id="sage"></span>
                    <p></p>

                    <label for="gender" id="gender_label" title="We ask for your gender only for statistical purposes.">Gender: &nbsp;</label>
                    <input type="radio" name="gender" id="gender_Male"    value=1 title="We ask for your gender only for statistical purposes.">female </input>
                    <input type="radio" name="gender" id="gender_Female"  value=2 title="We ask for your gender only for statistical purposes.">male  </input>
                    <span id="sgender"></span>
                    <p></p>
                    <label for="comment" id="name_label">Comment: </label> <p></p>
                    <textarea name="comment" rows="5" cols="30"></textarea>
                    <p></p>

                    <p></p>

                    <p></p>



<!--                    <input id="inp1" type="text" name="i1"></input><span id="s1"></span>
                    <p></p>
                    <input id="inp2" type="text" name="i2"></input><span id="s2"></span>
                    <p></p>-->

                </fieldset>
                <!--<input type="submit"></input>-->
                <input type="hidden" name="textid" id="textid"  value="<?php echo $textID ?>"></input>

                <input type="submit" name="submitfeedback_btn" value="Submit"></input>
                <span id="sallfields"></span>
            </form>
        </div>

        <div id="resultform"></div>


        <!-- We will output the results from process.php here -->


        <script>
            $( "#feedbackform" ).submit(function( event ) {              
                
                //sp-wasright - right1 to right5
                var useful1 = document.getElementById('useful1');
                var useful2 = document.getElementById('useful2');
                var useful3 = document.getElementById('useful3');
                var useful4 = document.getElementById('useful4');
                var useful5 = document.getElementById('useful5');                
                if (!(useful1.checked) && !(useful2.checked) && !(useful3.checked) && !(useful4.checked) && !(useful5.checked)) {
                    $("#swasuseful").text( "*" ).show();//.fadeOut(2000);
                    //$("#sallfields").text( "Please fill requiered fields" ).show();//.fadeOut(2000);
                }
                else{
                    $("#swasuseful").text("").fadeOut(10);
                    //$("#sallfields").text("").fadeOut(10);
                }    
                
                //sp-wasright - right1 to right5
                var easy1 = document.getElementById('easy1');
                var easy2 = document.getElementById('easy2');
                var easy3 = document.getElementById('easy3');
                var easy4 = document.getElementById('easy4');
                var easy5 = document.getElementById('easy5');                
                if (!(easy1.checked) && !(easy2.checked) && !(easy3.checked) && !(easy4.checked) && !(easy5.checked)) {
                    $("#swaseasy").text( "*" ).show();//.fadeOut(2000);
                }
                else{
                    $("#swaseasy").text("").fadeOut(10);
                }    
                
                //sp-wasright - right1 to right5
                var right1 = document.getElementById('right1');
                var right2 = document.getElementById('right2');
                var right3 = document.getElementById('right3');
                var right4 = document.getElementById('right4');
                var right5 = document.getElementById('right5');                
                if (!(right1.checked) && !(right2.checked) && !(right3.checked) && !(right4.checked) && !(right5.checked)) {
                    $("#swasright").text( "*" ).show();//.fadeOut(2000);
                }
                else{
                    $("#swasright").text("").fadeOut(10);
                }  
                
                //selectrole
                var e = document.getElementById("selectrole");
                var rolevalue = e.options[e.selectedIndex].value;
                if(rolevalue==0)
                {
                    $("#srole").text( "*" ).show();//.fadeOut(2000);
                }
                else{
                    $("#srole").text("").fadeOut(10);
                }
                
                //selectage
                var e = document.getElementById("selectage");
                var agevalue = e.options[e.selectedIndex].value;
                if(agevalue==0)
                {
                    $("#sage").text( "*" ).show();//.fadeOut(2000);
                }
                else{
                    $("#sage").text("").fadeOut(10);
                }
                
                //  gender_Male or gender_Female
                var genmale   = document.getElementById('gender_Male');
                var genfemale = document.getElementById('gender_Female');
                if (!(genmale.checked) && !(genfemale.checked)) {
                    $("#sgender").text( "*" ).show();//.fadeOut(2000);
                }
                else{
                    $("#sgender").text("").fadeOut(10);
                }
                 
                
                //                
                event.preventDefault();
                
                
                //return to form if all valid
                if ((genmale.checked || genfemale.checked)  && 
                    (agevalue != 0) &&
                    (rolevalue != 0) &&
                    (useful1.checked || useful2.checked || useful3.checked || useful4.checked || useful5.checked) &&
                    (easy1.checked || easy2.checked || easy3.checked || easy4.checked || easy5.checked) &&
                    (right1.checked || right2.checked || right3.checked || right4.checked || right5.checked) )
                {
                    //alert( 'success!' );                    
                    //$("#topbar").hide();
                    $("#feedbackform").hide(); 
                    $("#textnlg").hide();
                    $.post('process_feedback.php', $("#feedbackform").serialize(), function(data) {
                        $('#resultform').html(data);                        
                    });
                    
                    return;
                }                
               
            }
        );//submit
        </script>


    </body>
</html>