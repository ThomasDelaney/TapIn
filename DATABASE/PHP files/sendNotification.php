<?php 
	require "init.php";
	
	require_once 'Firebase.php';
	require_once 'Push.php';
	
	$cid = $_POST["cid"];
	$eid = $_POST["eid"];
	$day = $_POST["day"];
	$month = $_POST["month"];
	
	$time = "";
	$name = "";
	
	$response = array(); 
	
	
	$getTime = "Select E.YEENAME, T.CINTIME from TIMETABLE T join EMPLOYEE E on T.YEEID = E.YEEID where T.DAYOFWEEK = '$day' and T.MONTH = '$month' and E.YEEID = '$eid' ";
	
	$doQuery = mysqli_query($con, $getTime);
	
	if (mysqli_num_rows($doQuery) > 0)
	{
		$row = mysqli_fetch_assoc($doQuery);
		$name = $name.$row["YEENAME"];
		$time = $time.$row["CINTIME"];
	}
	else
	{
		echo "error";
	}
	
	$push = new Push("Has Clocked In at ".$time,$name);
				
	//getting the push from push object
	$mPushNotification = $push->getPush(); 
	 
	//getting the token from database object 
	$devicetoken = getAllTokens($cid);
	 
	//creating firebase class object 
	$firebase = new Firebase(); 
	 
	//sending push notification and displaying result 
	echo $firebase->send($devicetoken, $mPushNotification);
	echo json_encode($response);
	
	function getAllTokens($compID)
	{
		require "init.php";
		
		$query = "SELECT token FROM EMPLOYER where CID = '$compID'";
		$result =  mysqli_query($con, $query);
		
		$tokens = array(); 

		while($t = mysqli_fetch_assoc($result))
		{
			array_push($tokens, $t['token']);
		}
		return $tokens; 
	}
?>