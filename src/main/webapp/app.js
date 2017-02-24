'use-strict';

angular.module('restaurants', [ 'ui.router' ,'login.controller', 'guestHome.controller', 'activateAccount.controller',
								'invitation.controller'])
   
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
		
		.state('invitation', {
				url : '/invitation/:reservationId/:invitedId',
				templateUrl: 'pages/invitation.html',
				controller : 'invitationController'
		})
		
		.state('invitation.order', {
				url : '/order',
				templateUrl: 'pages/invitationOrder.html',
				controller : 'invitationController'
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
				url : "/date/:id",
				templateUrl : 'pages/guestDate.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.tables', {
				url : "/tables/:id",
				templateUrl : 'pages/guestRestaurantsTables.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.confirm', {
				url : "/confirm/:reservationId",
				templateUrl : 'pages/guestReservationConfirm.html',
				controller : 'guestHomeController'
		})
		
		.state('guestHome.order', {
				url : "/order/:reservationId",
				templateUrl : 'pages/guestReservationOrder.html',
				controller : 'guestHomeController'
		})
	});
