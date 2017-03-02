var guestHomeModule = angular.module('guestHome.controller', []);
 

guestHomeModule.controller('guestHomeController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {

	$scope.duration="";
	$scope.rest = "";
	var user = "";
	
	Date.prototype.addHours= function(h){
	    this.setHours(this.getHours()+h);
	    return this;
	}
	
	angular.element(document).ready(function () {
		
		$scope.isReadOnly = true;
		$scope.sortType     = 'name'; // set the default sort type
		$scope.sortReverse  = false;  // set the default sort order
		$scope.searchTerm   = '';     // set the default search/filter term
		
        $http.get('/user/getLoggedInUser').then(function(response) {
			   user = response.data;
			   $scope.ime = user.name;
			   $scope.prezime = user.surname;
			   $scope.email = user.email;
			   
			   
			}, function(response) {
				alert(response.statusText);
		});
       
        $http.get('/user/getAllUsers').then(function(response) {
		   $scope.allUsers = response.data;
		}, function(response) {
			alert(response.statusText);
	   });
        
        $http.get('/reservation/getAllReservations').then(function(response) {
 		   $scope.allReservations = response.data;
 		}, function(response) {
 			alert(response.statusText);
 	    });
        
        $http.get('/user/getFriends').then(function(response) {
        	$scope.friends = response.data;
 		}, function(response) {
 			alert(response.statusText);
 	    });
        
        $http.get('/user/getPendingRequests').then(function(response) {
        	$scope.pendingRequests = response.data;
        	for(i=0; i<$scope.pendingRequests.length; i++)
        		$scope.pendingRequests[i].responded = false;
 		}, function(response) {
 			alert(response.statusText);
 	   });
        
        $http.get('/user/getFriendSuggestions').then(function(response) {
        	$scope.friendSuggestions = response.data;
        	for(i=0; i<$scope.friendSuggestions.length; i++)
        		$scope.friendSuggestions[i].added = false;
 		}, function(response) {
 			alert(response.statusText);
 	    });
        
        $scope.star1 = false;
        $scope.star2 = false;
        $scope.star3 = false;
        $scope.star4 = false;
        $scope.star5 = false;
        
        $http.get('/restaurant/getAllRestaurants').then(function(response) {
        	$scope.restaurants = response.data;	
        }, function(response) {
        	alert(response.statusText);
        });
        
        $http.get('/user/getVisits').then(function(response) {
   		   $scope.visits = response.data;
   		   var today = new Date();
   		   for(i=0; i<$scope.visits.length; i++){
   			   var date = new Date($scope.visits[i].reservation.dateTime);
   			   var month = date.getMonth() + 1;
   			   var day = date.getDate();
   			   var year = date.getFullYear();
   			   var shortStartDate = day + "/" + month + "/" + year;
   			   $scope.visits[i].reservation.dateTime = shortStartDate;
   			   
   			   if(date.getTime() > today.getTime()){	//nije jos pocela
   				   $scope.visits[i].rateButton = false; //ne moze da se oceni
   				   var diff = new Date(today.getTime()-date.getTime());
   				   var minutes = diff.getMinutes();
   				   
   				   if(minutes<30){	//moze da se otkaze
   					   $scope.visits[i].cancelButton = true;
   				   } else {
   					   $scope.visits[i].cancelButton = false;
   				   }
   				   
   				   var reservationEnd = new Date($scope.visits[i].reservation.dateTime);
   				   date.addHours($scope.visits[i].reservation.length);
   			   } else {										//pocela je
   				   $scope.visits[i].cancelButton = false; //ne moze da se otkaze
   				   date.addHours($scope.visits[i].reservation.length);
   				   if(date.getTime() < today.getTime()) {	//nije se jos zavrsila
   					   $scope.visits[i].rateButton = true;
   				   } else {									//zavrsila se
   					   $scope.visits[i].rateButton = false;
   				   }
   			   } 
   		   }
   		}, function(response) {
   			alert(response.statusText);
   	    });    
	});
	
	$scope.dodajPrijatelja = function(id) {
		for (i = 0; i<$scope.friendSuggestions.length; i++){
			if($scope.friendSuggestions[i].id === id) {
				$scope.friendSuggestions[i].added = true;
			}
		}
		$http.get('/user/addFriend/'+id).then(function(response) {
			toastr.success("Zahtev poslat!");
		}, function(response) {
			alert(response.data);
		});
	}
	
	$scope.prihvatiPrijatelja = function(id) {
		for (i = 0; i<$scope.pendingRequests.length; i++){
			if($scope.pendingRequests[i].id === id) {
				$scope.pendingRequests[i].responded = true;
			}
		}
		$http.get('/user/acceptFriendRequest/'+id).then(function(response) {
			toastr.success("Zahtev prihvacen!");
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.odbijPrijatelja = function(id) {
		for (i = 0; i<$scope.pendingRequests.length; i++){
			if($scope.pendingRequests[i].id === id) {
				$scope.pendingRequests[i].responded = true;
			}
		}
		$http.get('/user/denyFriendRequest/'+id).then(function(response) {
			toastr.info("Zahtev odbijen!");
		}, function(response) {
			alert(response.statusText);
			$window.location.reload();
		});
	}
	
	$scope.removeFriend = function(id) {
		$http.get('/user/removeFriend/'+id).then(function(response) {
			var index;
			for(i=0; i<$scope.friends.length; i++){
				if($scope.friends[i].id === id) {
					index = i;
					$scope.friendSuggestions.push($scope.friends[i]);
					break;
				}
			}
			$scope.friends.splice(index, 1);
			toastr.info("Prijatelj obrisan.");
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	

	$scope.clickEdit = function() {
		if ($scope.isReadOnly == true) {
			$scope.isReadOnly = false;
		} else {
			$scope.isReadOnly = true;
			user.name = $scope.ime;
			user.surname = $scope.prezime;
			user.email = $scope.email;
			
			var emailRegex = new RegExp("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
			
			
			if(user.name == "" ){
				toastr.error("Morate uneti ime!");
			} else if(user.surname == "" ){
				toastr.error("Morate uneti prezime!");
			} else if(!emailRegex.test($scope.user.email)) {
				toastr.error("Morate uneti validan e-mail!");
			} else {
			
			$http.post('/user/updateUserInfo', user).then(function(response) {
				if(response.data == "EmailError") {
					toastr.error("Već postoji nalog sa tom email adresom.");
					$scope.email = '';
				}
				else 
					toastr.success("Nalog uspešno ažuriran.");
				}, function(response) {
					alert(response.statusText);
			});
			}
		}
	}
	
	$scope.promeniLozinku = function() {
		if($scope.staraLozinka == user.password) {
			if ($scope.novaLozinka == $scope.novaLozinka2) {
				if ($scope.novaLozinka != "") {
					user.password = $scope.novaLozinka;
					
					$http.post('/user/updatePassword', user).then(function(response) {
							if(response.data == "OK")
								toastr.success("Uspešno promenjena lozinka!");
						}, function(response) {
							alert(response.statusText);
					});
					
					$scope.staraLozinka = "";
					$scope.novaLozinka = "";
					$scope.novaLozinka2 = "";
				} else {
					toastr.error("Lozinka ne sme biti prazna!");
					$scope.staraLozinka = "";
					$scope.novaLozinka2 = "";
				}
			} else {
				toastr.error("Lozinke se ne podudaraju.");
				$scope.staraLozinka = "";
				$scope.novaLozinka = "";
				$scope.novaLozinka2 = "";
			}
		} else {
			toastr.error("Pogrešna lozinka!");
			$scope.staraLozinka = "";
			$scope.novaLozinka = "";
			$scope.novaLozinka2 = "";
			
		}
	}
	
	$scope.filterFn = function(user) {
		var check = user.name.concat(" "+user.surname);
		if(check.includes($scope.searchKeyword))
	    {
	        return true; 
	    }
		return false;
	};
	
	$scope.cancelReservation = function(reservationId) {
		$http.get("/reservation/cancel/"+reservationId).then(function(response) {
	    	 toastr.success("Rezervacija otkazana.");
	    	 var index = -1;
	    	 for(i=0; i<$scope.visits.length; i++){
	    		 if($scope.visits[i].reservation.id === reservationId) {
	    			 index = i;
	    			 break;
	    		 }
	    	 }
	         $scope.visits.splice(index,1);
		}, function(response) {
	    	alert(response.statusText);
	    });
	}
	
	$scope.reserve = function(id) {
		$location.path("/guestHome/date/"+id);
		
	}
	
	$scope.initt = function() {
		$('#datetime').datetimepicker({
			minDate: new Date(),
			format: 'DD-MM-YYYY HH:mm'
		});
	}
	
	$scope.reserveDateNext = function() {
		var datetime = $('#dateTextField').val();
		var duration = $scope.duration;
		if(datetime == ""){
			toastr.info("Unesite datum i vreme.");
		} else if ($scope.duration == "undefined"){
			toastr.info("Unesite trajanje rezervacije.");
		} else if ($scope.duration == ""){
			toastr.info("Unesite trajanje rezervacije.");
		} else if (isNaN($scope.duration)){
			toastr.error("Trajanje mora biti ceo broj sati!")
		} else if(!Number.isInteger(+duration)){
			toastr.error("Trajanje mora biti ceo broj sati!");
		} else {
			var value = datetime+","+duration;
			$http.post("/reservation/setDateTime/"+$stateParams.id, value).then(function(response) {
		    	 if(response.data == "ParseError") {
		    		 toastr.error('Proverite unete parametre.');
		    	 } else {
		    		 toastr.info(response.data);
		    		 $location.path("/guestHome/tables/"+response.data);
		    		
		    	 }
			}, function(response) {
		    	alert(response.statusText);
		    });
		}
		return $scope;
	} 
	
	$scope.drawTables = function() {
		$http.get('/reservation/getAvailableTables/'+$stateParams.id).then(function(response) {
  	   		$scope.tables = response.data;
  	   		var c=document.getElementById("myCanvas");
  	   		var ctx=c.getContext("2d");
  	   		
  	   		for (i = 0; i < $scope.tables.length; i++) { 
  	   			var t = $scope.tables[i];
  	   			ctx.fillStyle = "black"; 
  	   		
  	   			xpos = (t.x-0.7)*40;
  	   			ypos = (t.y-0.7)*30;
  	   		
	   			var font = "bold " + 18 +"px serif";
	   			ctx.font = font;
	   			ctx.textBaseline = "top";
	   			ctx.fillText(t.tableNumber, xpos+10 ,ypos);
	   			
	   			ctx.beginPath();
  	   			if((''+t.isAvailable) === 'true'){
  	   				ctx.strokeStyle = "black";
  	   			}
  	   			else 
  	   				ctx.strokeStyle = "red";
  	   			
  	   			ctx.rect(xpos, ypos, 30, 20);
  	   			ctx.stroke();
  	   			ctx.closePath();
  	   			
  	   		}
  		}, function(response) {
  			alert(response.statusText);
  		});
		
		$scope.reserveTablesNext = function() {
			var tableNumbers = '';
		    angular.forEach($scope.tables, function(table) {
		        if (table.selected) {
		        	tableNumbers += table.tableNumber + ',';
		        }
		    });
		    angular.forEach($scope.friends, function(friend) {
		        friend.invited = false;
		    });
		    if(tableNumbers === '') {
		    	toastr.info("Morate rezervisati barem jedan sto.");
		    } else {
		    	$http.post("/reservation/setTables/"+$stateParams.id, tableNumbers).then(function(response) {
		    		$location.path('/guestHome/confirm/'+$stateParams.id);
		    	   
		    	}, function(response) {
		    		toastr.error("Odabrani stolovi su se u medjuvremenu rezervisali!");
		    	});
		    }
		}	
	}
	
	$scope.initConfirm = function(){
		$http.get("/reservation/getReservation/"+$stateParams.reservationId).then(function(response) {
	    	 $scope.confirmRes = response.data;
	    	 var date = new Date($scope.confirmRes.dateTime);
	    	 $scope.confirmRes.dateTime = date.toLocaleString();
	    	 $scope.confirmRes.tableString = $scope.confirmRes.tables[0].tableNumber;
	    	 
	    	 for(i=1; i<$scope.confirmRes.tables.length; i++){
	    		 $scope.confirmRes.tableString +=", "+$scope.confirmRes.tables[i].tableNumber;
	    		 
	    	 }
		}, function(response) {
	    	alert(response.statusText);
	    });
		
		$http.get("/reservation/getRestaurant/"+$stateParams.reservationId).then(function(response) {
	    	 $scope.confirmResRestaurant = response.data;
		}, function(response) {
	    	alert(response.statusText);
	    });
	}
	
	$scope.inviteFriend = function(friend){
		for(i=0; i<$scope.friends.length; i++){
			if(friend === $scope.friends[i].id){
				$scope.friends[i].invited = true;
				break;
			}
		}
		$http.get("/reservation/inviteFriend/"+$stateParams.reservationId+"/"+friend).then(function(response) {
	    	 toastr.success("Pozivnica je poslata.");
		}, function(response) {
	    	alert(response.statusText);
	    });
	}
	
	

	$scope.nextToOrder = function() {
		$location.path('guestHome/order/'+$stateParams.reservationId);
	}
	
	$scope.initReservationOrder = function(){
		$http.get("/reservation/getRestaurant/"+$stateParams.reservationId).then(function(response) {
	    	 $scope.orderRestaurant = response.data;
	    	 
	 		 for(i=0; i< $scope.orderRestaurant.menu.length; i++){
	 			$scope.orderRestaurant.menu[i].ordered = false;
	 		 }
		}, function(response) {
	    	alert(response.statusText);
	    });
		$scope.order = {};
		$scope.order.items = [];
		$scope.temporder = {};
		$scope.temporder.items = [];
	}
	
	$scope.orderItem = function(itemId, isDrink){
		if(isDrink === 'true'){
			for(i=0; i<$scope.orderRestaurant.drinks.length; i++){
				if($scope.orderRestaurant.drinks[i].id === itemId) {
					$scope.orderRestaurant.drinks[i].ordered = true;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.orderRestaurant.menu.length; i++){
				if($scope.orderRestaurant.menu[i].id === itemId) {
					$scope.orderRestaurant.menu[i].ordered = true;
					break;
				}
			}
		}
		$scope.temporder.items.push(itemId);
		toastr.success('Stavka dodata na porudzbinu!');
	}
	
	$scope.removeItem = function(itemId, isDrink){
		var index = $scope.temporder.items.indexOf(itemId);
		if (index > -1) {
			$scope.temporder.items.splice(index, 1);
		}
		if(isDrink === 'true'){
			for(i=0; i<$scope.orderRestaurant.drinks.length; i++){
				if($scope.orderRestaurant.drinks[i].id === itemId) {
					$scope.orderRestaurant.drinks[i].ordered = false;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.orderRestaurant.menu.length; i++){
				if($scope.orderRestaurant.menu[i].id === itemId) {
					$scope.orderRestaurant.menu[i].ordered = false;
					break;
				}
			}
		}
	}
	
	$scope.orderModalClick = function() {
		if($scope.temporder.items.length === 0) {
			$location.path('/guestHome');
			toastr.success('Rezervacija potvrdjena!');
		}
	}
	
	$scope.submitOrderYes = function() {
		var postItems = '';
		for(i=0; i<$scope.temporder.items.length; i++){
			postItems += $scope.temporder.items[i] + ",";
		}
		var postData = {items : postItems, flag : 'true'}
		$http.post('/order/createFromReservation/'+$stateParams.reservationId, postData).then(function(response) {
			toastr.success("Porudzbina primljena. Vidimo se.");
			$location.path('/guestHome');
			}, function(response) {
				alert(response.statusText);
		});
	}
	
	$scope.submitOrderNo = function() {
		var postItems = '';
		for(i=0; i<$scope.temporder.items.length; i++){
			postItems += $scope.temporder.items[i] + ",";
		}
		var postData = {items : postItems, flag : 'false'}
		$http.post('/order/createFromReservation/'+$stateParams.reservationId, postData).then(function(response) {
			toastr.success("Vidimo se.");
			$location.path('/guestHome');
			}, function(response) {
				alert(response.statusText);
		});
	}
	
	
	$scope.initRestaurants = function() {
		$scope.initMap();
		$scope.userLocation = {};
		for(i=0; i<$scope.restaurants.length; i++){
			   $scope.restaurants[i].distance = 'a';
			   
		   }
		$http.get('/user/getLoggedInUser').then(function(response) {
			   $scope.userLocation = response.data;
			   
			}, function(response) {
				alert(response.statusText);
		});
	}
	
	$scope.calculateDistances = function(){
		
		for(i=0; i<$scope.restaurants.length; i++){
			   var lng = $scope.restaurants[i].longitude;
			   var lat = $scope.restaurants[i].latitude;
		   
			   $scope.restaurants[i].distance = getDistanceFromLatLonInKm($scope.userLocation.latitude,$scope.userLocation.longitude, lat, lng);
			   
		 }
	}
	
	$scope.initMap = function(){
  	  var uluru = {lat: -25.363, lng: 131.044};
  	  var map = new google.maps.Map(document.getElementById('map'), {
  	    zoom: 12,
  	    center: uluru
  	  });
      var infoWindow = new google.maps.InfoWindow({map: map});
     if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          };

         var postData = {latitude: pos.lat, longitude : pos.lng};
          $http.post('/user/setLatitudeAndLongitude', postData).then(function(response) {
        	  
			}, function(response) {
				alert(response.statusText);
		  });
          
          infoWindow.setPosition(pos);
          infoWindow.setContent('Vasa lokacija');
          map.setCenter(pos);
          
        }, function() {
          handleLocationError(true, infoWindow, map.getCenter());
        });
      } else {
        handleLocationError(false, infoWindow, map.getCenter());
      }
      
      var markers = [];
      for(i=0; i<$scope.restaurants.length; i++){
    	  markers.push(new google.maps.Marker({
    	  	    position: {lat: $scope.restaurants[i].latitude, lng : $scope.restaurants[i].longitude},
    	  	    map: map,
    	  	    animation: google.maps.Animation.DROP,
    	  	    label: $scope.restaurants[i].name
    	  	  }));
    	  
      } 
    }
	
	function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	   infoWindow.setPosition(pos);
	   infoWindow.setContent(browserHasGeolocation ?
	                        'Error: The Geolocation service failed.' :
	                        'Error: Your browser doesn\'t support geolocation.');
	}
	
	function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
		  var R = 6371; // Radius of the earth in km
		  var dLat = deg2rad(lat2-lat1);  // deg2rad below
		  var dLon = deg2rad(lon2-lon1); 
		  var a = 
		    Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
		    Math.sin(dLon/2) * Math.sin(dLon/2)
		    ; 
		  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		  var d = R * c * 1000; // Distance in km
		  return d.toFixed();
	}

	function deg2rad(deg) {
	     return deg * (Math.PI/180)
	}
	
	$scope.clickStar1 = function() {
		$scope.star1 = true;
		$scope.star2 = false;
		$scope.star3 = false;
		$scope.star4 = false;
		$scope.star5 = false;
	}
	
	$scope.clickStar2 = function() {
		$scope.star1 = true;
		$scope.star2 = true;
		$scope.star3 = false;
		$scope.star4 = false;
		$scope.star5 = false;
	}
	$scope.clickStar3 = function() {
		$scope.star1 = true;
		$scope.star2 = true;
		$scope.star3 = true;
		$scope.star4 = false;
		$scope.star5 = false;
	}
	$scope.clickStar4 = function() {
		$scope.star1 = true;
		$scope.star2 = true;
		$scope.star3 = true;
		$scope.star4 = true;
		$scope.star5 = false;
	}
	$scope.clickStar5 = function() {
		$scope.star1 = true;
		$scope.star2 = true;
		$scope.star3 = true;
		$scope.star4 = true;
		$scope.star5 = true;
	}
	
	$scope.rateRestaurant = function() {
		var rating = 0;
		if($scope.star5 === true){
			rating = 5;
		} else if ($scope.star4 === true) {
			rating = 4;
		} else if ($scope.star3 === true) {
			rating = 3;
		} else if ($scope.star2 === true) {
			rating = 2;
		} else if ($scope.star1 === true) {
			rating = 1;
		}
		 $http.post('/visit/rateRestaurant/'+$scope.ratingForVisit, rating).then(function(response) {
       	  	for(i = 0; i<$scope.visits.length; i++) {
       	  		if($scope.visits[i].id === $scope.ratingForVisit)
       	  			$scope.visits[i].ratedRestaurant = true;
       	  	}
			}, function(response) {
				alert(response.statusText);
		 });
	}
	
	$scope.rateMeal = function() {
		var rating = 0;
		if($scope.star5 === true){
			rating = 5;
		} else if ($scope.star4 === true) {
			rating = 4;
		} else if ($scope.star3 === true) {
			rating = 3;
		} else if ($scope.star2 === true) {
			rating = 2;
		} else if ($scope.star1 === true) {
			rating = 1;
		}
		$http.post('/visit/rateMeal/'+$scope.ratingForVisit, rating).then(function(response) {
	       	  	for(i = 0; i<$scope.visits.length; i++) {
	       	  		if($scope.visits[i].id === $scope.ratingForVisit)
	       	  			$scope.visits[i].ratedMeal = true;
	       	  	}
				}, function(response) {
					alert(response.statusText);
		});	
	}
	
	$scope.rateService = function() {
		var rating = 0;
		if($scope.star5 === true){
			rating = 5;
		} else if ($scope.star4 === true) {
			rating = 4;
		} else if ($scope.star3 === true) {
			rating = 3;
		} else if ($scope.star2 === true) {
			rating = 2;
		} else if ($scope.star1 === true) {
			rating = 1;
		}
		
		$http.post('/visit/rateService/'+$scope.ratingForVisit, rating).then(function(response) {
	   	  	for(i = 0; i<$scope.visits.length; i++) {
	   	  		if($scope.visits[i].id === $scope.ratingForVisit)
	   	  			$scope.visits[i].ratedService = true;
	   	  	}
		}, function(response) {
				alert(response.statusText);
	});	
		
	}
	
	$scope.setRatingForVisit = function(visitId) {
		$scope.ratingForVisit = visitId;
	}
	
	$scope.clickLogOut = function() {
		$http.get('/user/logout').then(function(response) {
	   	  	$location.path("login");
		}, function(response) {
				alert(response.statusText);
		});	
		
	}
}]);
