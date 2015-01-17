// require JS config
require.config({
	baseUrl : '/assets/libs/',
	maps : {

	},
	paths : {
		'domReady' : '/assets/libs/domReady-2.0.1',
		'jquery' : "/assets/libs/jquery-1.11.1.min",
		'angular' : '/assets/libs/angular-1.3.7.min',
		'ngRoute' : '/assets/libs/angular-route-1.3.7.min',
		'ngResource' : '/assets/libs/angular-resource-1.3.7.min',
		'uiRoute' : '/assets/libs/angular-ui-router-0.2.13.min',
	},
	shim : {
		'angular': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery'],
            // use the global 'Backbone' as the module name.
            exports: 'angular'
        },
        'ngRoute': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery', 'angular'],
            // use the global 'Backbone' as the module name.
            exports: 'ngRoute'
        },
        'ngResource': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery', 'angular'],
            // use the global 'Backbone' as the module name.
            exports: 'ngResource'
        },
        'uiRoute': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery', 'angular'],
            // use the global 'Backbone' as the module name.
            exports: 'uiRoute'
        }
	}
});

/*
require(['jquery', 'angular', 'ngRoute', 'ngResource', 'domReady'], function($, angular, ngRoute, ngResource, domReady) {

	console.log('Initialize App ' + angular);

	angular.module('myApp', ['ngRoute', 'ngResource'])
	.controller('MainCtrl', function ($scope, $route, $resource) {
		//alert($route);
	})
	.controller('BookController', function($scope, $routeParams) {
		alert(1);
	     $scope.name = "BookController";
	     $scope.params = $routeParams;
	})
	.controller('ChapterController', function($scope, $routeParams) {
	     $scope.name = "ChapterController";
	     $scope.params = $routeParams;
	})
	.config(function($routeProvider, $locationProvider) {
		
		$routeProvider
		.when('/aa', {
			templateUrl: '/aa',
			controller: 'BookController'
		})
		.when('#chapter', {
			templateUrl: 'views/chapter.html',
			controller: 'ChapterController'
		});

		// configure html5 to get links working on jsfiddle
		$locationProvider.html5Mode(true);
		//$locationProvider.html5Mode(false);
		$locationProvider.hashPrefix("#");
	});

	angular.element(document).ready(function() {
		angular.bootstrap(document, ['myApp']);
    });
		
});
*/

require(['jquery', 'angular', 'uiRoute', 'domReady'], function($, angular, uiRoute, domReady) {

	console.log('Initialize App ' + angular);

	angular.module('myApp', ['ui.router'])
	.config(function($stateProvider, $urlRouterProvider) {
	  //
	  // For any unmatched url, redirect to /state1
	  $urlRouterProvider.otherwise("/state1");
	  //
	  // Now set up the states
	  $stateProvider
	    .state('state1', {
	      url: "/state1",
	      templateUrl: "assets/app/templates/state1.html"
	    })
	    .state('state1.list', {
	      url: "/list",
	      templateUrl: "assets/app/templates/state1.list.html",
	      controller: function($scope) {
	        $scope.items = ["A", "List", "Of", "Items"];
	      }
	    })
	    .state('state2', {
	      url: "/state2",
	      templateUrl: "assets/app/templates/state2.html"
	    })
	    .state('state2.list', {
	      url: "/list",
	      templateUrl: "assets/app/templates/state2.list.html",
	      controller: function($scope) {
	        $scope.things = ["A", "Set", "Of", "Things"];
	      }
	    });
	});
	
	angular.element(document).ready(function() {
      	angular.bootstrap(document, ['myApp']);
    });
	
});