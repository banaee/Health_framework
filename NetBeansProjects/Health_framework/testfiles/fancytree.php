<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
        <title>Fancytree - Example</title>

        <script src="Fancytree/lib/jquery.js" type="text/javascript"></script>
        <script src="Fancytree/lib/jquery-ui.custom.js" type="text/javascript"></script>

        <link href="Fancytree/src/skin-win7/ui.fancytree.css" rel="stylesheet" type="text/css">
        <script src="Fancytree/src/jquery.fancytree.js" type="text/javascript"></script>


        <script type="text/javascript">
            $(function(){
                // using default options
                $("#tree").fancytree();
            });
        </script>
    </head>

    <body class="example">
        <h1>Example: Default</h1>
        <div class="description">
            This tree uses default options.<br>
            It is initialized from a hidden element on this page.
        </div>
        <div>
            <label for="skinswitcher">Skin:</label> <select id="skinswitcher"></select>
        </div>
        <div id="tree">
            <ul id="treeData" style="display: none;">
                <li id="id1" title="Look, a tool tip!">item1 with key and tooltip
                <li id="id2">item2
                <li id="id3" class="folder">Folder with some children
                    <ul>
                        <li id="id3.1">Sub-item 3.1
                            <ul>
                                <li id="id3.1.1">Sub-item 3.1.1
                                <li id="id3.1.2">Sub-item 3.1.2
                            </ul>
                        <li id="id3.2">Sub-item 3.2
                            <ul>
                                <li id="id3.2.1">Sub-item 3.2.1
                                <li id="id3.2.2">Sub-item 3.2.2
                            </ul>
                    </ul>
                <li id="id4" class="expanded">Document with some children (expanded on init)
                    <ul>
                        <li id="id4.1"  class="active focused">Sub-item 4.1 (active and focus on init)
                            <ul>
                                <li id="id4.1.1">Sub-item 4.1.1
                                <li id="id4.1.2">Sub-item 4.1.2
                            </ul>
                        <li id="id4.2">Sub-item 4.2
                            <ul>
                                <li id="id4.2.1">Sub-item 4.2.1
                                <li id="id4.2.2">Sub-item 4.2.2
                            </ul>
                    </ul>
            </ul>
        </div>

        <!-- (Irrelevant source removed.) -->
    </body>
</html>