<?php
	include '../model/mahasiswa.php';
	include '../config/config.php';
	function isTheseParametersAvailable($params){
		$available = true;
		$missingparams = "";

		foreach($params as $param){
 			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				 $available = false;
				 $missingparams = $missingparams . ", " . $param; 
  			}
 		}

		if(!$available){
			 $response = array();
			 $response['error'] = true;
			 $response['message'] = 'Parameters ' . substr($missingparams, 1,
			 strlen($missingparams)) . ' missing';

			 echo json_encode($response);
			 die();
		}
	}

	$response = array();
	if(isset($_GET['apicall'])){
		switch($_GET['apicall']){
			case 'create_mahasiswa':
				isTheseParametersAvailable(array('nrp','nama','jurusan','kelas','telp','alamat'));
		 		$result=createMahasiswa($conn,$_POST['nrp'],$_POST['nama'],$_POST['jurusan'],$_POST['kelas'],$_POST['telp'],$_POST['alamat']);
		 		if($result){
					 $response['error']=false;
					 $response['message'] = 'Mahasiswa berhasil ditambahkan';
					 $response['mahasiswa'] = getMahasiswa($conn);
		 		}else{
					 $response['error'] = true;
					 $response['message'] = 'Some error';
		 		}
 				break;

			 case 'update_mahasiswa':
				 isTheseParametersAvailable(array('nrp','nama','jurusan','kelas','telp','alamat'));
				 $result=updateMahasiswa($conn,$_POST['nrp'],$_POST['nama'],$_POST['jurusan'],$_POST['kelas'],$_POST['telp'],$_POST['alamat']);
				 if($result){
					 $response['error']=false;
					 $response['message'] = 'Mahasiswa berhasil diupdate';
					 $response['mahasiswa'] = getMahasiswa($conn);
				 }else{
					 $response['error'] = true;
					 $response['message'] = 'Some error';
				 }
				 break;

 			case 'get_mahasiswa':
				 $response['error'] = false;
				 $response['message'] = 'Request successfully completed';
				 $response['mahasiswa'] = getMahasiswa($conn);
				 break;

 			case 'delete_mahasiswa':
				 if(isset($_GET['nrp'])){
				 	if(deleteMahasiswa($conn,$_GET['nrp'])){
						 $response['error']=false;
						 $response['message'] = 'Delete mahasiswa berhasil';
						 $response['mahasiswa'] = getMahasiswa($conn);
					 }else{
							 $response['error'] = true;
							 $response['message'] = 'Some error';
					 }
				 }else{
					 $response['error'] = true;
					 $response['message'] = 'Nothing to delete';
				 }
				 break;
 		}
	}
	else{
		 $response['error'] = true;
		 $response['message'] = 'Invalid API Call';
	}
	echo json_encode($response);