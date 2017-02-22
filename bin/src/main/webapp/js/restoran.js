var app = angular.module('myApp', []);
	app.controller('restoranCtrl', function($scope, $http) {
		$scope.clickNapraviRestoran = function() {
			if ($scope.naziv == "") {
				toastr.error("Naziv restoran ne sme ostati prazan!");
			}
			else {
				$http({
					method : 'POST',
					url : 'restaurant/create',
					data : formToJSON($scope.naziv, $scope.opis),
				}).then(function mySuccess(response){
					if (response.data == "NameError"){
						toastr.error("Vec postoji restoran sa unetom adresom");
					}
					else {
						toastr.success("Uspesno kreiranje restorana");
					}
				}, function myError(response){
					alert(response.statusText);
				});
			};
		}
	});
	
	function formToJSON(name, description) {
		return JSON.stringify({
			"name" : name,
			"description" : description
		});
	}