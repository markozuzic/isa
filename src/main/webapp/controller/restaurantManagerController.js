var restaurantManagerModule = angular.module('restaurantManager.controller', []);

restaurantManagerModule.controller('restaurantManagerController', ['$scope', '$location', '$http','$stateParams', 
	function ($scope, $location, $http, $stateParams) {
		
	angular.element(document).ready(function () {
		$scope.drinkItem = {};
		$scope.menuItem = {};
		$scope.newEmployee = {};
		$scope.newTable = {};
		$scope.updateTable = {};
		$scope.newSupplier = {};
		$scope.newItem = {};
		$scope.items = [];
		$scope.clickedMenuItem = {};
		$scope.clickedWaiter = {};
		$scope.restaurantEarning = '';
		$scope.waiterEarning = '';
		$scope.shift = {};


		$scope.selectedEmployeeType = {};
		$scope.isReadOnly = true;
		
		$http.get('/manager/getLoggedIn').then(function(response) {
			   manager = response.data;
			   $scope.manager = manager;
			}, function(response) {
				alert(response.statusText);
		   });

		$http.get('/restaurant/getRestaurant').then(function(response) {
				$scope.restaurant = response.data;
			}, function(response) {
				alert(response.statusText);
			});
				
		$http.get('/waiter/getWaiters').then(function(response) {
			$scope.waiters = response.data;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/chef/getChefs').then(function(response) {
			$scope.chefs = response.data;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/bartender/getBartenders').then(function(response) {
			$scope.bartenders = response.data;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/table/getTables').then(function(response) {
		   $scope.tables = response.data;
		}, function(response) {
			alert(response.statusText);
	    });
		
		$http.get('/supplier/getSuppliers').then(function(response) {
		   $scope.suppliers = response.data;
		}, function(response) {
			alert(response.statusText);
	    });
		
		$http.get('/demand/getDemands').then(function(response) {
			$scope.demands = response.data;
		}, function(response) {
			alert(response.statusText);
		});
		
		$http.get('/manager/getMyDemands').then(function(response) {
			$scope.demands = response.data;
			for(i=0; i<$scope.demands.length; i++) {
				var d = $scope.demands[i];
				var itemsString = '';
				for(j=0; j<d.listOfItems.length; j++) {
					itemsString += d.listOfItems[j].name+", ";
				}
		   		   
			$scope.demands[i].itemsString = itemsString;
		   		   
		   	}
	     }, function(response) {
		   		alert(response.statusText);
		 });
		
		$http.get('/offer/getOfferForDemands').then(function(response) {
		   	   $scope.offers = response.data;
	        }, function(response) {
		   		alert(response.statusText);
		});
		
	})
	

	
	$scope.createMenuItem = function() {
		$http.post('/menuItem/createMenuItem', $scope.menuItem).then(function(response) {
				toastr.success("Uspesno dodat");
				$scope.restaurant.menu.push(response.data);
				$scope.menuItem = {};
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.createDrink = function() {

		$http.post('/menuItem/createDrinkItem', $scope.drinkItem).then(function(response) {
				toastr.success("Uspesno dodat");
				$scope.restaurant.drinks.push(response.data);
				$scope.drinkItem = {};
			
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.removeDrinkItem = function(mi) {
		$http.get('/menuItem/removeDrinkItem/' + mi).then(function(response) {
			var index = -1;
			for (i = 0; i < $scope.restaurant.menu.length; i++) {
				if (mi === $scope.restaurant.menu[i].id) {
					index = i;
					break;
				}
			}
			$scope.restaurant.drinks.splice(index, 1);
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.removeMenuItem = function(mi) {
		$http.get('/menuItem/removeMenuItem/' + mi).then(function(response) {
			var index = -1;
			for (i = 0; i < $scope.restaurant.menu.length; i++) {
				if (mi === $scope.restaurant.menu[i].id) {
					index = i;
					break;
				}
			}
			$scope.restaurant.menu.splice(index, 1);
		}, function(response) {
			alert(response.statusText);
		});
	}
	
	$scope.createEmployee = function() {
		var type = document.getElementById("employeeType").value;	//iz combo boxa
		
		//type = waiter || bartender || chef 
		$http.post('/'+type+'/create', $scope.newEmployee).then(function(response) {
			toastr.success("Novi radnik uspesno dodat!");
			var retVal = response.data;
			if(type === 'chef') {
				$scope.chefs.push(retVal);
			} 
			else if (type === 'bartender') {
				$scope.bartenders.push(retVal);
			} 
			else if (type === 'waiter') {
				$scope.waiters.push(retVal);
			}
		}, function(response) {
			alert(response.statusText);
		});		
	}
	
	$scope.createNewTable = function() {
		$http.post('/table/create', $scope.newTable).then(function(response) {
				if(response.data === "OK") {
					toastr.success("Sto dodat!");
					$scope.tables.push($scope.newTable);
				} 
				else if (response.data === "NumberError") {
					toastr.error("Sto sa tim brojem vec postoji!");
				}
				$scope.newTable = {};

			}, function(response) {
				alert(response.statusText);
		    });
	}
	
	$scope.deleteTable = function(id) {
		$http.get('/table/delete/' + id).then(function(response) {
			if(response.data === "OK") {
				var index = -1;
				for(i = 0; i < $scope.tables.length; i++) {
					if($scope.tables[i].tableNumber === id) {
						index = i;
						break;
					}
				}
				$scope.tables.splice(index, 1);
				
			}
			else {
				toastr.error("Sto je rezervisan, pa se ne moze obrisati!");
			}
				
			}, function(response) {
				alert(response.statusText);
		    });
	}
	
	$scope.clickUpdateTable = function(t) {
		$scope.updateTable = t;	//da bi se pojavio u modalnom
	}
	
	$scope.updateTable = function() {
		$http.post('/table/update', $scope.updateTable).then(function(response) {
			toastr.success("Uspesno promenjen sto!");
			$scope.updateTable = {};
		}, function(response) {
			alert(response.statusText);
	    });
		
	}
	
	$scope.createNewSupplier = function() {

			$http.post('/supplier/create', $scope.newSupplier).then(function(response) {
				if (response.data.id === 0) {
					toastr.error("Email vec zauzet");
				}
				else {
					$scope.suppliers.push(response.data);
					$scope.newSupplier = {};
				}	

			}, function(response) {
				alert(response.statusText);
		    });	
	}
	
	$scope.clickEditRestaurant = function() {
		if ($scope.isReadOnly == true) {
			$scope.isReadOnly = false;
		} 
		else {
			$scope.isReadOnly = true;
			
			$http.post('/restaurant/updateRestaurant', $scope.restaurant).then(function(response) {
					toastr.success("Profil restorana uspešno ažuriran.");
				}, function(response) {
					alert(response.statusText);
			});
		}
	}
	
	$scope.initDateTimePickerStart = function() {
		$('#datetimeStart').datetimepicker({
			minDate: new Date(),
			format: 'DD-MM-YYYY HH:mm'
		});
	}
	
	$scope.initDateTimePickerEnd = function() {
		$('#datetimeEnd').datetimepicker({
			minDate: new Date(),
			format: 'DD-MM-YYYY HH:mm'
		});
	}
	
	$scope.createNewItem = function() {
		$http.post("/demandItem/create", $scope.newItem).then(function(response) {
			var i = {};
			i.name = response.data.name;
			$scope.items.push(i);
			$scope.newItem = {};
		}, function(response) {
			alert(response.statusText);
		});	
	}
	
	$scope.createNewDemand = function() {
		var datetimeStart = $('#dateTextFieldStart').val();
		var datetimeEnd = $('#dateTextFieldEnd').val();
		
		var itemIds = '';
		for(i=0; i<$scope.items.length; i++){
			itemIds += $scope.items[i].name;
			if (i < $scope.items.length - 1) {
				itemIds += ",";
			}
		}

		if (itemIds === '') {
			toastr.error("Morate uneti barem jednu namirnicu.");
		} 
		else if (datetimeStart === '' || datetimeEnd === '') {
			toastr.error("Morate uneti datum početka i kraja.");
		} 
		else {
			$scope.demand = {};
			$scope.demand.beginDate = datetimeStart;
			$scope.demand.endDate = datetimeEnd;

			$http.post("/demand/createDemand/"+itemIds, $scope.demand).then(function(response) {	
		    	 if(response.data == "ParseError") {	
		    		 toastr.error('Proverite unete parametre.');
		    	 } else if (response.data === "OK"){
		    		 toastr.success("Ponuda uspesno napravljena");
		    	 }
			}, function(response) {
		    	alert(response.statusText);
		    });	
		}
	}
	
	$scope.filterFn = function(mi) {
		var check = mi.name;
		if(check.includes($scope.searchKeyword))
	    {
	        return true; 
	    }
		return false;
	};
	
	
	$scope.filterFnEmployee = function(waiter) {
		var check = waiter.name.concat(" "+waiter.surname);
		if(check.includes($scope.searchKeywordWaiter))
	    {
	        return true; 
	    }
		return false;
	};
	
	$scope.filterFnWaiterEarning = function(waiter) {
		var check = waiter.name.concat(" "+waiter.surname);
		if(check.includes($scope.searchKeywordWaiterEarning))
	    {
	        return true; 
	    }
		return false;
	};
	
	$scope.clickMealReport = function(menuitem){
		$scope.clickedMenuItem = menuitem;
	}
	
	$scope.clickWaiterReport = function(waiter){
		$scope.clickedWaiter = waiter;
		
	}
	
	$scope.initDateTimePickerStart = function() {
		$('#datetimeStart').datetimepicker({
			format: 'DD-MM-YYYY HH:mm'
		});
	}
	
	$scope.initDateTimePickerEnd = function() {
		$('#datetimeEnd').datetimepicker({
			format: 'DD-MM-YYYY HH:mm'
		});
	}
	
	$scope.clickEarningReport = function() {
		var datetimeStart = $('#dateTextFieldStart').val();
		var datetimeEnd = $('#dateTextFieldEnd').val();
		
		if(datetimeStart === "") {
			toastr.error("Morate uneti početni datum.");
		} else if (datetimeStart === "") {
			toastr.error("Morate uneti krajnji datum.");
		} else {

			if(datetimeStart < datetimeEnd) {
				
				var demandData = {};
				demandData.dateStart = datetimeStart;
				demandData.dateEnd = datetimeEnd; 

				$http.post("/order/generateReport", demandData).then(function(response) {	
			    	 if(response.data == "ParseError") {	
			    		 toastr.error('Proverite unete parametre.');
			    	 } 
			    	 else {
			    		 $scope.restaurantEarning = response.data;
			    	 }
				}, function(response) {
			    	alert(response.statusText);
			    });
				
			} else {
				toastr.error("Datum početka mora biti pre datuma kraja.");
			}
		}
	}
	
	$scope.clickWaiterEarningReport = function(id){
		$http.get("/order/generateReportForWaiter/"+id).then(function(response) {
	    	$scope.waiterEarning = response.data;
	    	 
		}, function(response) {
	    	alert(response.statusText);
	    });
			
	}
	
	$scope.initDateTimePicker = function() {
		$('#datetime').datetimepicker({
			minDate: new Date(),
			format: 'DD-MM-YYYY',
			//inline: true,
			keepOpen : true
		});
	}
	
	$scope.setEmployee = function(waiter) {
		toastr.info("Odabran " + $scope.selectedEmployeeType + " "  + waiter.name);
		$scope.shift.employeeId = waiter.id;
	}

	$scope.createShift = function() {
		var datetime = $('#dateTextField').val();
		$scope.shift.date = datetime;
		$scope.shift.shiftType = document.getElementById("shiftType").value;
		
		 if ($scope.selectedEmployeeType === "waiter") {
			var tableNumbers = '';
			angular.forEach($scope.tables, function(table) {
				if (table.selected) {
					tableNumbers += table.tableNumber + ',';
				}
			});
			if (tableNumbers === '') {
				toastr.error("Morate uneti barem jedan sto.");
			} 
			else {
				$http.post("/shift/createWaiterShift/" + tableNumbers, $scope.shift).then(function(response) {
					if (response.data === "OK") {
						toastr.success('Uspešno napravljena smena.');
					}
					else if (response.data === "NoIdError") {
						toastr.error("Morate da oznacite radnika!");
					}
					else if (response.data === "IdError") {
						toastr.error('Smena za datog konobara je vec odredjena');
					}
				}, function(response) {
					alert(response.statusText);
				});
			}		 
		}
		else if ($scope.selectedEmployeeType === "bartender")  {
			$scope.shift.employeeType = $scope.selectedEmployeeType;
			$http.post("/shift/createBartenderShift/", $scope.shift).then(function(response) {
				if (response.data === "OK") {
					toastr.success('Uspesno napravljena smena.');
				}
				else if (response.data === "NoIdError") {
					toastr.error("Morate da oznacite radnika!");
				}
				else if (response.data === "IdError") {
					toastr.error('Smena za datog radnika je vec odredjena');
				}
			}, function(response) {
				alert(response.statusText);
			});
		}
		else if ($scope.selectedEmployeeType === "chef") {
			$scope.shift.employeeType = $scope.selectedEmployeeType;
			$http.post("/shift/createChefShift/", $scope.shift).then(function(response) {
				if (response.data === "OK") {
					toastr.success('Uspesno napravljena smena.');
				}
				else if (response.data === "NoIdError") {
					toastr.error("Morate da oznacite radnika!");
				}
				else if (response.data === "IdError") {
					toastr.error('Smena za datog radnika je vec odredjena');
				}
			}, function(response) {
				alert(response.statusText);
			});
		}
		else {
			toastr.error("Morate da oznacite tip radnika!");
		}
			
		$scope.shift = {};
	}
	
	$scope.approveOffer = function(o) {
		for(i = 0; i<$scope.offers.length; i++) {
   		   if($scope.offers.demandId === o.demandId) {
   			   $scope.offers[i].responded = true;
   		   }}
		
		$http.post('/offer/approveOffer', o.id).then(function(response) {
	   	  
			
		   
      		}, function(response) {
	   		alert(response.statusText);
		});
	} 
	
}]);
