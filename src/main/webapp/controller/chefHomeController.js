var chefHomeModule = angular.module('chefHome.controller', []);
 

chefHomeModule.controller('chefHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	$scope.meals = [];
	
	$scope.chef = {};
	
	$http.get('/chef/getLoggedIn').then(function(response) {
	   	   $scope.chef = response.data;
	   	   var array = new Date(response.data.birthDate).toLocaleString().split(' ');
	   	   $scope.chef.birthDate = array[0];
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
		   			   for(k=0; k<$scope.restaurant.menu.length; k++){
		   				   if(o.menuItems[j].id === $scope.restaurant.menu[k].id) {
		   					   o.menuItems[j].orderId = o.id;
			   				   $scope.meals.push(o.menuItems[j]);
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
	
	
	
	/*$http.get('/employee/getAllMeals').then(function(response) {
	  	   $scope.meals = response.data;
	  	}, function(response) {
	  		alert(response.statusText);
	  	});*/
	     
	
	 $scope.clickIzmeni = function() {
		 if ($scope.chef.name == ""){
			  toastr.error("Ime ne moze ostati prazno!");
		  } else if ($scope.chef.lastname == "") {
			  toastr.error("Prezime ne moze ostati prazno!");
		  } else if ($scope.chef.password == "") {
			  toastr.error("Morate uneti lozinku!");
		  } else if ($scope.chef.clothesSize == "") {
			  toastr.error("Morate uneti konfekcijski broj!");
		  } else if ($scope.chef.shoeSize == "") {
			  toastr.error("Morate uneti broj cipela!");
		  } else if ($scope.chef.email == "") {
			  toastr.error("Morate uneti email!");
		  } else if ($scope.chef.birthDate == "") {
			  toastr.error("Morate uneti datum rodjenja!");
		  }  else {
		  $http({
		        method : "POST",
		        url : "/chef/updateProfile",
		        data : $scope.chef,
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

