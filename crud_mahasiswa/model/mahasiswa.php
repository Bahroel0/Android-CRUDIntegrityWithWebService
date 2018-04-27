<?php 
	function createMahasiswa($conn,$nrp,$nama,$jurusan,$kelas,$telp,$alamat){
		 $sql="INSERT INTO profile(nrp,nama,jurusan,kelas,telp,alamat) VALUES('$nrp','$nama','$jurusan','$kelas','$telp','$alamat')";
		 if(mysqli_query($conn,$sql)) {
			 return true;
		 }
		 mysqli_close($conn);
		 return false;
	}

	function getMahasiswa($conn){
		 $sql="SELECT * FROM profile";
		 $result=mysqli_query($conn, $sql);
		 $mahasiswa=array();
		 while($row = mysqli_fetch_array($result)){
			 $mahasiswa_temp=array();
			 $mahasiswa_temp['nrp']=$row['nrp'];
			 $mahasiswa_temp['nama']=$row['nama'];
			 $mahasiswa_temp['jurusan']=$row['jurusan'];
			 $mahasiswa_temp['kelas']=$row['kelas'];
			 $mahasiswa_temp['telp']=$row['telp'];
			 $mahasiswa_temp['alamat']=$row['alamat'];
			 array_push($mahasiswa,$mahasiswa_temp);
	 	}
		mysqli_close($conn);
	 	return $mahasiswa;
	}

	function updateMahasiswa($conn,$nrp,$nama,$jurusan,$kelas,$telp,$alamat){
		 $sql="UPDATE profile SET nama='$nama',jurusan='$jurusan',kelas='$kelas',telp='$telp',alamat='$alamat' WHERE nrp='$nrp' ";
		 if(mysqli_query($conn,$sql)) {
			 return true;
		 }
		 mysqli_close($conn);
		 return false;
	}

	function deleteMahasiswa($conn,$nrp){
		 $sql="DELETE FROM profile WHERE nrp='$nrp' ";
		 if(mysqli_query($conn,$sql)) {
		 	return true;
		 }
		 mysqli_close($conn);
		 return false;
	}
 ?>