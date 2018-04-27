<?php
$hostname = "localhost";
$username = "root";
$password = "";
$dbname = "crud_mahasiswa";
$conn = mysqli_connect($hostname, $username, $password, $dbname);
if (!$conn) {
 die("Koneksi Gagal: " . mysqli_connect_error());
}
