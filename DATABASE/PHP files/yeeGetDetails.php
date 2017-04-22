<?php
	require "init.php";
	
	$yeeid = $_POST["yeeid"];
	
	$query = "select YEENAME, JOB, CNAME FROM EMPLOYEE INNER JOIN COMPANY ON EMPLOYEE.CID = COMPANY.CID WHERE YEEID = '$yeeid' ";
	
	$result = mysqli_query($con, $query);

	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$yeename = $row["YEENAME"];
			$job = $row["JOB"];
			$cname = $row["CNAME"];
		        
			$final = $yeename.",".$job.",".$cname;	
		}	
		echo $final;
	}
	else
	{
		echo "null";
	}

?>
