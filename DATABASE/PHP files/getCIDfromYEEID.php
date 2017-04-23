<?php 
	require "init.php";
	
	$eid = $_POST["eid"];
	
	$query = "SELECT CID from EMPLOYEE WHERE YEEID = '$eid'";

	$checkres = mysqli_query($con, $query);

	if (mysqli_num_rows($checkres) > 0)
	{
		$row = mysqli_fetch_assoc($checkres);
		echo $row["CID"];
	}
	else
	{
		echo "null";
	}
?>