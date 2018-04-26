<?php 
	require_once "../config/Connection.php";

	class Mahasiswa extends Connection{
		public function __construct(){
			$this->dbConnect();
		}	

		function createMahasiswa($params = []){
			$response 	= [];
			$nrp		= $params['nrp'];
			$nama		= $params['nama'];
			$jurusan	= $params['jurusan'];
			$kelas		= $params['kelas'];
			$telp		= $params['telp'];
			$alamat		= $params['alamat'];


			$query_mahasiswa_exist = "SELECT nrp FROM tb_mahasiswa where nrp ='$nrp' ";
			$sql_query = mysqli_query($this->conn, $query_mahasiswa_exist);
			$count_mahasiswa = mysqli_num_rows($sql_query);

			if($count_mahasiswa != 0){
				$response['message'] = "Data Mahasiswa dengan nrp ". $nrp . " sudah ada.";
			}else{
				$query_add_mahasiswa = "INSERT INTO tb_mahasiswa(nrp,nama,jurusan,kelas,telp,alamat) VALUES ('$nrp', '$nama', '$jurusan', '$kelas', '$telp', '$alamat')";
				$result = mysqli_query($this->conn, $query_add_mahasiswa);
				if($result){
					$response['error'] 		= 1;
					$response['message'] 	= "Data berhasil ditambahkan";
				}else{
					$response['error'] 		= 0;
					$response['message'] 	= "Error ketika input data";
				}
			}
			echo json_encode($response);
		}

		function getDataMahasiswa(){
			try{
				$response 	= [];
				$query 		= "SELECT * FROM tb_mahasiswa";
				$result 	= mysqli_query($this->conn, $query);
				$mahasiswa 	= []; 
				while($arrayResult = mysqli_fetch_array($result)){
					$mahasiswa_tmp = [];
					$mahasiswa_tmp['nrp'] 		= $arrayResult['nrp'];
					$mahasiswa_tmp['nama'] 		= $arrayResult['nama'];
					$mahasiswa_tmp['jurusan'] 	= $arrayResult['jurusan'];
					$mahasiswa_tmp['kelas'] 	= $arrayResult['kelas'];
					$mahasiswa_tmp['telp'] 		= $arrayResult['telp'];
					$mahasiswa_tmp['alamat'] 	= $arrayResult['alamat'];
					array_push($mahasiswa, $mahasiswa_tmp);
				}
				$response['error']		= 1;
				$response['message']	= "Request Complete";
				$response['mahasiswa']	= $mahasiswa;

				echo json_encode($response);
			}catch (Exception $e){
				echo $e->getMessage();
			}
		}

		function updateMahasiswa($params = []){
			$response 	= [];
			$nrp		= $params['nrp'];
			$nama		= $params['nama'];
			$jurusan	= $params['jurusan'];
			$kelas		= $params['kelas'];
			$telp		= $params['telp'];
			$alamat		= $params['alamat'];

			$query = "UPDATE tb_mahasiswa SET nama='$nama', jurusan='$jurusan', kelas='$kelas', telp='$telp', alamat='$alamat' WHERE nrp='$nrp' ";

			$update = mysqli_query($this->conn, $query);

			if ($update) {
				$response['error'] 		= 1;
				$response['message']	= "Data nrp ".$nrp. " berhasil diupdate";
			}else{
				$response['message'] = "Data nrp ".$nrp. " tidak berhasil diupdate";
			}
			echo json_encode($response);
		}

		function deleteMahasiswa($nrp){
			$response = [];
			$query 	= "DELETE FROM tb_mahasiswa WHERE nrp='$nrp' ";
			$result = mysqli_query($this->conn, $query);

			if ($result){
				$response['error'] 		= 1;
				$response['message'] = "Data nrp ".$nrp. " berhasil dihapus";
			}else{
				$response['message'] = "Data nrp ".$nrp. " gagal dihapus";
			}
			echo json_encode($response);
		}
	}
	
 ?>