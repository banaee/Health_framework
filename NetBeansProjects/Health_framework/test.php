<html>
    <head>

        <!-- Load the jQuery UI CSS -->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

        <!-- Load jQuery and jQuery UI -->
        <script src="//code.jquery.com/jquery-1.9.1.js"></script>
        <script src="//code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <!-- jQuery UI Slider code -->
        <script>
            // When the browser is ready...
            $(function() {
                // variable for counting
                var count = 0;
    
    
    
                // Call the "button" method on the div 
                // with id 'sampleButtonDiv'.
                // This will convert the DIV into a 
                // jQuery UI Button with the contents
                // from the original DIV.
                $("#sampleButtonDiv").button();





                // On clicking the button, we add increment
                // the output which is the number of times
                // the button has been clicked.
                $("#sampleButtonDiv").click(function (evt) {
                    // increment the value of output 
                    $('.output').html(++count);
                });
            });
        </script>
    </head>
    <body>
        <!-- div for the button -->
        <div id="sampleButtonDiv">
            Button
        </div>

        <p>
            Number of Times Button has been clicked:
        </p>

        <!-- Our output value -->
        <div style="border: 1px solid;">
            <p class="output" style="padding-left: 10px;">
                0
            </p>
        </div>

    </body>
</html>
