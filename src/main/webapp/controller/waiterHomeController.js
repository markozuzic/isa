var waiterHomeModule = angular.module('waiterHome.controller', []);
 

waiterHomeModule.controller('waiterHomeController', ['$scope','$location', '$http',
  	function ($scope, $location, $http) {
	
	     
     $http.get('/employee/getAllShifts').then(function(response) {
   	   $scope.shifts = response.data;
   	}, function(response) {
   		alert(response.statusText);
   	});
     
     
     $scope.clickIzmeni = function() {
		  if ($scope.ime == ""){
			  toastr.error("Ime ne moze ostati prazno!");
		  } else if ($scope.prezime == "") {
			  toastr.error("Prezime ne moze ostati prazno!");
		  } else if ($scope.lozinka == "") {
			  toastr.error("Morate uneti lozinku!");
		  } else {
		  $http({
		        method : "POST",
		        url : "/waiter/updateProfile",
		        data : $scope.waiter,
		    }).then(function mySuccess(response) {
		    	if(response.data == "IdError"){
		    		toastr.error("Ne postoji id!");
		    	} else
		    		toastr.success("Uspesna registracija!");
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

   	   			ctx.rect(xpos, ypos, 30, 20);
   	   			ctx.stroke();
   	   			ctx.closePath();
   	   			
   	   		}
   		}, function(response) {
   			alert(response.statusText);
   		});
     }
	  
     
     
     
	
}]);

	
