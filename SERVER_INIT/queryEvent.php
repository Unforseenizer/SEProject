<?php
include 'db_connect.php';
doDB();
$result = mysqli_query($mysqli, "Select * from evt");
$arr = array();
while ($row = mysqli_fetch_array($result)){
$push=array();
$push['evtName']=$row['evtName'];
$push['evtTime']=$row['evtTime'];
$push['evtDesc']=$row['evtDesc'];
$push['lat']=$row['lat'];
$push['lng']=$row['lng'];


$push['participant'] =(isset($row['participant']) ? $row['participant'] : '');
$push['host'] =(isset($row['host'])? $row['host']: '');

$push['CREATE_TIME']=$row['CREATE_TIME'];
array_push($arr , $push);
}
echo json_encode($arr);
?>