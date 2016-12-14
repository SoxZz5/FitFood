<?php

    $con = mysqli_connect("mysql5012.smarterasp.net", "a1452a_fitfood", "Ba8myn9c", "db_a1452a_fitfood");
    
    $image = $_POST["image"];
    $name = $_POST["name"];
	$recipe = $POST["recipeid"];

    $statement = mysqli_prepare($con, "SELECT * FROM recipe_image WHERE name = ?");
    mysqli_stmt_bind_param($statement, "s", $name);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colIdImage, $colName, $colIdRecipe);
    
    $response = array();
    $response["success"] = false;  
    $response["error"] = "image";
	
    while(mysqli_stmt_fetch($statement)){
			$response["success"] = false;  
    }
	
	$path = "/uploads/".$name.".png";
	
	$actualpath = "http://soxzer-001-site1.ftempurl.com".$path;
	
	$sql = "INSERT INTO recipe_image (name,recipe_idrecipe) VALUES ('".$actualpath."',".$recipe.")";
	
	if(mysqli_query($con,$sql))
	{
		file_put_contents($path,base64_decode($image))
		$response["success"]=true;
	}
	mysqli_close($con);
    echo json_encode($response);	
?>
