<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 * Questa API restituscie la tab.
 * @route /getTab.php
 * @param token
 */
if( isset($_GET["token"]) && isset($_GET["groupId"])){
	$query='SELECT * FROM user WHERE token LIKE "'.$_GET["token"].'"';
	$results=mysqli_query($conn, $query);
	if($results) {
		$user = mysqli_fetch_object( $results );
		if ( $user ) {
			mysqli_free_result( $results );
			$query   = 'SELECT * FROM tab WHERE Utenti_id = ' . $user->id;
			$results = mysqli_query( $conn, $query );
			if ( $results ) {
				$tab = mysqli_fetch_object( $results );
				if ( $tab ) {
					$query="SELECT * FROM `tabexercise` AS tx
							JOIN exercise AS e ON e.id=tx.exercise_ID
							WHERE tx.tab_ID=".$tab->id." AND e.Muscle_id=".$_GET["groupId"];

					$results=mysqli_query($conn, $query);
					if($results){
						$exercises=array();
						while ($retrievedData=mysqli_fetch_object($results)){
							$singleExercise=array();
							$singleExercise["name"]=$retrievedData->exercise;
							$singleExercise["id"]=$retrievedData->exercise_ID;
							$singleExercise["set"]=$retrievedData->set;
							$singleExercise["rep"]=$retrievedData->rep;

							$exercises[]=$singleExercise;
						}
						$response["data"]["exercises"]=$exercises;
					}
					else{
						$response["errorCode"]=4;
						$response["errorMessage"]="Nessun esercizio tovato per il gruppo ".$_GET["groupId"]." e per la scheda ID ".$tab->id;
					}
				}
				else {
					$response["errorCode"]=1;
					$response["errorMessage"]="Tab non trovato";
				}
			}
			else{
				$response["errorCode"]=1;
				$response["errorMessage"]="Tab non trovato";
			}
		}
		else{
			$response["errorCode"]=2;
			$response["errorMessage"]="Utente non trovato";
		}
	}
	else{
		$response["errorCode"]=2;
		$response["errorMessage"]="Utente non trovato";
	}

}
else{
	$response["errorCode"]=3;
	$response["errorMessage"]="Parametri mancanti";
}
echo json_encode($response);
mysqli_close($conn);
