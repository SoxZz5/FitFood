<?php
echo $_POST["username"];
if($_POST["username"] && $_POST["password"])
{
$connect = mysqli_connect("mysql5012.smarterasp.net", "a1452a_fitfood", "Ba8myn9c", "db_a1452a_fitfood");

	$username = $_POST["username"];
    $password = $_POST["password"];
 
	$statement = mysqli_prepare($connect, "SELECT adminid, username FROM admin WHERE username = ? AND password = ?"); 
			mysqli_stmt_bind_param($statement, "ss", $username, md5($password));
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			$count = mysqli_stmt_num_rows($statement);
			echo $count;
			mysqli_stmt_close($statement);
			if ($count < 1)
			{
			  echo "L'utilisateur n'existe pas";
			}
			else 
			{
			  echo "Connection";
			  $_SESSION["username"] = $username;
			  header('Location: ./admin/admin.php');  
			}
}
?>