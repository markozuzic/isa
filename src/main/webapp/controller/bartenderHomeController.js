var bartenderHomeModule = angular.module('bartenderHome.controller', []);
 

bartenderHomeModule.controller('bartenderHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	$scope.drinks = [];
	
	$scope.bartender = {};
	
	$http.get('/bartender/getLoggedIn').then(function(response) {
	   	   $scope.bartender = response.data;
	   	   var array = new Date(response.data.birthDate).toLocaleString().split(' ');
	   	   $scope.bartender.birthDate = array[0];
		 }, function(response) {
	   		alert(response.statusText);
		 });
	
	$http.get('/restaurant/getRestaurantForEmployee').then(function(response) {
	   	   $scope.restaurant = response.data;
	   	   
	   	$http.get('/order/getUnfinishedOrders').then(function(response) {
		   	   $scope.unfinishedOrders = response.data;
		   	   for(i=0; i<$scope.unfinishedOrders.length; i++){
		   		   var o = $scope.unfinishedOrders[i];
		   		   for(j=0; j<o.menuItems.length; j++) {
		   			   for(k=0; k<$scope.restaurant.drinks.length; k++){
		   				   if(o.menuItems[j].id === $scope.restaurant.drinks[k].id) {
		   					   o.menuItems[j].orderId = o.id;
			   				   $scope.drinks.push(o.menuItems[j]);
			   				   break;
		   				   }
		   			   }
		   		   }
		   	   }
		   	}, function(response) {
		   		alert(response.statusText);
		   	});
	   	   
	   	   
	   	   
	   	}, function(response) {
	   		alert(response.statusText);
	   	});
	
	
	$http.get('/shift/getAllShifts').then(function(response) {
	   	   $scope.shifts = response.data;
	   	   for(i = 0; i< $scope.shifts.length; i++) {
	   		   var d = new Date($scope.shifts[i].date);
	   		   var d2 = d.toLocaleString();
	   		   var array = d2.split(',');
	   		   $scope.shifts[i].date = array[0];
	   	   }
	   	}, function(response) {
	   		alert(response.statusText);
	   	});
	  
	  /*$http.get('/employee/getAllDrinks').then(function(response) {
	   	   $scope.drinks = response.data;
	   	}, function(response) {
	   		alert(response.statusText);
	   	});*/
	  
	  $scope.clickIzmeni = function() {
		  if ($scope.bartender.name == ""){
			  toastr.error("Ime ne moze ostati prazno!");
		  } else if ($scope.bartender.lastname == "") {
			  toastr.error("Prezime ne moze ostati prazno!");
		  } else if ($scope.bartender.password == "") {
			  toastr.error("Morate uneti lozinku!");
		  } else if ($scope.bartender.clothesSize == "") {
			  toastr.error("Morate uneti konfekcijski broj!");
		  } else if ($scope.bartender.shoeSize == "") {
			  toastr.error("Morate uneti broj cipela!");
		  } else if ($scope.bartender.email == "") {
			  toastr.error("Morate uneti email!");
		  } else if ($scope.bartender.birthDate == "") {
			  toastr.error("Morate uneti datum rodjenja!");
		  } else {
		  $http({
		        method : "POST",
		        url : "/bartender/updateProfile",
		        data : $scope.bartender,
		    }).then(function mySuccess(response) {
		    	if(response.data == "EmailError"){
		    		toastr.error("E-mail adresa je vec zauzeta.");
		    	} else
		    		toastr.success("Uspesno azuriran profil!");
		    }, function myError(response) {
		    	alert(response.statusText);
		    });
		 };
	}
	  
	  $scope.clickLogOut = function() {
			$http.get('/user/logout').then(function(response) {
		   	  	$location.path("login");
			}, function(response) {
					alert(response.statusText);
			});	
			
		}
	  
	  $scope.initDateTimePicker = function() {
	 		$('#datetime').datetimepicker({
	 			maxDate: new Date(),
	 			format: 'DD-MM-YYYY HH:mm'
	 		});
	 	}
	
}]);

	