<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 * Questa API restituisce la lista dei gruppi muscolari (server per il menu)
 *
 * @route /getMuscolarGroups.php
 */
$query="SELECT * FROM muscle WHERE 1";
$results=mysqli_query($conn, $query);
$musclesGroups=array();
while($muscleGroup=mysqli_fetch_object($results)){
	$singleGroup=array();
	$singleGroup["id"]=$muscleGroup->id;
	$singleGroup["name"]=$muscleGroup->muscle;
	$musclesGroups[]=$singleGroup;
}
$response["data"]["muscolarGroups"]=$musclesGroups;
echo json_encode($response);
mysqli_close($conn);

