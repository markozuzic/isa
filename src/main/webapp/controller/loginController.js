var loginModule = angular.module('login.controller', []);
 

loginModule.controller('loginController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {

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
	 			   //$location.path("guestHome");
	 			  // $location.path("waiter");
	 			   $location.path("chef");
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
	
	$scope.submitPassword = function() {
		if($scope.password1.length < 8) {
			toastr.info('Lozinka mora imati barem 8 karaktera!');
		} else if ($scope.password1 !== $scope.password2) {
			toastr.error('Lozinke se ne poklapaju!');
		} else {
			
			$http.post('user/firstLogin/'+$stateParams.systemUserId, $scope.password).then(function mySuccess(response) {
	    		$location.path("login");
	    	}, function myError(response) {
	    		alert(response.statusText);
	    	});
		}
	}

}]);
