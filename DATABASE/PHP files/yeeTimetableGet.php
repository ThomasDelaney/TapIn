<?php
	require "init.php";
	
	$employee = $_POST["employee"];
	
	$query = "select YEEID, DAYOFWEEK, STARTTIME, ENDTIME FROM TIMETABLE WHERE YEEID = '$employee' ";
	
	$result = mysqli_query($con, $query);

	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$eid = $row["YEEID"];
			$dayofweek = $row["DAYOFWEEK"];
			$starttime = $row["STARTTIME"];
			$endtime = $row["ENDTIME"];
	                
			$final = $eid.",".$dayofweek.",".$starttime.",".$endtime."-";
			
		}
		echo $final;
	}
	else
	{
		echo "null";
	}

?>