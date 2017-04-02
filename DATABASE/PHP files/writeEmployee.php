<?php 
	require "init.php";
	
	$name = $_POST["name"];
	$username = $_POST["username"];
	$email = $_POST["email"];
	$phone = $_POST["phone"];
	$job = $_POST["job"];
	$wage = $_POST["wage"];
	$parttime = $_POST["parttime"];
	$password = $_POST["password"];
	$cid = $_POST["cid"];
	
	$query = "INSERT INTO EMPLOYEE (YEENAME, YEEUSERNAME, EMAIL, PHONE, JOB, WAGE, PARTIME, CID, PASSWORD) values ('$name', '$username', '$email', '$phone', '$job', '$wage', '$parttime', '$cid', '$password')";
	
	if (mysqli_query($con, $query))
	{
		echo "true";
	}
	else
	{
		echo "false";
	}
?>