var app = angular.module('myApp', []);
app.controller('managerCtrl', function($scope, $http) {
	
	$scope.clickCreateManager = function() {
		toastr.error($scope.manager.type);
		if ($scope.manager.email == "") {
			toastr.error("Email ne sme ostati prazan!");
		}
		else {
			
			
			
			 $http.post('/manager/register', $scope.manager).then(function(response) {
				 if (response.data == "NameError"){
						toastr.error("Vec postoji manager sa unetom email adresom");
					}
					else {
						toastr.success("Uspesno kreiranje managera");
					}
				}, function(response) {
					alert(response.statusText);
			   });
			 
			
		};
	}
});