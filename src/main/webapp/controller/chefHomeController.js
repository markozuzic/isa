var chefHomeModule = angular.module('chefHome.controller', []);
 

chefHomeModule.controller('chefHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	$http.get('/employee/getAllShifts').then(function(response) {
	  	   $scope.shifts = response.data;
	  	}, function(response) {
	  		alert(response.statusText);
	  	});
	
	$http.get('/employee/getAllMeals').then(function(response) {
	  	   $scope.meals = response.data;
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
		        url : "/chef/updateProfile",
		        data : $scope.chef,
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

