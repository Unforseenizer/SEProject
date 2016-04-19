<?php
include 'db_connect.php';
doDB();

$CREATE_TIME = $_POST['ctime'];
$query = "DELETE FROM evt
WHERE CREATE_TIME = '$CREATE_TIME';";
$mysqli->query($query) OR die("Exception occured, please try again");
echo "Success FROM Server.";
?>