<?php 
	require "init.php";
	$user = $_POST["username"];
	$pass= $_POST["password"];
	
	$query = "select * from EMPLOYER where username = '$user' and password = '$pass'";
	
	$result = mysqli_query($con, $query);
	
	if (mysqli_num_rows($result) > 0)
	{
		$row = mysqli_fetch_assoc($result);
		$cid = $row["CID"];
		$uname = $row["USERNAME"];
		$name = $row["YERNAME"];
		
		echo $cid." ".$uname." ".$name;
	}
	else
	{
		echo "null";
	}
	
?>