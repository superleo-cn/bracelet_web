// require JS config
define(['app'], function(app) {
    'use strict';

    return app.config(function($stateProvider, $urlRouterProvider) {
		
		// For any unmatched url, redirect to /state1
		$urlRouterProvider.otherwise("/realtime");
		//
		// Now set up the states
		$stateProvider
	  	.state('realtime', {
	  		url: '/realtime',
	  		templateUrl: 'assets/app/templates/realtime.html',
	  		controller: 'Realtime'
	    })
	    .state('history', {
	  		url: '/history',
	  		templateUrl: 'assets/app/templates/history.html',
	  		controller: 'History'
	    })
	    .state('about', {
	    	url: '/about',
	    	templateUrl: 'assets/app/templates/about.html'
	    })
	});
});