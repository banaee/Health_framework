<html>
    <head>
        <style>

            fieldset
            {
                border:1px solid #009;
                background:#eef;
                padding:10px;
                margin:0 0 10px 0;
            }

            form
            {
                font:normal normal normal 0.9em arial,sans-serif;
            }
            label
            {
                display:block;
                margin:0 0 5px 0;
                cursor:point;
            }
            label:last-child
            {
                margin-bottom:0;
            }

            label > span
            {
                float:left;
                width:45%;
                padding-right:5%;
            }

            input
            {

                width:25%;
            }
            #topbar{
                width:700px;
                /*height:250px;*/
                background-color: #eef;
                /*overflow:scroll;*/
            }

        </style>

    </head>

    <body>


        <p>This is some text.</p>

        <div style="color:#0000FF">
            <h3>This is a heading in a div element</h3>
            <p>This is some text in a div element.</p>
        </div>

        <p>This is some text.</p>
        <div id="topbar"> 
            <form action="">
                <fieldset>
                    <legend>Feedback</legend>
                    <label for="text1">
                        <span>These fields</span>
                        <input type="text" id="text1" name="text1" />
                    </label>
                    <label for="text2">
                        <span>Are grouped together</span>
                        <input type="text" id="text2" name="text2" />
                    </label>
                    <label for="text3">
                        <span>They're a set of fields (hence the name!)</span>
                        <input type="text" id="text3" name="text3" />
                    </label>
                    <!--                </fieldset>
                                    <fieldset>-->

                </fieldset> 
                <input type="submit" value="See what I mean?" />
            </form>

        </div>


    </body>
</html>