var loginModule = angular.module('login.controller', []);
 

loginModule.controller('loginController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {

	$scope.test = "radii";
	
	$scope.submitLogin = function () { 
	
		$http.post('user/login', $scope.user)
	    	.then(function mySuccess(response) {
	    		if(response.data == "EmailError") {
	 			   toastr.error("Uneli ste pogresnu email adresu!");
	 		   } else if (response.data == "PasswordError") {
	 			   toastr.error("Uneli ste pogresnu lozinku!");
	 		   } else {
	 			   toastr.success("Uspesno logovanje!");
	 			   $location.path("guestHome");
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
	
	$scope.loginWithFacebook = function() {
		$http.get('user/loginWithFacebook').then(function mySuccess(response) {
			toastr.info('dsfdfs');
			toastr.success(response.data);
			$location.url(response.data);
    }, function myError(response) {
    	alert(response.statusText);
    });
		
	}
}]);
