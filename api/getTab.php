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
if( isset($_GET["token"])){
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
					$responseTab=array();
					$responseTab["date"]=$tab->expirationTab;
					$responseTab["monday"]=(int)$tab->monday;
					$responseTab["tuesday"]=(int)$tab->tuesday;
					$responseTab["wednesday"]=(int)$tab->wednesday;
					$responseTab["thursday"]=(int)$tab->thursday;
					$responseTab["friday"]=(int)$tab->friday;
					$responseTab["saturday"]=(int)$tab->saturday;
					$response["data"]["tab"]=$responseTab;
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
