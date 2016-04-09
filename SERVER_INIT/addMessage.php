<?php
    include 'db_connect.php';
    doDB();

 if($_POST != null){
$sender = $_POST['sender'];
$recipient= $_POST['recipient'];
$title= $_POST['title'];
$content= $_POST['content'];

}
else{echo 'bad request';
}
	$query = "insert into message values('$sender','$recipient', '$title', '$content', CURRENT_TIMESTAMP)";
	$result = $mysqli->query($query) OR die("Exception occured, please try again");
	echo "SUCCESS FROM SERVER";
?>