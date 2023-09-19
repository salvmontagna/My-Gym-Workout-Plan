<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 * Questa API restituisce tutti i gruppi e tutti gli esercizi di una specifica scheda in uno specifico giorno
 * @exemple getTabGroup.php?token=qioUauZNBaj2HVN6doUyNP8EYxYnGu36Nr1XV0Lxb&dayNumber=3  (token di user 3)
 *
 * @route /getTabGroup.php
 * @param token
 * @param dayNumber
 *
 */
if( isset($_GET["token"]) && isset($_GET["dayNumber"]) ){
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
					$query="SELECT m.* FROM `tabgroup` AS tb
							JOIN muscle AS m on tb.groupId= m.id
							WHERE tb.`tabId`=".$tab->id." AND tb.`dayNumber` = ".$_GET["dayNumber"];
					//echo $query;
					$resultsFirst=mysqli_query($conn, $query);
					if($resultsFirst){
						//echo "check";
						$tabResults=array();
						$response["data"]["groupsExercises"]=array();
						while ($tabGroupValue=mysqli_fetch_object($resultsFirst)){
							//echo "GROUP ".$tabGroupValue->id."<br>";
							$retrievedTabGroup            =array();
							$retrievedTabGroup["groupId"] =$tabGroupValue->id;
							$retrievedTabGroup["groupName"] =$tabGroupValue->muscle;
							$query                        ="SELECT ex.id AS id, ex.exercise AS exerciseName,  tx.set AS 'set', tx.rep AS rep
									FROM  `tabexercise` AS tx
									JOIN exercise AS ex ON tx.`exercise_ID` = ex.id
									JOIN muscle AS m ON ex.Muscle_id = m.id
									WHERE  `tab_ID` = ".$tab->id." AND m.id= ".$tabGroupValue->id;
							//echo $query;
							$results                      =mysqli_query($conn, $query);
							if($results){
								$exercises=array();
								while ($exercise=mysqli_fetch_object($results)){
									$retrievedExercise                 =array();
									$retrievedExercise["id"]           =$exercise->id;
									$retrievedExercise["exerciseName"] =$exercise->exerciseName;
									$retrievedExercise["groupId"]      =$tabGroupValue->id;
									$retrievedExercise["groupName"]    =$tabGroupValue->muscle;
									$retrievedExercise["set"]          =$exercise->set;
									$retrievedExercise["rep"]          =$exercise->rep;
									//$retrievedTabGroup["exercises"]    =$retrievedExercise;
									$exercises[]=$retrievedExercise;

								}
								$response["data"]["groupsExercises"]["group_".$tabGroupValue->id] =$exercises;
							}
							else{
								$response["errorCode"]=7;
								$response["errorMessage"]="Nessun esercizio trovato per questo tab per questo gurppo muscolare.";
							}

						}
						/*if(count($tabResults)!=2){
							$response["errorCode"]=6;
							$response["errorMessage"]="Numero di gruppo trovati inferiore a 2";
						}*/
					}
					else{
						$response["errorCode"]=4;
						$response["errorMessage"]="Nessun Gruppo muscolare trovato per questa scheda in questo giorno";
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
