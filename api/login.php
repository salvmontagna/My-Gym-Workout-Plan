<?php 
require "conn.php";

$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="";
$data=array();
$response["data"]=$data;
$response["data"]["tabPresent"]=false;

if(isset($_GET["user_mail"]) && isset($_GET["user_pass"])){
	$user_mail = $_GET["user_mail"];
	$user_pass = $_GET["user_pass"];
	$mysql_qry = "SELECT * FROM user WHERE email LIKE '".$user_mail."' AND password LIKE '".$user_pass."' ";
	$result = mysqli_query($conn ,$mysql_qry);
	if(mysqli_num_rows($result) > 0) {
		$user=mysqli_fetch_array($result);
		$userId=$user["id"];
		$mysql_qry_sheet= "SELECT * FROM tab WHERE Utenti_id=".$userId;
		$resultTab=mysqli_query($conn,$mysql_qry_sheet);
		

		
		if(mysqli_fetch_array($resultTab)!= null){
			$response["data"]["tabPresent"]=true;

		}
		//TODO Creazione Token
		$token=RandomString(40);
		//TODO Update utente
		$query="UPDATE user SET token= '".$token."' WHERE id= ".$user["id"];
		mysqli_query($conn ,$query);
		//TODO Restituzione token"
		$response["data"]["token"]=$token;
		//echo "Accesso effettuato con successo";
	}
	else {
		$response["errorCode"]=1;
		$response["errorMessage"]="Nessun Utente Trovato";
		//echo "L'accesso non ha avuto successo";
	}
}else{
	$response["errorCode"]=2;
	$response["errorMessage"]="Credenziali non valide";
	if(isset($_GET['merda']))
		$response["data"]["lammerda"]=$_GET;
	if(isset($_GET['merda']))
		$response["data"]["lammerda"]=$_GET;
}

echo json_encode($response);
 
 /**
 * Crea una stringa random
 */
 function RandomString($lenght)
{
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < $lenght+1; $i++) {
        $randstring .= $characters[rand(0, strlen($characters)-1)];
    }
    return $randstring;
}
?>