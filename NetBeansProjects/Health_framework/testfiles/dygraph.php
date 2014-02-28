<!DOCTYPE html>
<html>
  <head>
    <!--<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">-->
    <title>color visibility</title>
    <!--[if IE]>
    <script type="text/javascript" src="../excanvas.js"></script>
    <![endif]-->
    
    <!--For production (minified) code, use:-->
    <script type="text/javascript" src="dygraph-combined.js"></script>
    
    <!--<script type="text/javascript" src="dygraph-dev.js"></script>-->

  </head>
  <body>
    <p>The lines should maintain their colors as their visibility is toggled.</p>

    <div id="blah"></div>
    <p><b>Display: </b>
    <input type=checkbox id=0 onClick="change(this)" checked>
    <label for="0"> a</label>
    <input type=checkbox id=1 onClick="change(this)" checked>
    <label for="1"> b</label>
    <input type=checkbox id=2 onClick="change(this)" checked>
    <label for="2"> c</label>
    </p>

    <script type="text/javascript">
      chart = new Dygraph(document.getElementById("blah"),
                          "X,a,b,c\n" +
                          "10,12345,23456,34567\n" +
                          "11,12345,20123,31345\n",
                          {
                            width: 640,
                            height: 480,
                            colors: ['#284785', '#EE1111', '#8AE234'],
                            visibility: [true, true, true]
                          });

      function change(el) {
        chart.setVisibility(el.id, el.checked);
      }
    </script>
  </body>
</html>