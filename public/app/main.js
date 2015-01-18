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
		'flot' : '/assets/libs/plugins/flot/jquery.flot.min',
		'resize' : '/assets/libs/plugins/flot/jquery.flot.resize.min',
		'pie' : '/assets/libs/plugins/flot/jquery.flot.pie.min',
		'categories' : '/assets/libs/plugins/flot/jquery.flot.categories.min',
		'charts' : '/assets/app/controllers/charts',
	},
	shim : {
		'angular': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery'],
            // use the global 'Backbone' as the module name.
            exports: 'angular'
        },
        'ngRoute': {
            deps: ['jquery', 'angular'],
            exports: 'ngRoute'
        },
        'ngResource': {
            deps: ['jquery', 'angular'],
            exports: 'ngResource'
        },
        'uiRoute': {
            deps: ['jquery', 'angular'],
            exports: 'uiRoute'
        }
	}
});

require(['jquery', 'angular', 'uiRoute', 'domReady', 'flot', 'charts'], function($, angular, uiRoute, domReady, flot) {

	console.log('Initialize App ' + angular);

	angular.module('myApp', ['ui.router'])
	.config(function($stateProvider, $urlRouterProvider) {
		
		// For any unmatched url, redirect to /state1
		$urlRouterProvider.otherwise("/dashboard");
		//
		// Now set up the states
		$stateProvider
	  	.state('dashboard', {
	  		url: "/dashboard",
	  		templateUrl: "assets/app/templates/dashboard.html",
	  		controller: 'charts'
	    })
	    .state('about', {
	    	url: "/about",
	    	templateUrl: "assets/app/templates/about.html"
	    })
	});
	
	angular.element(document).ready(function() {
		angular.bootstrap(document, ['myApp']);
    });
	
});