var app = angular.module('myApp', []);
app.controller('personCtrl', function($scope, $window) {
   $scope.clickRegistrujSe = function() {
	   $window.location.href = '/registracija.html';
   };
});