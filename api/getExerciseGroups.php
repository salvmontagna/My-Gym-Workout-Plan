<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 * Questa API restituisce la lista degli esercizi per uno specifico gruppo muscolare
 *
 * @route /getExerciseGroups.php
 * @param groupId
 */

if(isset($_GET["groupId"])){
	$query="SELECT * FROM exercise WHERE Muscle_id=".$_GET["groupId"];
	$results=mysqli_query($conn, $query);
	$exercises=array();
	while($retrievedExercise=mysqli_fetch_object($results)){
		$singleExercise=array();
		$singleExercise["id"]=$retrievedExercise->id;
		$singleExercise["name"]=$retrievedExercise->exercise;
		$exercises[]=$singleExercise;
	}
	$response["data"]["exercises"]=$exercises;
}
else{
	$response["errorMessage"]="Paramentro mancante";
	$response["errorCode"]=1;
}
echo json_encode($response);
mysqli_close($conn);

