<?php


$i = 0;

$dataDirectoryname = "Measurement"; // manually from directory
$foldernamesarray= array();

$foldernamesarray = array('person1', 'person2');

foreach ($foldernamesarray as $k) {
    
}






$filearr = array();
$namearr = array();

foreach (glob($foldername . "/*.txt") as $entry) {
    $file = substr(strrchr($entry, "/"), 1);
    $filearr += array($file => $entry);
    $namearr[$i] = $file;
    $i++;
}

//print_r($filearr);
//echo '<br/>';
//print_r($namearr[0]);
//print_r($i);

$msrmntname
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
            var namearr = <?php echo json_encode($namearr) ?>;
            var foldername = <?php echo json_encode($foldername) ?>;

            var selectedmsrmnt = "";
            var parname = '';
            var data = [                {    
                    label: foldername,
                    children: [                        
                        { name: namearr[0]}, { name: namearr[1]}, { name: namearr[2]},
                        { name: namearr[3]}, { name: namearr[4]}, { name: namearr[5]} ]  },
                {
                    label: 'test',
                    children: [
                        { label: 'test1' }
                    ]
                }
            ];
            
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
                        //alert(parname);
                        
                        selectedmsrmnt = node.name;
                        $("#msrmntfile").text(selectedmsrmnt);    
                        //$("#articlediv").text(selectedmsrmnt); 

                        
                        //alert(selectedmsrmnt);
                        //CALL PAGE
                        $.get("gplot.php", 
                        {slectfile: selectedmsrmnt, parname: parname}, 
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