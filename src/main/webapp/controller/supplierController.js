var supplierModule = angular.module('supplier.controller', []);

supplierModule.controller('supplierController', ['$scope', '$location', '$http', '$stateParams',
	function ($scope, $location, $http, $stateParams) {
	
	angular.element(document).ready(function() {
		$scope.supplier = {};
		$scope.offers = {};
		$scope.demand = {};
		$scope.offer = {};

		$http.get('/supplier/getSupplier').then(function(response) {
			$scope.supplier = response.data;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/supplier/getAllOffers').then(function(response) {
			$scope.offers = response.data;
			for (i=0; i < $scope.offers.length; i++) {
				var date = new Date($scope.offers[i].endDate);
				$scope.offers[i].endDate = date.toLocaleString();
			}
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/supplier/getActiveDemands').then(function(response) {
		   	   $scope.demands = response.data;
		   	   for(i=0; i<$scope.demands.length; i++) {
		   		   var d = $scope.demands[i];
		   		   var itemsString = '';
		   		   for(j=0; j<d.listOfItems.length; j++) {
		   			   itemsString += d.listOfItems[j].name+", ";
		   		   }
		   		   
		   		   $scope.demands[i].itemsString = itemsString;
		   		   
		   	   }
	        }, function(response) {
		   		alert(response.statusText);
		});


		$scope.createOrUpdateOffer = function(demand) {
			$scope.demand = demand;
		
		}

		$scope.setPrice = function() {
			//url: /supplier/createOrUpdate/{demandId}
			$scope.offer.demandId = $scope.demand.id;
			$http.post('/supplier/createOrUpdate/', $scope.offer).then(function(response) {
		   	   toastr.success("Ponudjeno!");
				//vrv bi trebalo dodavanje u offere, nisam sigurna da li ce sam da skine najnovije sa servera kad odes u istoriju
	       		 }, function(response) {
		   		alert(response.statusText);
			});

		}
		
	})
	
	$scope.initModifySupplier = function(){
		$http.get('/supplier/getSupplier').then(function(response) {
			$scope.supplier = response.data;
			
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.modifySupplier = function() {
		$http.post('/supplier/modifySypplier', $scope.supplier).then(function(response) {
			if (response.data === "OK") {
				toastr.success("Profil uspesno azuriran");
				$location.path("/supplierHome");
			}
			else {
				toastr.error("Email zauzet");
			}
		}, function(response){
			alert(response.statusText);
		});
	}
	
}]);