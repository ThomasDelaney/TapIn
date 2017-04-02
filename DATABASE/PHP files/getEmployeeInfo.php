<?php
	require "init.php";
	$yee = $_POST["employee"];

	$query = "select * from EMPLOYEE where name = '$yee'";

	$result = mysqli_query($con, $query);

	if (mysqli_num_rows($result) > 0)
	{
		/*$row = mysqli_fetch_assoc($result);
		$name = $row["YEENAME"];
		$surname = $row["YEESURNAME"];
		$email = $row["EMAIL"];
		$phone = $row["PHONE"];
		$job = $row["JOB"];
		$wage = $row["WAGE"];
		$partime = $row["PARTIME"];
		$company = $row["CID"];*/

		echo $yee;
	}
	else
	{
		echo "null";
	}
?>