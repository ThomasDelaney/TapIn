<?php 
	require "init.php";
	$month = $_POST["month"];
	$cid = $_POST["cid"];
	
	$query = "select E.YEEID, E.YEENAME, E.JOB, E.PARTIME from TIMETABLE T join EMPLOYEE E on T.YEEID = E.YEEID where T.MONTH = '$month' and E.CID = '$cid' group by T.CID";
	
	$result = mysqli_query($con, $query);
	
	$final = "";
	
	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$eid = $row["YEEID"];
			$name = $row["YEENAME"];
			$job = $row["JOB"];
			$parttime = $row["PARTIME"];
	
			$final = $final.$eid.",".$name.",".$job.",".$parttime."-";
		}
		echo $final;
	}
	else
	{
		echo "null";
	}
?>