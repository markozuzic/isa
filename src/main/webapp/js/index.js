var app = angular.module('myApp', []);

app.controller('personCtrl', function($scope, $window, $http) {
	
   $scope.clickRegistrujSe = function() {
	   $window.location.href = '/registracija.html';
   };
   
   $scope.clickUlogujSe = function() {
	   var user = formToJSON("" , "", $scope.email, $scope.lozinka);
	   $http.post('/user/login', user).then(function(response) {
		   if(response.data == "EmailError") {
			   alert("Uneli ste pogresnu email adresu");
		   } else if (response.data == "PasswordError") {
			   alert("Uneli ste pogresnu lozinku");
		   } else {
			   alert("Uspesno logovanje!");
		   }
		}, function(response) {
			alert(response.data);
		});
   }
});

function formToJSON(name, surname, email, password) {
	return JSON.stringify({
		"name" : name,
		"surname" : surname,
		"email" : email,
		"password" : password
	});
}
