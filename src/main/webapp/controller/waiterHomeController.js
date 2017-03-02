var waiterHomeModule = angular.module('waiterHome.controller', []);
 

waiterHomeModule.controller('waiterHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {
	
	$scope.newOrderMenuItems = [];
	$scope.waiter = {};
	
	$http.get('/waiter/getLoggedIn').then(function(response) {
	   	   $scope.waiter = response.data;
	   	   $scope.waiter.birthDate = new Date(response.data.birthDate).toLocaleString();
		 }, function(response) {
	   		alert(response.statusText);
		 });
	
	 $http.get('/waiter/getReon').then(function(response) {
   	   $scope.reon = response.data;
	 }, function(response) {
   		alert(response.statusText);
	 });
	 
	 $http.get('/restaurant/getAllMenuItems').then(function(response) {
	   	   $scope.allMenuItems = response.data;
		 }, function(response) {
	   		alert(response.statusText);
		 });
	
	 $http.get('/waiter/getUnfinishedOrders').then(function(response) {
	   	   $scope.unfinishedOrders = response.data;
	   	   for(i=0; i<$scope.unfinishedOrders.length; i++) {
	   		   var o = $scope.unfinishedOrders[i];
	   		   var itemsString = '';
	   		   for(j=0; j<o.menuItems.length; j++) {
	   			   itemsString += o.menuItems[j].name+", ";
	   		   }
	   		   var tablesString = '';
	   		   if(o.tableNumber !== 0) {
	   			   tablesString = o.tableNumber; 
	   		   } else {
	   			   for(j=0; j<o.reservation.reservation.tables.length; j++) {
		   			   tablesString +=o.reservation.reservation.tables[j].tableNumber+", ";
		   		   }
	   		   }
	   		   $scope.unfinishedOrders[i].itemsString = itemsString;
	   		   $scope.unfinishedOrders[i].tablesString = tablesString;
	   		   
	   	   }
     }, function(response) {
	   		alert(response.statusText);
	 });
		    
	 
     $http.get('/shift/getAllShifts').then(function(response) {
   	   $scope.shifts = response.data;
   	   for(i = 0; i< $scope.shifts.length; i++) {
   		   var d = new Date($scope.shifts[i].date);
   		   var d2 = d.toLocaleString();
   		   var array = d2.split(',');
   		   $scope.shifts[i].date = array[0];
   	   }
   	 }, function(response) {
   		alert(response.statusText);
   	 });
     
       
     $scope.clickIzmeni = function() {
		  if ($scope.waiter.name == ""){
			  toastr.error("Ime ne moze ostati prazno!");
		  } else if ($scope.waiter.surname == "") {
			  toastr.error("Prezime ne moze ostati prazno!");
		  } else if ($scope.waiter.birthDate == "") {
			  toastr.error("Morate uneti datum rodjenja!");
		  } else if ($scope.waiter.email == ""){
			  toastr.error("Morate uneti e-mail adresu!");
		  } else if ($scope.waiter.shoeSize == "") {
			  toastr.error("Morate uneti broj obuce!");
		  } else if ($scope.waiter.clothesSize == "") {
			  toastr.error("Morate uneti konfekcijski broj!");
		  } else {
		  $http({
		        method : "POST",
		        url : "/waiter/updateProfile",
		        data : $scope.waiter,
		    }).then(function mySuccess(response) {
		    	if(response.data == "EmailError"){
		    		toastr.error("E-mail adresa je vec zauzeta.");
		    	} else
		    		toastr.success("Uspesno azuriran profil!");
		    }, function myError(response) {
		    	alert(response.statusText);
		    });
		 };
	 }
     
     
     
     $scope.drawTables = function() {
 		$http.get('/employee/getTables').then(function(response) {
 			
   	   		$scope.tables = response.data;
   	   		var c=document.getElementById("myCanvas");
   	   		var ctx=c.getContext("2d");
   	   		toastr.success($scope.reon);
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
 	   			
 	   			ctx.strokeStyle = "black";
 	   			for(j = 0; j<$scope.reon.length; j++){
 	   				if($scope.reon[j].tableNumber === t.tableNumber) {
 	   					ctx.strokeStyle = "blue";
 	   					break;
 	   				}
 	   			}
 	   			
 	   			ctx.rect(xpos, ypos, 30, 20);
   	   			ctx.stroke();
   	   			ctx.closePath();
   	   			
   	   		}
   		}, function(response) {
   			alert(response.statusText);
   		});
     }
	  
     $scope.clickLogOut = function() {
 		$http.get('/user/logout').then(function(response) {
 	   	  	$location.path("login");
 		}, function(response) {
 				alert(response.statusText);
 		});	
 		
 	}
     
     
     $scope.createBill = function(orderId) {
    	 $http.get('/order/createBill/'+orderId).then(function(response) {
    		 for(i=0; i<$scope.unfinishedOrders.length; i++){
    	 			if($scope.unfinishedOrders[i].id === orderId) {
    	 				$scope.unfinishedOrders[i].finished = true;
    	 				$scope.unfinishedOrders[i].bill = response.data;
    	 			}
    	 	  }
  		}, function(response) {
  				alert(response.statusText);
  		});	
    }
     
     
     $scope.filterFn = function(menuitem) {
 		var check = menuitem.name;
 		if(check.includes($scope.searchKeyword))
 	    {
 	        return true; 
 	    }
 		return false;
 	};
 	
     $scope.clickNewOrder = function(){
    	 $location.path('/waiter/orderNew');
     }
     
     $scope.addToOrder = function(menuitem){
    	 toastr.info(menuitem.id);
    	 for(i=0; i<$scope.allMenuItems.length; i++) {
    		 if($scope.allMenuItems[i].id === menuitem.id){
    			 $scope.newOrderMenuItems.push($scope.allMenuItems[i]);
    			 menuitem.added = true;
    			 break;
    		 }
    	 }
    	 
     }
     
     $scope.createNewOrder = function(){
    	 var tableString = '';
    	 $location.path('/waiter/orderNew');
     }
     
     $scope.createNewOrderFinish = function(){
    	 var tableString = '';
    	 for(i=0; i<$scope.reon.length; i++) {
    		 if($scope.reon[i].selected === true)
    			 tableString += $scope.reon[i].tableNumber;
    	 }
    	 if(tableString === '')
    		 toastr.error('Morate uneti sto.');
    	 else if(tableString.length > 1)
    		 toastr.error('Morate uneti tacno jedan sto.');
    	 else {
    		 var itemsString = '';
    		 for(i=0; i<$scope.newOrderMenuItems.length; i++){
    			 itemsString += $scope.newOrderMenuItems[i].id+',';
    		 }
    		 if(itemsString == '') {
    			 toastr.error('Morate uneti barem jednu stavku.');
    		 } else {
    			 var postData = {};
    			 postData.items = itemsString;
    			 postData.tables = tableString;
    			 $http.post('/order/new', postData).then(function(response) {
    				    toastr.success('Uspesno napravljena porudzbina.');
    				    $scope.unfinishedOrders.push(response.data);
    		 	   	  	$location.path("/waiter");
    		 	 }, function(response) {
    		 			alert(response.statusText);
    		 	 });	
    		}
    		 
    	 }
     }
     
     $scope.initDateTimePicker = function() {
 		$('#datetime').datetimepicker({
 			maxDate: new Date(),
 			format: 'DD-MM-YYYY HH:mm'
 		});
 	}
	
}]);

	
