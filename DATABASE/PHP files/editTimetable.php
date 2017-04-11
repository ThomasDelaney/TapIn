<?php 
	require "init.php";
	
	$eid = $_POST["eid"];
	$day = $_POST["day"];
	$month = $_POST["month"];
	$sTime = $_POST["sTime"];
	$eTime = $_POST["eTime"];
	
	$query = "UPDATE TIMETABLE SET STARTTIME = '$sTime', ENDTIME = '$eTime' WHERE YEEID = '$eid' and MONTH = '$month' and DAYOFWEEK = '$day'";
	
	if (mysqli_query($con, $query))
	{
		echo "true";
	}
	else
	{
		echo "false";
	}
?>