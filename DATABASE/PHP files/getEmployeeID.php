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
	
	$query = "select YEEID from EMPLOYEE where YEENAME = '$name' and YEEUSERNAME = '$username' and EMAIL = '$email' and PHONE = '$phone' and JOB = '$job' and WAGE = '$wage' and PARTIME = '$parttime' and CID = '$cid' and PASSWORD = '$password'";
	
	$result = mysqli_query($con, $query);
	
	if (mysqli_num_rows($result) > 0)
	{
		$row = mysqli_fetch_assoc($result);
		$eid = $row["YEEID"];
		
		echo $eid;
	}
	else
	{
		echo "null";
	}
?>