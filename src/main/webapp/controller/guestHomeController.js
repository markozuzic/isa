var guestHomeModule = angular.module('guestHome.controller', []);
 

guestHomeModule.controller('guestHomeController', ['$scope','$location', '$http', '$stateParams',
  	function ($scope, $location, $http, $stateParams) {

	$scope.duration="";
	$scope.rest = "";
	var user = "";
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
 		}, function(response) {
 			alert(response.statusText);
 	    });
        
        
        
        $http.get('/user/getVisits').then(function(response) {
   		   $scope.visits = response.data;
   		}, function(response) {
   			alert(response.statusText);
   	    });
        
        $http.get('/restaurant/getAllRestaurants').then(function(response) {
    	   $scope.restaurants = response.data;
    	}, function(response) {
    		alert(response.statusText);
    	});
        
	});
	
	$scope.dodajPrijatelja = function(id) {
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
		$http.get('/user/denyFriendRequest/'+id).then(function(response) {
			toastr.info("Zahtev odbijen!");
		}, function(response) {
			alert(response.statusText);
			$window.location.reload();
		});
	}
	
	$scope.removeFriend = function(id) {
		$http.get('/user/removeFriend/'+id).then(function(response) {
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
			
			$http.post('/user/updateUserInfo', user).then(function(response) {
				if(response == "EmailError")
					toastr.error("Već postoji nalog sa tom email adresom.");
				else 
					toastr.success("Nalog uspešno ažuriran.");
				}, function(response) {
					alert(response.statusText);
			});
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
		    $http.post("/reservation/setTables/"+$stateParams.id, tableNumbers).then(function(response) {
		    	   $location.path('/guestHome/confirm/'+$stateParams.id);
		    	   
		    	}, function(response) {
		    		alert(response.statusText);
		    	});
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
	    	 toastr.success($scope.confirmResRestaurant.name);
				for(i=0; i<$scope.confirmResRestaurant.menu.length; i++){
					$scope.confirmResRestaurant.menu[i].ordered = false;
				}
				for(i=0; i<$scope.confirmResRestaurant.drinks.length; i++){
					$scope.confirmResRestaurant.drinks[i].ordered = false;
				}
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
	
	$scope.orderItem = function(menuitem, isDrink){
		if(isDrink === 'true'){
			for(i=0; i<$scope.confirmResRestaurant.drinks.length; i++){
				if($scope.confirmResRestaurant.drinks[i].id === itemId) {
					$scope.confirmResRestaurant.drinks[i].ordered = true;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.confirmResRestaurant.menu.length; i++){
				if($scope.confirmResRestaurant.menu[i].id === itemId) {
					$scope.confirmResRestaurant.menu[i].ordered = true;
					break;
				}
			}
		}
		
		$scope.order.items.push(itemId);
		toastr.success('Stavka dodata na porudzbinu!');
	}
	
	$scope.removeItem = function(itemId, isDrink){
		var index = $scope.order.items.indexOf(itemId);
		if (index > -1) {
			$scope.order.items.splice(index, 1);
		}
		toastr.success($scope.order.items);
		if(isDrink === 'true'){
			for(i=0; i<$scope.confirmResRestaurant.drinks.length; i++){
				if($scope.confirmResRestaurant.drinks[i].id === itemId) {
					$scope.confirmResRestaurant.drinks[i].ordered = true;
					break;
				}
			}
		} else {
			for(i=0; i<$scope.confirmResRestaurant.menu.length; i++){
				if($scope.confirmResRestaurant.menu[i].id === itemId) {
					$scope.confirmResRestaurant.menu[i].ordered = true;
					break;
				}
			}
		}
	}
}]);


