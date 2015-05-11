// require JS config
define(['app', 'jquery.cookie'], function(app) {
    'use strict';
    
    return app.config(function($stateProvider, $urlRouterProvider) {
    	
    	// if not logined yet
    	var value = $.cookie("current_id");
    	if(value == null || value == ""){
			return;
		};
    	
		// For any unmatched url, redirect to /state1
		// $urlRouterProvider.otherwise("/realtime");
		
		// Now set up the states
		$stateProvider
	  	.state('realtime', {
	  		url: '/realtime/:id',
	  		templateUrl: 'assets/app/templates/realtime.html',
	  		controller: 'Realtime',
	  		roles: ['admin', 'user', 'doctor']
	    })
	    .state('history', {
	  		url: '/history/:id',
	  		templateUrl: 'assets/app/templates/history.html',
	  		controller: 'History',
	  		roles: ['admin', 'user', 'doctor']
	    })
	    .state('about', {
	    	url: '/about',
	    	templateUrl: 'assets/app/templates/about.html',
	    	controller: 'About',
	    	roles: ['admin', 'user', 'doctor']
	    })
	    .state('users', {
	  		url: '/users',
	  		templateUrl: 'assets/app/templates/user-list.html',
	  		controller: 'Users',
	  		roles: ['admin']
	    })
	    .state('user', {
	  		url: '/users/:id',
	  		templateUrl: 'assets/app/templates/user-form.html',
	  		controller: 'UserForm',
	  		roles: ['admin']
	    })
	    .state('bracelets', {
	  		url: '/bracelets',
	  		templateUrl: 'assets/app/templates/bracelet-list.html',
	  		controller: 'Bracelets',
	  		roles: ['admin']
	    })
	    .state('bracelet', {
	  		url: '/bracelets/:id',
	  		templateUrl: 'assets/app/templates/bracelet-form.html',
	  		controller: 'BraceletForm',
	  		roles: ['admin']
	    })
	    .state('codes', {
	  		url: '/codes',
	  		templateUrl: 'assets/app/templates/code-list.html',
	  		controller: 'Codes',
	  		roles: ['admin']
	    })
	    .state('timeline', {
	  		url: '/timeline',
	  		templateUrl: 'assets/app/templates/timeline.html',
	  		controller: 'Timeline',
	  		roles: ['admin', 'user', 'doctor']
	    })
	});
});