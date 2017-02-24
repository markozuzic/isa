
var invitationModule = angular.module('invitation.controller', []);
 

invitationModule.controller('invitationController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {
 	
	$scope.responded = false;
	$scope.order = {};
	$scope.order.items = [];
	
	angular.element(document).ready(function () {
		$http.get('/reservation/getReservation/'+$stateParams.reservationId).then(function(response) {
			$scope.reservation = response.data;
			var date = new Date($scope.reservation.dateTime);
			$scope.reservation.dateTime = date.toLocaleString();
			var tableString = $scope.reservation.tables[0].tableNumber;
			for (i=1; i<$scope.reservation.tables.length; i++){
				tableString += ", "+$scope.reservation.tables[i].tableNumber;
			}
			$scope.reservation.tableString = tableString;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/reservation/getRestaurant/'+$stateParams.reservationId).then(function(response) {
			$scope.restaurant = response.data;
			for(i=0; i<$scope.restaurant.menu.length; i++){
				$scope.restaurant.menu[i].ordered = false;
			}
			for(i=0; i<$scope.restaurant.drinks.length; i++){
				$scope.restaurant.drinks[i].ordered = false;
			}
		}, function(response) {
			alert(response.statusText);
		});
		
	});
	
	$scope.acceptInvitation = function() {
		$scope.responded = true; 
		$http.get('/reservation/acceptInvitation/'+$stateParams.reservationId + "/"+$stateParams.invitedId).then(function(response) {
			$location.path('/invitation/'+$stateParams.reservationId+'/'+$stateParams.invitedId+'/order');
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.declineInvitation = function() {
		$scope.responded = true;
		$http.get('/reservation/declineInvitation/'+$stateParams.reservationId + "/"+$stateParams.invitedId).then(function(response) {
			//toastr.info("Poslao");
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.orderItem = function(itemId, isDrink){
		if(isDrink === 'true'){
			for(i=0; i<$scope.restaurant.drinks.length; i++){
				if($scope.restaurant.drinks[i].id === itemId) {
					$scope.restaurant.drinks[i].ordered = true;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.restaurant.menu.length; i++){
				if($scope.restaurant.menu[i].id === itemId) {
					$scope.restaurant.menu[i].ordered = true;
					break;
				}
			}
		}
		
		$scope.order.items.push(itemId);
		toastr.success('Stavka dodata na porudzbinu!');
	}
	
	$scope.removeItem = function(itemId, isDrink){
		var index = $scope.order.items.indexOf(itemId);
		if (index > -1) {
			$scope.order.items.splice(index, 1);
		}
		toastr.success($scope.order.items);
		if(isDrink === 'true'){
			for(i=0; i<$scope.restaurant.drinks.length; i++){
				if($scope.restaurant.drinks[i].id === itemId) {
					$scope.restaurant.drinks[i].ordered = false;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.restaurant.menu.length; i++){
				if($scope.restaurant.menu[i].id === itemId) {
					$scope.restaurant.menu[i].ordered = false;
					break;
				}
			}
		}
	}
}]);

/*.controller('MyCtrl1', ['$scope','$routeParams', function($scope, $routeParams) {
  var param1 = $routeParams.param1;
  var param2 = $routeParams.param2; //change here from param1 to param2
  ...
}]);*/