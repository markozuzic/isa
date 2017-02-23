
var activateAccountModule = angular.module('activateAccount.controller', []);
 

activateAccountModule.controller('activateAccountController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {
	
	$scope.user = {
	        activationCode : $stateParams.code,
	        email : $stateParams.email
	    };
 	
	angular.element(document).ready(function () {
		$http.post('/user/activateAccount', $scope.user).then(function(response) {
			if(response.data == "OK") {
				$scope.message = "Uspešno sve aktivirali nalog!"
			
			} else {
				$scope.message = "Došlo je do greške.";
			}
		}, function(response) {
			alert(response.statusText);
		});
	});
	
}]);

/*.controller('MyCtrl1', ['$scope','$routeParams', function($scope, $routeParams) {
  var param1 = $routeParams.param1;
  var param2 = $routeParams.param2; //change here from param1 to param2
  ...
}]);*/