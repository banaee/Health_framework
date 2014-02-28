<!DOCTYPE html>

<html lang="en">
    <head>
        <link type="text/css" rel="stylesheet" href="css/divs.css" /> 
        
        <!--jQuery UI – Google Hosted Repository Links-->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.js"> </script>
        <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

        <script>           

            $(document).ready(function(){
                $("#msrmntfile").onchange(function(){
                    alert("something");
                });
                //                $("#msrmntfile").on('labelchanged', function(){
                //                    alert('changed');
                //                });
            });
        </script>
    </head>
    <body>
        <header>           
            <a href="http://www.oru.se/"><img src="img/oru.png" style="float:right" width="100" height="50"></a>
            <div style="float:right" >&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <a href="http://www.novamedtech.se/"><img src="img/nova.png" style="float:right" width="100" height="45"></a>
            <h3>HealthText</h3>  

            <!--<div style=" height:10px; width:10px; float:right; border: 1px; border-color: #0000FF"  >test</div>-->


        </header>
        <div id='main'>

            <article >  
                <p id="msrmntfile"></p> 
                
                <div id="articlediv"></div>
                <div id="articlediv2"></div>

            </article> 

            <nav> <br />Select a measurement: 
                <br />
                <?php
                include 'db.php';
                //$filearr = array();
                
                include 'importmsrmntfiles.php';

                include 'jqtree.php';
                ?>
            </nav>

            <!--<aside></aside>-->
        </div>
        <footer></footer>
    </body>
</html>