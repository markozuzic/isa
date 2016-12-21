var app = angular.module('myApp', []);
	app.controller('registerCtrl', function($scope, $http) {
	   
	   $scope.clickRegistrujSe = function() {
		  if ($scope.email == ""){
			  toastr.error("E-mail adresa ne moze ostati prazna!");
		  } else if ($scope.lozinka == "") {
			  toastr.error("Lozinka ne moze ostati prazna!");
		  } else if ($scope.lozinka != $scope.lozinka2) {
			  toastr.error("Lozinke se ne poklapaju!");
		  } else {
		  $http({
		        method : "POST",
		        url : "/user/register",
		        data : formToJSON($scope.ime, $scope.prezime, $scope.email, $scope.lozinka),
		    }).then(function mySuccess(response) {
		    	if(response.data == "EmailError"){
		    		toastr.error("Vec postoji nalog sa unetom e-mail adresom!");
		    	} else
		    		toastr.success("Uspesna registracija!");
		    }, function myError(response) {
		    	alert(response.statusText);
		    });
		 };
		  }
	});
	
	function formToJSON(name, surname, email, password) {
		return JSON.stringify({
			"name" : name,
			"surname" : surname,
			"email" : email,
			"password" : password
		});
	}