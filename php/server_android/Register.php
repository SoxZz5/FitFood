<?php

    $connect = mysqli_connect("mysql5012.smarterasp.net", "a1452a_fitfood", "Ba8myn9c", "db_a1452a_fitfood");
    
    $name = $_POST["name"];
    $surname = $_POST["surname"];
	$pseudo = $_POST["pseudo"];
    $mail = $_POST["mail"];
    $password = $_POST["password"];


    $response = array();
    $response["success"] = false;  
	
	$valid = true;
	
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE pseudo = ?"); 
        mysqli_stmt_bind_param($statement, "s", $pseudo);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
		mysqli_stmt_close($statement);
        if ($count < 1)
		{
          $response["errorpseudo"]=false;
        }
		else 
		{
			$response["errorpseudo"]=true;
			$valid = false;
		}
	
		$statement = mysqli_prepare($connect, "SELECT * FROM user WHERE mail = ?"); 
        mysqli_stmt_bind_param($statement, "s", $mail);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement); 
        if ($count < 1)
		{
			$response["errormail"]=false;
        }
		else 
		{
			$response["errormail"]=true;
			$valid = false;
        }
	
	if($valid)
	{
		$statement = mysqli_prepare($connect, "INSERT INTO user (name, surname, pseudo, mail, password, diet_iddiet) VALUES (?, ?, ?, ?, ?,1)");
        mysqli_stmt_bind_param($statement, "sssss", $name, $surname, $pseudo, $mail, md5($password));
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);   
        $response["success"] = true;  
	}
    
    echo json_encode($response);
?>
