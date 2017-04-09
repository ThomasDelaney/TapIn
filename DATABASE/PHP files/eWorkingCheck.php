<?php 
	require "init.php";
	$day = $_POST["day"];
	$month = $_POST["month"];
	$cid = $_POST["cid"];
	
	$idArray = array();
	
	$query = "select E.YEEID, E.YEENAME, E.JOB from TIMETABLE T join EMPLOYEE E on T.YEEID = E.YEEID where T.DAYOFWEEK = '$day' and T.MONTH = '$month' and E.CID = '$cid' group by E.YEEID";
	$query2 = "select E.YEEID, E.YEENAME, E.JOB from TIMETABLE T join EMPLOYEE E on T.YEEID = E.YEEID where T.DAYOFWEEK != '$day' or T.MONTH != '$month' and E.CID = '$cid' group by E.YEEID";
	
	$result = mysqli_query($con, $query);

	$final = "";
	
	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$name1 = $row["YEENAME"];
			$id1 = $row["YEEID"];
			$job1 = $row["JOB"];
			
			array_push($idArray, $id1);
			
			$final = $final.$name1.",".$id1.",".$job1.",true"."-";
		}
	}

        $result2 = mysqli_query($con, $query2);

	if (mysqli_num_rows($result2) > 0)
	{
		while($row = mysqli_fetch_assoc($result2))
		{
			$name = $row["YEENAME"];
			$id = $row["YEEID"];
			$job = $row["JOB"];
			
			if (!in_array($id, $idArray))
			{
				$final = $final.$name.",".$id.",".$job.",false"."-";
			}
		}
	}
	
	echo $final;
?>