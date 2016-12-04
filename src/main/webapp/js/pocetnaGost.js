var app = angular.module('myApp', [])

app.controller('homePageController', function($scope, $http) {
    
	var user = "";
	angular.element(document).ready(function () {
		
		$scope.isReadOnly = true;
		
        $http.get('/user/getLoggedInUser').then(function(response) {
			   user = response.data;
			   $scope.ime = user.name;
			   $scope.prezime = user.surname;
			   $scope.email = user.email;
			   
			}, function(response) {
				alert(response.statusText);
		});
	});
	
	$scope.clickEdit = function() {
		if ($scope.isReadOnly == true) {
			$scope.isReadOnly = false;
		} else {
			$scope.isReadOnly = true;
			
			user.name = $scope.ime;
			user.surname = $scope.prezime;
			user.email = $scope.email;
			
			$http.post('/user/updateUserInfo', user).then(function(response) {
				if(response == "EmailError")
					toastr.error("Već postoji nalog sa tom email adresom.");
				else 
					toastr.success("Nalog uspešno ažuriran.");
				}, function(response) {
					alert(response.statusText);
			});
		}
	}
	
	$scope.promeniLozinku = function() {
		if($scope.staraLozinka == user.password) {
			if ($scope.novaLozinka == $scope.novaLozinka2) {
				if ($scope.novaLozinka != "") {
					user.password = $scope.novaLozinka;
					
					$http.post('/user/updatePassword', user).then(function(response) {
							if(response.data == "OK")
								toastr.success("Uspešno promenjena lozinka!");
						}, function(response) {
							alert(response.statusText);
					});
					
					$scope.staraLozinka = "";
					$scope.novaLozinka = "";
					$scope.novaLozinka2 = "";
				} else {
					toastr.error("Lozinka ne sme biti prazna!");
					$scope.staraLozinka = "";
					$scope.novaLozinka2 = "";
				}
			} else {
				toastr.error("Lozinke se ne podudaraju.");
				$scope.staraLozinka = "";
				$scope.novaLozinka = "";
				$scope.novaLozinka2 = "";
			}
		} else {
			toastr.error("Pogrešna lozinka!");
			$scope.staraLozinka = "";
			$scope.novaLozinka = "";
			$scope.novaLozinka2 = "";
			
		}
	}
});