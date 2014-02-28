<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            html, body{
                padding: 0;
                margin: 0;
            }

            #header{
                background-color:#ccc;
                height: 70px;
                width: 100%;
                margin-left: 5px; 

            }
            #content{
                position: absolute;
                border: 1px solid crimson;
                width: 100%;
                top: 90px;
                bottom: 38px;
                margin-left: 5px; 

            }
            #leftbar{
                background-color:#eee;
                position: absolute;
                width: 300px;
                height: 100%;
                overflow:auto;
            }
            #rightbar{
                background-color:whitesmoke;
                padding-left:310px;
                height:100%;
                overflow: auto;
            }
            #footer{
                background-color:lightgrey;
                position: absolute;
                height: 30px;
                width: 100%;
                bottom:0;                 
            }
        </style>
    </head>
    <body>
        <div id="header">
            <p>header</p>
        </div>

        <div id="content">
            <div id="leftbar">
                <?php include 'jqtree.php'; ?>
            </div>

            <div id="rightbar">



            </div>
        </div>

        <div id="footer">
            footer

        </div>
    </body>
</html>