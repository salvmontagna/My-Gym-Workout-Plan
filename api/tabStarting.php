<?php
require "conn.php";
$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;

/**
 * errorCode=1 Nessun Utente trovato
 * errorCode=2 Nessuna Scheda trovata
 */
if( isset($_GET["date"]) && isset($_GET["token"]) && isset($_GET["monday"]) && isset($_GET["tuesday"]) && isset($_GET["wednesday"]) && isset($_GET["thursday"])&& isset($_GET["friday"])&& isset($_GET["saturday"]) ){
	// individuazione utente
	//$dateTime= new \DateTime();
	//$dateTime->setTimestamp($_GET["date"]);

	$dateTime= new \DateTime($_GET["date"]);

	$query='SELECT * FROM user WHERE token LIKE "'.$_GET["token"].'"';
	$results=mysqli_query($conn, $query);
	if($results){
		$user=mysqli_fetch_object($results);
		if($user){
			mysqli_free_result($results);
			$query='SELECT * FROM tab WHERE Utenti_id = '.$user->id;
			$results=mysqli_query($conn, $query);
			if($results){
				$tab=mysqli_fetch_object($results);
				if($tab){
					mysqli_free_result($results);
					$query="UPDATE tab SET expirationTab='".$dateTime->format('d-m-Y H:i:s')."', monday=".$_GET["monday"].", tuesday=".(string)$_GET["tuesday"].", wednesday=".(string)$_GET["wednesday"].", thursday=".(string)$_GET["thursday"].", friday=".(string)$_GET["friday"].", saturday=".(string)$_GET["saturday"]." WHERE id=".$tab->id;
					//echo $query;
					mysqli_query($conn, $query);
				}
				else{
					mysqli_free_result($results);
					$query="INSERT INTO `tab`( `expirationTab`, `Utenti_id`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`) VALUES (expirationTab='".$dateTime->format('Y-m-d H:i:s')."',$user->id,".(string)$_GET['monday'].", ".(string)$_GET['tuesday'].",".(string)$_GET['wednesday'].",".(string)$_GET['thursday'].",".(string)$_GET['friday'].", ".(string)$_GET['saturday'].")";
					//echo $query;
					mysqli_query($conn, $query);
				}
			}
			else{
				$query="INSERT INTO `tab`( `expirationTab`, `Utenti_id`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`) VALUES (expirationTab='".$dateTime->format('Y-m-d H:i:s')."',$user->id,".(string)$_GET['monday'].", ".(string)$_GET['tuesday'].",".(string)$_GET['wednesday'].",".(string)$_GET['thursday'].",".(string)$_GET['friday'].", ".(string)$_GET['saturday'].")";
				//echo $query;
				mysqli_query($conn, $query);
			}
		}
		else{
			mysqli_free_result($results);
			$response["errorMessage"]="Nessun Utente Trovato";
			$response["errorCode"]=1;
		}
	}
	else{
		$response["errorMessage"]="Nessun Utente Trovato";
		$response["errorCode"]=1;
	}
	echo json_encode($response);
}
else{
	$response["errorMessage"]="Parametri mancanti";
	$response["errorCode"]=3;
	echo json_encode($response);
}
mysqli_close($conn);

