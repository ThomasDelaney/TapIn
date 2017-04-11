<?php
	require "init.php";
	$user = $_POST["user"];
	$pass = $_POST["pass"];
	
	$query = "select * from EMPLOYEE where YEEUSERNAME = '$user' AND PASSWORD = '$pass'"; 
	$result = mysqli_query($con, $query);
	
	if (mysqli_num_rows($result) > 0)
	{
		$row = mysql_fetch_array($result)
		$id = $row['YEEID'];
		echo "true". $id;
	}
	else
	{
		echo "false";
	}
?>