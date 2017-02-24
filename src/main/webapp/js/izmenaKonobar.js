var app = angular.module('myApp', []);
	app.controller('updateWaiterCtrl', function($scope, $http) {
	  
		
	   $scope.clickIzmeni = function() {
		  if ($scope.ime == ""){
			  toastr.error("Ime ne moze ostati prazno!");
		  } else if ($scope.prezime == "") {
			  toastr.error("Prezime ne moze ostati prazno!");
		  } else if ($scope.lozinka == "") {
			  toastr.error("Morate uneti lozinku!");
		  } else {
		  $http({
		        method : "POST",
		        url : "/waiter/updateProfile",
		        data : $scope.waiter,
		    }).then(function mySuccess(response) {
		    	if(response.data == "IdError"){
		    		toastr.error("Ne postoji id!");
		    	} else
		    		toastr.success("Uspesna registracija!");
		    }, function myError(response) {
		    	alert(response.statusText);
		    });
		 };
		  }
	});
	
	function formToJSON(name, lastname, birthDate, clothesSize, shoeSize, password) {
		return JSON.stringify({
			"name" : name,
			"lastname" : lastname,
			"birthDate" : birthDate,
			"clothesSize" : clothesSize,
			"shoeSize" : shoeSize,
			"password" : password
		});
	}