
<!DOCTYPE html>
<html>
    <head>
        <title>Layout Example</title>
        <link type="text/css" rel="stylesheet" href="layout/layout-default.css" />
        <link type="text/css" rel="stylesheet" href="layout/layout-default-latest.css" />

        <script type="text/javascript" src="layout/jquery.js"></script>
        <script type="text/javascript" src="layout/jquery.layout.js"></script>
        <script type="text/javascript" src="layout/jquery.layout-1.2.0.js"></script>
        <script type="text/javascript" src="layout/jquery.layout.min-1.2.0.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {
                $('body').layout({ applyDemoStyles: true });
            });
        </script>
    </head>
    <body>
        <div class="ui-layout-center">Center
            <p><a href="http://layout.jquery-dev.net/demos.html">Go to the Demos page</a></p>
            <p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p>
            <p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p><p>..</p>
        </div>
        <div class="ui-layout-north">North</div>
        <div class="ui-layout-south">South</div>
        <div class="ui-layout-east">East</div>
        <div class="ui-layout-west">West</div>
    </body>
</html>