var bartenderHomeModule = angular.module('bartenderHome.controller', []);
 

bartenderHomeModule.controller('bartenderHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	  $http.get('/employee/getAllShifts').then(function(response) {
	   	   $scope.shifts = response.data;
	   	}, function(response) {
	   		alert(response.statusText);
	   	});
	  
	  $http.get('/employee/getAllDrinks').then(function(response) {
	   	   $scope.drinks = response.data;
	   	}, function(response) {
	   		alert(response.statusText);
	   	});
	  
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
		        url : "/bartender/updateProfile",
		        data : $scope.bartender,
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
	
}]);

	