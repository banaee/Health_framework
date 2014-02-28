
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>submit demo</title>

        <style>
            p {
                margin: 0;
                color: blue;
            }
            div,p {
                margin-left: 10px;
            }
            span {
                color: red;
            }
            fieldset.feedback
            {
                border:1px solid #f5c6c6;
                background: whitesmoke;
                padding:7px;
                margin:0 0 10px 0;
            }
            fieldset.info
            {
                border:1px solid #f5c6c6;
                background: whitesmoke;
                padding:7px;
                margin:0 0 10px 0;
                width: 40%;
            }
            #topbar{
                width:80%;
                /*height:250px;*/
                /*background-color: #eef;*/
                /*overflow:scroll;*/
            }

            /*table*/
            table {
                border-collapse: collapse;    
            }
            td {
                /*border: 0px solid #ccc;*/
                padding: 5px;                
                text-align:center;
                border-top: 1px dashed #ccc;
                /*border-bottom: 1px dashed #ccc;*/
            }
            th{
                font-size: 13px;
                border: 0px solid #ccc;
                padding: 5px;                
                text-align:center;
            }
            #commentarea{
                text-align: left;
            }
            label {
                padding: 30px;
            }
            select{
                padding-right:5%;
                text-align: left;
            }
            /*            tr {
                            border: 1px dashed #ccc;
                        }*/
        </style>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script> 

    </head>
    <body>

        <p>Type 'correct' to validate.</p>
        <!--<form action="javascript:alert( 'success!' );">-->

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
                            <td><input type="radio" name="wasuseful" value="5"></td>
                            <td><input type="radio" name="wasuseful" value="4"></td>
                            <td><input type="radio" name="wasuseful" value="3"></td>
                            <td><input type="radio" name="wasuseful" value="2"></td>
                            <td><input type="radio" name="wasuseful" value="1"></td>
                            <td><span id="sp-wasuseful"></span></td>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">The text was easy to understand:</td>
                            <td><input type="radio" name="waseasy" value="5"></td>
                            <td><input type="radio" name="waseasy" value="4"></td>
                            <td><input type="radio" name="waseasy" value="3"></td>
                            <td><input type="radio" name="waseasy" value="2"></td>
                            <td><input type="radio" name="waseasy" value="1"></td>
                            <td><span id="sp-waseasy"></span></td>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">The right level of detail was given in the text:</td>
                            <td><input type="radio" name="wasright" value="5"></td>
                            <td><input type="radio" name="wasright" value="4"></td>
                            <td><input type="radio" name="wasright" value="3"></td>
                            <td><input type="radio" name="wasright" value="2"></td>
                            <td><input type="radio" name="wasright" value="1"></td>
                            <td><span id="sp-wasright"></span></td>
                        </tr>
                        <tr>
                            <td style="text-align:right; border-top: 0">Comment:</td>
                            <td id="commentarea" colspan="6"><textarea name="comment" rows="5" cols="55"></textarea></td>
                        </tr>
                    </table>

                </fieldset>
                <p></p>


                <fieldset class="info">
                    <legend style="background: white">Info</legend>

                    <label for="role" id="role_label" title="We ask for your role only for statistical purposes.">Profession:&nbsp;</label>  
                    <select name=droprole[] title="We ask for your role only for statistical purposes.">
                        <option value=1 selected>Select your profession&nbsp;</option>
                        <option value=2>Doctor</option>
                        <option value=3>Health professional</option>
                        <option value=4>Patient</option>
                    </select>
                    <p></p>

                    <label for="age" id="age_label" title="We ask for your age only for statistical purposes.">Age:&nbsp;</label> 
                    <select name=dropage[] title="We ask for your age only for statistical purposes.">
                        <option value=1 selected>Select your age&nbsp;</option>
                        <option value=2>20 or younger</option>
                        <option value=3>21 - 30</option>
                        <option value=4>31 - 40</option>
                        <option value=5>41 - 50</option>
                        <option value=6>51 - 60</option>
                        <option value=6>61 - 70</option>
                        <option value=7>71 or older</option>
                    </select> 
                    <p></p>                       

                    <label for="gender" id="gender_label" title="We ask for your gender only for statistical purposes.">Gender: &nbsp;</label>
                    <input type="radio" name="gender" id="gender_Male"   value=1 title="We ask for your gender only for statistical purposes.">female </input>
                    <input type="radio" name="gender" id="gender_Female" value=2 title="We ask for your gender only for statistical purposes.">male  </input>
                    <span id="sgender"></span>
                    <p></p>
<!--                    <label for="comment" id="name_label">Comment: </label> <p></p>
                    <textarea name="comment" rows="5" cols="30"></textarea>
                    <p></p>-->

                    <p></p>

                    <p></p>



                    <input id="inp1" type="text" name="i1"><span id="s1"></span>
                    <p></p>
                    <input id="inp2" type="text" name="i2"><span id="s2"></span>
                    <p></p>
                </fieldset> 
                <input type="submit">

            </form>
        </div>

        <div id="resultform"></div>

        <script>
            $( "#feedbackform" ).submit(function( event ) {              
           
                if ( $( "#inp1" ).val().length < 1 ) {
                    $("#s1").text( "Not filled!" ).show();//.fadeOut(2000);
                }
                else{ 
                    $("#s1").text("").fadeOut(10);      
                }
                
                if ( $( "#inp2" ).val() != "c" ) {
                    $("#s2").text( "Not valid!" ).show();//.fadeOut(2000);
                }
                else{ 
                    $("#s2").text("").fadeOut(10);                
                }
                
                 
                if (!(document.getElementById('gender_Male').checked) && !(document.getElementById('gender_Female').checked)) {
                    $("#sgender").text( "Not filled!" ).show();//.fadeOut(2000);
                }
                else{
                    $("#sgender").text("").fadeOut(10);
                }
                    
                
                
                //                
                event.preventDefault();
                
                
                
                
                
                //return to form if all valid
                if ( $("#inp1" ).val().length >=1 && 
                    $("#inp2" ).val() == "c" &&
                    (document.getElementById('gender_Male').checked || document.getElementById('gender_Female').checked)
     
            )
                {
                    //alert( 'success!' );                    
                    //$("#topbar").hide();
                    //$("#textnlg").hide();  
                    $.post('draftpost.php', $("#feedbackform").serialize(), function(data) {
                        $('#resultform').html(data);                        
                    });
                    
                    return;
                }                
               
            });
        </script>


        <div id="accordion">
            <!-- First accordion menu item-->
            <h3>Step One</h3>
            <div>
                <p>
                    Learn jQuery!
                </p>
            </div>

            <!-- Second accordion menu item-->
            <h3>Step Two</h3>
            <div>
                <p>
                    Learn jQuery UI!
                </p>
            </div>

            <!-- Third accordion menu item-->
            <h3>Step Three</h3>
            <div>
                <p>
                    WRITE EPIC CODE!!!
                </p>
            </div>
        </div>  
        <script> 
            $(function() {
                $("#accordion").accordion();
            });
        </script>
    </body>
</html>