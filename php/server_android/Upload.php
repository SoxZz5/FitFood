<?php

    $con = mysqli_connect("mysql5012.smarterasp.net", "a1452a_fitfood", "Ba8myn9c", "db_a1452a_fitfood");
    
    $image = $_POST["image"];
   
    $response = array();
    $response["success"] = false;  
    $response["error"] = "image";
	
	$sql ="SELECT idrecipe_image FROM recipe_image ORDER BY idrecipe_image ASC";
 
		 $res = mysqli_query($con,$sql);
		 
		 $id = 0;
		 
		 while($row = mysqli_fetch_array($res)){
		 $id = $row['id'];
		 }
		 
	 $path = "uploads/$id.png";
 
	 $actualpath = "http://soxzer-001-site1.ftempurl.com/server_android/$path";
		 
		 $sql = "INSERT INTO recipe_image (name) VALUES ('$actualpath')";
		 
		 if(mysqli_query($con,$sql)){
		 file_put_contents($path,base64_decode($image));
		 echo "Successfully Uploaded";
		 }
	mysqli_close($con);
    echo json_encode($response);	
?>
