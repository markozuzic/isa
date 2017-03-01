var supplierModule = angular.module('supplier.controller', []);

supplierModule.controller('supplierController', ['$scope', '$location', '$http', '$stateParams',
	function ($scope, $location, $http, $stateParams) {
		
	angular.element(document).ready(function() {
		$http.get('/supplier/getAllOffers/' + $stateParams.supplierEmail).then(function(response) {
			$scope.offers = response.data;
			for (i=0; i<$scope.offers.length; i++) {
				var date = new Date($scope.offers[i].endDate);
				$scope.offers[i].endDate = date.toLocaleString();
			}
		}, function(response) {
			alert(response.statusText);
		});
	})
	
	$scope.clickModifySupplier = function() {
		$location.path("/supplier/modify/" + $stateParams.supplierEmail);
	}
	
	$scope.initModifySupplier = function(){
		$http.get('/supplier/getSupplier/' + $stateParams.supplierEmail).then(function(response) {
			$scope.supplier = response.data;
			
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.modifySupplier = function() {
		$http.post('/supplier/modifySypplier', $scope.supplier).then(function(response) {
			if (response.data === "OK") {
				toastr.success("Profil uspesno azuriran");
				$location.path("/supplier/" + $scope.supplier.email);
			}
			else {
				toastr.error("Email zauzet");
			}
		}, function(response){
			alert(response.statusText);
		});
	}
	
}]);