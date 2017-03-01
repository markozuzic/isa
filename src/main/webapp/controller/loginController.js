var loginModule = angular.module('login.controller', []);
 

loginModule.controller('loginController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	$scope.test = "radii";
	
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
	 		   }
	    }, function myError(response) {
	    	alert(response.statusText);
	    });
	}
	
	$scope.submitRegister = function () { 
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
}]);
