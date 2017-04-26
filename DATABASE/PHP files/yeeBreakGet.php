<?php
	require "init.php";
	
	$yeeid = $_POST["yeeid"];
	
	$query = "select BREAKTIME FROM COMPANY JOIN EMPLOYEE WHERE COMPANY.CID = EMPLOYEE.CID AND EMPLOYEE.YEEID = '$yeeid' ";
	
	$result = mysqli_query($con, $query);

	if (mysqli_num_rows($result) > 0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$breaktime = $row["BREAKTIME"];
			echo $breaktime;
		}
	}
	else
	{
		echo "null";
	}

?>
