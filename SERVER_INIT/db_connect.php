<?php
function doDB(){
	global $mysqli;
	
	//connect
	$mysqli = mysqli_connect("127.0.0.1","root","","dbms");

	if(mysqli_connect_errno()){
	printf("Error",mysqli_connect_error());
	exit();
}
}?>