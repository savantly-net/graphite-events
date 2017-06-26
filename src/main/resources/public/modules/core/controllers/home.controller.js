'use strict';


angular.module('core').controller('HomeController', ['$scope', '$http', '$mdDialog', 
	function($scope, $http, $mdDialog) {	
	
		$scope.eventNames = [];
		$scope.graphiteConfig = graphiteConfig;
		
		$scope.getRecentImageUrl = function(){
			return 'http://' + graphiteConfig.host + ':' + graphiteConfig.webPort + 
				'/render/?width=800&height=400&from=-24h&target=' + $scope.targetParam;
		}
		
		$http.get('rest/health/event/names').then(function(response){
			$scope.eventNames = response.data;
			var eventNamesMap = [];
			var eventMaxSegments = 0;
			response.data.map(function(eventName){
				var segments = eventName.split('.');
				eventMaxSegments = Math.max(eventMaxSegments, segments.length);
				eventNamesMap.push(segments);
			});
			
			var targets = [];
			for (var i = 0; i < eventMaxSegments; i++) {
				targets[i] = eventNamesMap.map(function(eventNameSegments){
					return eventNameSegments[i];
				});
			}
			var targetParam = '';
			for (var i = 0; i < targets.length; i++) {
				targetParam +='{' + targets[i].join(',') + '}';
				if (i < targets.length - 1) targetParam += '.';
			}
			$scope.targetParam = 'lineWidth(' + graphiteConfig.prefix + targetParam +',2)';
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