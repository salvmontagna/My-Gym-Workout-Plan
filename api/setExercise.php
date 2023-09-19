<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 *
 * Questa api permette di settare la coppia di gruppi muscolari per un giorno specificato per uno specifico tab.
 *
 * @route /setTabGroup.php
 *
 * @param token
 * @param groupOneId
 * @param groupTwoId
 * @param dayNumber
 */
if( isset($_GET["token"]) && isset($_GET["exerciseId"]) && isset($_GET["set"]) && isset($_GET["rep"])){
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
					$query="DELETE FROM `tabexercise` WHERE tab_ID =".$tab->id." AND  exercise_ID =".$_GET["exerciseId"];  
					mysqli_query($conn, $query);
					$query="INSERT INTO `tabexercise`(`tab_ID`, `user_ID`, `exercise_ID`, `set`, `rep`) VALUES (".$tab->id.",".$user->id.",".$_GET['exerciseId'].",".$_GET["set"].",".$_GET["rep"].")";
					mysqli_query($conn, $query);
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
