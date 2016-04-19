<?php
include 'db_connect.php';
doDB();
 if($_POST != null){
$evt = $_POST['evt'];
$etime = $_POST['etime'];
$desc= $_POST['desc'];
$lat= $_POST['lat'];
$lng = $_POST['lng'];
$ctime = $_POST['ctime'];
$ptt = $_POST['ptt'];
$org = $_POST['org'];
		}
else{echo 'Bad Request';}
	$query = "UPDATE evt SET evtName='$evt' ,
evtTime='$etime',
evtDesc = '$desc',
lat = $lat,
lng = $lng,
participant = '$ptt',
host = '$org' WHERE CREATE_TIME= '$ctime'";
	$result = $mysqli->query($query) OR die("Exception occured, please try again");
	echo "UPDATE SUCCESSFULLY FROM SERVER";
?>