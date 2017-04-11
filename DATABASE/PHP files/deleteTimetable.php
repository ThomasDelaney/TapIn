<?php 
	require "init.php";
	
	$eid = $_POST["eid"];
	$day = $_POST["day"];
	$month = $_POST["month"];
	
	$query = "DELETE FROM TIMETABLE WHERE YEEID = '$eid' and MONTH = '$month' and DAYOFWEEK = '$day'";
	
	if (mysqli_query($con, $query))
	{
		echo "true";
	}
	else
	{
		echo "false";
	}
?>