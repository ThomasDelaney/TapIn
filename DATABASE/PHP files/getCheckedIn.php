<?php 
	require "init.php";
	$day = $_POST["day"];
	$month = $_POST["month"];
	$cid = $_POST["cid"];
	
	$query = "select E.YEENAME, T.CHECKEDIN, T.STARTTIME, T.ENDTIME, T.CINTIME from TIMETABLE T join EMPLOYEE E on T.YEEID = E.YEEID where T.DAYOFWEEK = '$day' and T.MONTH = '$month' and E.CID = '$cid'";
	
	$result = mysqli_query($con, $query);
	
	$final = "";
	
	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$name = $row["YEENAME"];
			$isIn = $row["CHECKEDIN"];
			$stime = $row["STARTTIME"];
			$etime = $row["ENDTIME"];
			$citime = $row["CINTIME"];
			
			if (is_null($citime))
			{
				$final = $final.$name.",".$isIn.",".$stime.",".$etime.", ";
			}
			else
			{
				$final = $final.$name.",".$isIn.",".$citime.",".$etime.", ";
			}
		}
		echo $final;
	}
	else
	{
		echo "null";
	}
?>