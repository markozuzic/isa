'use-strict';

angular.module('restaurants', [ 'ui.router' ,'login.controller', 'guestHome.controller'])
   
	.config(function($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise('/login');

		$stateProvider
			
		.state('login', {
				url : '/login',
				templateUrl : 'pages/login.html',
				controller : 'loginController'
		})
		
		.state('register', {
				url : '/register',
				templateUrl : 'pages/register.html',
				controller : 'loginController'
		})
		
		.state('guestHome', {
				url : '/guestHome',
				templateUrl : 'pages/guestHome.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.friends', {
				url : "",
				templateUrl : 'pages/guestFriends.html',
				controller : 'guestHomeController'
		})

		.state('guestHome.profile', {
				url : "",
				templateUrl : 'pages/guestProfile.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.visits', {
				url : "",
				templateUrl : 'pages/guestVisits.html',
				controller : 'guestHomeController'
		})
	});
