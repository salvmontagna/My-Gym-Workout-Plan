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
 * @param groupThreeId
 * @param groupFourId
 * @param groupFiveId
 * @param groupSixId
 * @param groupSevenId
 * @param dayNumber
 */
if( isset($_GET["token"]) && isset($_GET["groupOneId"]) && isset($_GET["groupTwoId"]) && isset($_GET["groupThreeId"]) && isset($_GET["groupFourId"]) && isset($_GET["groupFiveId"]) && isset($_GET["groupSixId"]) && isset($_GET["groupSevenId"]) && isset($_GET["dayNumber"])){
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
					$query="DELETE FROM `tabgroup` WHERE tabId=".$tab->id." AND dayNumber = ".$_GET["dayNumber"];
					mysqli_query($conn, $query);
					$query="INSERT INTO `tabgroup`( `tabId`, `groupId`,  `dayNumber`) VALUES (".$tab->id.",".$_GET["groupOneId"].", ".$_GET["dayNumber"]."),(".$tab->id.",".$_GET["groupTwoId"].", ".$_GET["dayNumber"].", (".$tab->id.",".$_GET["groupThreeId"].", ".$_GET["dayNumber"]."), (".$tab->id.",".$_GET["groupFourId"].", ".$_GET["dayNumber"]."), (".$tab->id.",".$_GET["groupFiveId"].", ".$_GET["dayNumber"]."), (".$tab->id.",".$_GET["groupSixId"].", ".$_GET["dayNumber"]."), (".$tab->id.",".$_GET["groupSevenId"].", ".$_GET["dayNumber"]."),) ";
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
