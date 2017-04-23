<?php
	require "init.php";
	
	$token = $_POST["token"];
	$name = $_POST["name"];
	$username = $_POST["username"];
	$cid = $_POST["cid"];
	
	$query = "UPDATE EMPLOYER set token = '$token' where YERNAME = '$name' and USERNAME = '$username' and CID = '$cid'";
	
	$result = mysqli_query($con, $query);

	if (mysqli_num_rows($result) > 0)
	{
		echo "true";
	}
	else
	{
		echo "false";
	}
?>