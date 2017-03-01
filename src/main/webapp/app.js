'use-strict';


angular.module('restaurants', [ 'ui.router' ,'login.controller', 'guestHome.controller', 'activateAccount.controller',
								'invitation.controller','activateAccount.controller', 'waiterHome.controller', 'chefHome.controller', 'bartenderHome.controller'])

   
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
		
		.state('waiterHome', {
				url: '/waiter',
				templateUrl : 'pages/waiterHome.html',
				controller : 'waiterHomeController',
				abstract : true
		})
		
		.state('waiterHome.shifts', {
				url: '',
				templateUrl : 'pages/waiterShifts.html',
				controller : 'waiterHomeController'
		})
		
		.state('waiterHome.update', {
				url: '',
				templateUrl : 'pages/updateWaiter.html',
				controller : 'waiterHomeController'
		})
		
		.state('waiterHome.tables', {
				url: '',
				templateUrl : 'pages/waiterTables.html',
				controller : 'waiterHomeController'
		})
		
		.state('waiterHome.orders', {
				url: '',
				templateUrl : 'pages/waiterOrder.html',
				controller : 'waiterHomeController'
		})
		
		.state('waiterHome.orderNew', {
				url: '/orderNew',
				templateUrl : 'pages/waiterOrderNew.html',
				controller : 'waiterHomeController'
		})
		
		.state('chefHome', {
				url: '/chef',
				templateUrl : 'pages/chefHome.html',
				controller : 'chefHomeController',
				abstract : true
		})
		
		.state('chefHome.shifts', {
				url: '',
				templateUrl : 'pages/chefShifts.html',
				controller : 'chefHomeController'
		})
		
		.state('chefHome.update', {
				url: '',
				templateUrl : 'pages/updateChef.html',
				controller : 'chefHomeController'
		})
		
		.state('chefHome.meals', {
				url: '',
				templateUrl : 'pages/mealsChef.html',
				controller : 'chefHomeController'
		})
		
		.state('bartenderHome', {
				url: '/bartender',
				templateUrl : 'pages/bartenderHome.html',
				controller : 'bartenderHomeController',
				abstract : true
		})
		
		.state('bartenderHome.shifts', {
				url: '',
				templateUrl : 'pages/bartenderShifts.html',
				controller : 'bartenderHomeController'
		})
		
		.state('bartenderHome.update', {
				url: '',
				templateUrl : 'pages/updateBartender.html',
				controller : 'bartenderHomeController'
		})
		
		.state('bartenderHome.drinks', {
				url: '',
				templateUrl : 'pages/drinksBartender.html',
				controller : 'bartenderHomeController'

		})
		
		.state('employeeChangePassword', {
				url: '/employeeChangePassword/:systemUserId',
				templateUrl : 'pages/employeeChangePassword.html',
				controller : 'loginController'

		})
	});
