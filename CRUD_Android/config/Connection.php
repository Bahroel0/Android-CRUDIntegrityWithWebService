<?php 

	class Connection{
		public $conn;
		public function __construct(){
			$this->dbConnect();
		}

		public function dbConnect(){
			try{
				$hostname 	= "localhost"; 
				$username 	= "root";
				$password 	= "";
				$dbname		= "crud_android";

				$conn = new mysqli($hostname, $username,$password,$dbname);
				$this->conn = $conn;
			}catch(Exception $e){
				echo "Connection Failed : " . $e->getMessage();
			}
		}
	}

 ?>