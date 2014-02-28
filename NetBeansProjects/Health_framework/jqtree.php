<?php
//$dataDirectoryname = "Measurements"; // manually from directory
//$measurements = array(array());
//$foldernamesarray = array('person1', 'person2'); //keys
//$foldernamesarray = array_filter(glob($dataDirectoryname . '/*'), 'is_dir');


foreach ($foldernamesarray as $k) {
    $namearr = array();

    $i = 0;
    foreach (glob($k . "/*.txt") as $entry) {
        $file = substr(strrchr($entry, "/"), 1);
        $namearr[$i] = $file;
        $i++;
    }

    $k = substr(strrchr($k, "/"), 1);
    $measurements[$k] = $namearr;
}

//echo $measurements['person2'][3]; output is third text file
?>


<!doctype html>
<html lang="en">
    <head>

        <script src="js/jquery.min.js"></script>

        <script src="js/tree.jquery.js"></script>
        <link rel="stylesheet" href="css/jqtree.css">
        <script src="js/jquery.cookie.js"></script>
    </head>
    <body>

        <div id="tree1"></div>
        <p></p>
        <table  border="0.5" cellspacing="0" cellpadding="3" width="50%">
            <tr>
                <td class="test1" ></td>
            </tr>
        </table>


        <?php ?>


        <script>
            var msrmnts           = <?php echo json_encode($measurements) ?>;
            var dataDirectoryname = <?php echo json_encode($dataDirectoryname) ?>;
            
            var selectedmsrmnt = "";
            var parname = '';
            
            
            var i=0;
            var data = new Array();
            $.each(msrmnts, function(key, value) {
                data[i] = {label: key.toString(),children: value};
                i++;
            });
            
            
            $(document).ready(function() {
                $('#tree1').tree({
                    data: data
                }); 
                $('#tree1').bind(
                'tree.select', function(event) 
                {
                    // The clicked node is 'event.node'
                    var node = event.node;
                    
                    if (node.children.length == 0) 
                    {
                        //alert(node.name);
                        parname = node.parent.name;
                        parpath =   dataDirectoryname.concat('/',parname);
                        
                        //alert(parname);
                        
                        selectedmsrmnt = node.name;
                        $("#msrmntfile").text(selectedmsrmnt);    
                        //$("#articlediv").text(selectedmsrmnt); 

                        
                        //alert(selectedmsrmnt);
                        //CALL PAGE
                        $.get("gplot.php", 
                        {slectfile: selectedmsrmnt, parpath: parpath}, 
                        function(responseText){
                            $("#articlediv").html(responseText)
                        }
                    ); 
                    }
                }
            );  
              
                
            }); //ready
            
        </script>


    </body>
</html>