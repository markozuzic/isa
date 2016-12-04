var app = angular.module('myApp', []);
	app.controller('registerCtrl', function($scope, $http) {
	   $scope.clickRegistrujSe = function() {
		  $http({
		        method : "POST",
		        url : "/user/register",
		        data : formToJSON($scope.ime, $scope.prezime, $scope.email, $scope.lozinka),
		    }).then(function mySuccess(response) {
		    	alert(response.data);
		    }, function myError(response) {
		    	alert(response.statusText);
		    });
		 };
	});
	
	function formToJSON(name, surname, email, password) {
		return JSON.stringify({
			"name" : name,
			"surname" : surname,
			"email" : email,
			"password" : password
		});
	}