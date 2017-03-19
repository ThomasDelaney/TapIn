<?php 
	require "init.php";
	
	$age = $_POST["age"];
	$fname = $_POST["fname"];
	$sname = $_POST["sname"];
	
	$query = "INSERT INTO test (age, fname, sname) values ('$age', '$fname', '$sname')";
	
	if (mysqli_query($con, $query))
	{
		echo "true";
	}
	else
	{
		echo "false";
	}
?>