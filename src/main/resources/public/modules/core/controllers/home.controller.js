'use strict';


angular.module('core').controller('HomeController', ['$scope', '$http', '$mdDialog', 
	function($scope, $http, $mdDialog) {		
		
		$http.get('rest/health/event/names').then(function(response){
			$scope.eventNames = response.data;
		});
	
		$scope.sendGraphiteEvent = function(){
			$scope.graphiteEvent.startDateTime = moment($scope.rawDateTime).format('x');
			$http.post('rest/health/event', $scope.graphiteEvent).then(function(response){
			    $mdDialog.show(
			    	      $mdDialog.alert()
			    	        .clickOutsideToClose(true)
			    	        .title('Saved Graphite Event')
			    	        .ok('Got it!')
			    	    );
			}).catch(function(response){
				$mdDialog.show(
			    	      $mdDialog.alert()
			    	        .clickOutsideToClose(true)
			    	        .title('Failed')
			    	        .textContent(response)
			    	        .ok('=(')
			    	    );
			});
		};
	}
]);