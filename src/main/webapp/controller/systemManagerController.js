var systemManagerModule = angular.module('systemManager.controller', []);
 

systemManagerModule.controller('systemManagerController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {

	angular.element(document).ready(function () {
		$http.get('/restaurant/getAllRestaurants').then(function(response) {
			   $scope.restaurants = response.data;
			}, function(response) {
				alert(response.statusText);
		   });
	})
	
	$scope.createManager = function(id) {
		$location.path("/createManager/" + id);
	}
	
	$scope.createRestaurantManager = function() {	
		$http.post("/manager/register/" + $stateParams.restaurantId, $scope.manager).then(function(response) {
			   if (response.data === "OK") {
				   toastr.success("Uspesno dodat");
				   $location.path("/systemManagerHome");
			   }
			   else {
				   toastr.error("Email zauzet");
			   }
			}, function(response) {
				alert(response.statusText);
		   });
	}
	
	$scope.clickCreateSystemManager = function() {
		$location.path("/createSystemManager");
	}
	
	$scope.createSystemManager = function() {
		$http.post("/systemManager/register", $scope.manager).then(function(response) {
			if (response.data === "OK") {
				toastr.success("Uspesno dodat");
				$location.path("/systemManagerHome");
			}
			else {
				toastr.error("Email zauzet");
			}
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.clickCreateRestaurant = function() {
		$location.path("/createRestaurant");
	}
	
	$scope.createRestaurant = function() {
		$http.post("/restaurant/create", $scope.restaurant).then(function(response) {
			if (response.data === "OK") {
				toastr.success("Restoran uspesno napravljen");
				$location.path("/systemManagerHome");
			}
			else {
				toastr.error("Postoji restoran sa unetim imenom");
			}
		}, function(response){
			alert(response.statusText);
		});
	}
	$scope.clickLogOut = function() {
		$http.get('/user/logout').then(function(response) {
	   	  	$location.path("login");
		}, function(response) {
				alert(response.statusText);
		});	
		
	}
	
}]);
