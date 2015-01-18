// require JS config
define(['app'], function(app) {
    'use strict';

    return app.config(function($stateProvider, $urlRouterProvider) {
		
		// For any unmatched url, redirect to /state1
		$urlRouterProvider.otherwise("/dashboard");
		//
		// Now set up the states
		$stateProvider
	  	.state('dashboard', {
	  		url: '/dashboard',
	  		templateUrl: 'assets/app/templates/dashboard.html',
	  		controller: 'Charts'
	    })
	    .state('about', {
	    	url: '/about',
	    	templateUrl: 'assets/app/templates/about.html'
	    })
	});
});