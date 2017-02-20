'use-strict';

angular.module('restaurants', [ 'ui.router' ,'login.controller', 'guestHome.controller', 'activateAccount.controller'])
   
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
		
		.state('activateAccount', {
				url : '/activateAccount/:email/:code',
				templateUrl : 'pages/activateAccount.html',
				controller : 'activateAccountController'
		})
		
		.state('guestHome', {
				url : '/guestHome',
				templateUrl : 'pages/guestHome.html',
				controller : 'guestHomeController',
				abstract : true
		})
		
		.state('guestHome.profile', {
				url : "",
				templateUrl : 'pages/guestProfile.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.friends', {
				url : "",
				templateUrl : 'pages/guestFriends.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.visits', {
				url : "",
				templateUrl : 'pages/guestVisits.html',
				controller : 'guestHomeController'
		})
		
		
		.state('guestHome.restaurants', {
				url : "",
				templateUrl : 'pages/guestRestaurants.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.date', {
				url : "/date",
				templateUrl : 'pages/guestDate.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.tables', {
				url : "/tables",
				templateUrl : 'pages/guestRestaurantsTables.html',
				controller : 'guestHomeController'
		})
	});
