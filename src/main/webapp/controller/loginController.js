var loginModule = angular.module('login.controller', []);
 

loginModule.controller('loginController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {

	$scope.password1 = '';
	$scope.password2 = '';
	
	$scope.submitLogin = function () { 
	
		$http.post('/systemUser/login', $scope.user)
	    	.then(function mySuccess(response) {
	    		if(response.data == "EmailError") {
	 			   toastr.error("Uneli ste pogresnu email adresu!");
	 		   } else if (response.data == "PasswordError") {
	 			   toastr.error("Uneli ste pogresnu lozinku!");
	 		   } else {
	 			   toastr.success("Uspesno logovanje!");
	 			   if (response.data === "user") {
	 				  $location.path("guestHome");
	 			   }
	 			   else if (response.data === "restaurant") {
	 				  $location.path("restaurantManagerHome");
	 			   }
	 			   else if (response.data === "system") {
	 				  $location.path("systemManagerHome");
	 			   }
	 			   else if (response.data === "supplier") {
	 				  $location.path("supplier/" + $scope.user.email);
	 			   }
	 			   else if (response.data === "chef"){
	 				  $location.path("chef");
	 			   }
	 			   else if (response.data === "waiter") {
	 				  $location.path("waiter");
	 			   }
	 			   else if (response.data === "bartender") {
	 				  $location.path("bartender");	
	 			   }
	 			   else {
	 				   var array = response.data.split(',');
	 				   if(array[0] === "firstLogin") {
	 					   $location.path("employeeChangePassword/"+array[1]);
	 				   }
	 			   }
	 		   }
	    }, function myError(response) {
	    	alert(response.statusText);
	    });
	}
	
	$scope.submitRegister = function () { 
		var emailRegex = new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
		
	
		if($scope.user.name == "" ){
			toastr.error("Morate uneti ime!");
		} else if($scope.user.surname == "" ){
			toastr.error("Morate uneti prezime!");
		} else if ($scope.user.password == "") {
			toastr.error("Morate uneti lozinku!")
		} else if ($scope.user.password !== $scope.user.password1) {
			toastr.error("Lozinke se ne poklapaju!");
		} else if(!emailRegex.test($scope.user.email)) {
			toastr.error("Morate uneti validan e-mail!");
		} else {
		$http.post('user/register', $scope.user)
	    	.then(function mySuccess(response) {
	    		if(response.data == "EmailError"){
		    		toastr.error("Vec postoji nalog sa unetom e-mail adresom!");
		    	} else
		    		toastr.success("Uspesna registracija!");
	    			$location.path("login");
	    }, function myError(response) {
	    	alert(response.statusText);
	    });
		}
	}
	
	$scope.submitPassword = function() {
		if($scope.password1.trim().length < 8) {
			toastr.info('Lozinka mora imati barem 8 karaktera!');
		} else if ($scope.password1 !== $scope.password2) {
			toastr.error('Lozinke se ne poklapaju!');
		} else {
			toastr.success($stateParams.systemUserId);
			$http.post('systemUser/firstLogin/'+$stateParams.systemUserId, $scope.password1).then(function mySuccess(response) {
	    		$location.path("login");
	    	}, function myError(response) {
	    		alert(response.statusText);
	    	});
		}
	}

}]);
