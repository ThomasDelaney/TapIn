<?php 
	require "init.php";
	$id = $_POST["id"];
	
	$query = "select age, fname, sname from test where id = '$id'";
	
	$result = mysqli_query($con, $query);
	
	if (mysqli_num_rows($result) > 0)
	{
		$row = mysqli_fetch_assoc($result);
		$age = $row["age"];
		$fname = $row["fname"];
		$sname = $row["sname"];
		
		echo $age." ".$fname." ".$sname;
	}
	else
	{
		echo "Employee not Found";
	}
	
?>