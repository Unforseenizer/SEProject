<?php
    include 'db_connect.php';
    doDB();

 if($_POST != null){
$evt = $_POST['evt'];
$etime = $_POST['etime'];
$desc= $_POST['desc'];
$lat= $_POST['lat'];
$lng = $_POST['lng'];
$org =$_POST['org'];
}
else{echo 'bad request';
}
	$query = "insert into evt values('$evt','$desc', null, '$org','$lat', '$lng','$etime', CURRENT_TIMESTAMP)";
	$result = $mysqli->query($query) OR die("Exception occured, please try again");
	echo "SUCCESS FROM SERVER";
?>