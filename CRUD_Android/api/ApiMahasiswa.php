<?php 
	require_once "../config/Connection.php";
	require_once "../model/Mahasiswa.php";

	$api = $_GET['api'];
	$connection = new Connection();
	$mahasiswa = new Mahasiswa();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		switch($api){
			case 'createMahasiswa' :
				$mahasiswa->createMahasiswa([
					'nrp' 		=>$_POST['nrp'],
					'nama'		=>$_POST['nama'],
					'jurusan' 	=>$_POST['jurusan'],
					'kelas'		=>$_POST['kelas'],
					'telp' 		=>$_POST['telp'],
					'alamat'	=>$_POST['alamat']
				]);
				break;
			case 'updateMahasiswa' :
				$mahasiswa->updateMahasiswa([
					'nrp' 		=>$_POST['nrp'],
					'nama'		=>$_POST['nama'],
					'jurusan' 	=>$_POST['jurusan'],
					'kelas'		=>$_POST['kelas'],
					'telp' 		=>$_POST['telp'],
					'alamat'	=>$_POST['alamat'],
				]);
				break;
			case 'deleteMahasiswa' :
				$mahasiswa->deleteMahasiswa($_POST['nrp']);
				break;
			// case 'getMahasiswa' :
			// 	$mahasiswa->getMahasiswa();
			// 	break;


		}
	}else{
			$mahasiswa->getDataMahasiswa();
	}
 ?>




