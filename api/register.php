<?php 
require "conn.php";

$response=array();
$response["errorCode"]=0;
$response["errorMessage"]="Registrato con successo";
$data=array();
$response["data"]=$data;

$user_mail = $_GET["user_mail"];
$user_pass = $_GET["user_pass"];


if(checkUserExistence($conn,$user_mail,$user_pass)) {
	$response["errorCode"]=1;
	$response["errorMessage"]="Email gia' esistente";
}
else{
	$token=RandomString(40);
	$mysql_qry = "INSERT INTO user (id, password, email, token) VALUES (NULL, '".$user_pass."', '".$user_mail."', '".$token."');";
	$result = mysqli_query($conn,$mysql_qry);
	$response["data"]["token"]=$token;
}




echo json_encode($response);
 
function checkUserExistence($conn,$user_mail,$user_pass){
	$mysql_qry = "SELECT * FROM user WHERE email LIKE '".$user_mail."' ";
	$result = mysqli_query($conn,$mysql_qry);
	$users=array();
	if($result){
		while($user=mysqli_fetch_object($result)){
			$users[]=$user;
		}
		if(count($users)>0){
			//echo "Trovati utenti n ". count($users);
			return true;
		}
		else{
			return false;
		}
	}
	else return false;
	
}

function RandomString($lenght)
{
	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$randstring = '';
	for ($i = 0; $i < $lenght+1; $i++) {
		$randstring .= $characters[rand(0, strlen($characters)-1)];
	}
	return $randstring;
}
mysqli_close($conn);
?>
