<!DOCTYPE html>
<html>
<body>
<head>
<?php session_start();?>
  <link rel="stylesheet" href="style.css">
</head>
<div id="warp">
  <form  action="" id="formu">
    	<div class="admin">
			      <div class="rota">
				        <h1>FITFOOD</h1>
        				<input id="username" type="text" name="username" value="" placeholder="Username"/><br />
				        <input id="password" type="password" name="password" value="" placeholder="Password"/>
      			</div>
    		</div>
    		<div class="cms">
      			<div class="roti">
			        	<h1>ADMIN</h1>
				        <input id="valid" type="submit" name="valid" value="Valid"/><br />

      </div>
    		</div>
  	</form>
</div>
<?php include "./connection.php"?>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="connection.js"></script>
</body>
</html>