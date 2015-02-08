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
	    .state('users', {
	  		url: '/users',
	  		templateUrl: 'assets/app/templates/user-list.html',
	  		controller: 'Users'
	    })
	    .state('user-form', {
	  		url: '/user-form',
	  		templateUrl: 'assets/app/templates/user-form.html',
	  		controller: 'UserForm'
	    })
	    .state('bracelets', {
	  		url: '/bracelets',
	  		templateUrl: 'assets/app/templates/bracelet-list.html',
	  		controller: 'Bracelets'
	    })
	    .state('timeline', {
	  		url: '/timeline',
	  		templateUrl: 'assets/app/templates/timeline.html',
	  		controller: 'Timeline'
	    })
	});
});