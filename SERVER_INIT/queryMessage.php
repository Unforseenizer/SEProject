<?php
include 'db_connect.php';
doDB();
$query = sprintf("Select * from message where receiver = '%s'" , $_GET['recipient']);
$result = mysqli_query($mysqli, $query);
$arr = array();
while ($row = mysqli_fetch_array($result)){
$push=array();
$push['sender']=$row['sender'];
$push['recipient']=$row['receiver'];
$push['title']=$row['title'];
$push['content']=$row['content'];
$push['createtime']=$row['createtime'];
array_push($arr , $push);
}
echo json_encode($arr);
?>