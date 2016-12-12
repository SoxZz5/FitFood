<?php

    $con = mysqli_connect("mysql5012.smarterasp.net", "a1452a_fitfood", "Ba8myn9c", "db_a1452a_fitfood");
    
    $pseudo = $_POST["pseudo"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE pseudo = ?");
    mysqli_stmt_bind_param($statement, "s", $pseudo);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colUserID, $colName, $colSurname, $colPseudo, $colMail, $colPassword, $colDiet);
    
    $response = array();
    $response["success"] = false;  
    $response["error"] = "pseudo";
	
    while(mysqli_stmt_fetch($statement)){
			if (md5($password) == $colPassword) 
			{
				$response["success"] = true;  
				$response["pseudo"] = $colPseudo;
				$response["mail"] = $colMail;
				$response["error"] = "";
			}
			else
			{
				$response["success"] = false; 
				$response["error"] = "password";
			}
    }
	
    echo json_encode($response);	
?>
