define([
    'angular',
    'uiRouter',
    'ngCookies',
    'translate',
    'loaderStaticFiles',
    'storagekey',
    'storageLocal',
    'storageCookie',
    'controllers',
    'directives',
    'filters',
    'services'
], function (ng) {
    'use strict';

    var app = ng.module('app', [
		'app.controllers',
		'app.directives',
		'app.filters',
		'app.services',
		'ui.router',
		'pascalprecht.translate',
		'ngCookies'
    ]);
    
    app.config(['$translateProvider', function ($translateProvider) {
    	
    	$translateProvider.useStaticFilesLoader({
    		prefix: '/app/l10n/',
    		suffix: '.json'
    	});
    	
    	// Tell the module what language to use by default
    	$translateProvider.preferredLanguage('zh');
    	// Tell the module to store the language in the cookies(session)
    	//$translateProvider.useCookieStorage();
    	// Tell the module to store the language in the cookies(persist)
    	$translateProvider.useLocalStorage();
    	// Tell the module to use a key 'lang' in the storage instead of default key
    	// $translateProvider.storageKey('zh');
    	
	}]);
    
    // global function
    app.run(function($http, $rootScope, $cookieStore, $timeout, $window, $translate, $state, $cookies) {
    	 // change language
        $rootScope.setLang = function(key) {
            $translate.use(key);
        };
        
        // redirect URL
	    $rootScope.redirect = function(url){
		    $timeout(function(){
				$window.location.href = url;
			}, 100);
    	};
    	
    	// close alert box
        $('.close').click(function(event) {
        	$('.alert').hide();
        	return;
        })
    	
    	// authentication
    	$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
    		
	    });
	    
    });

    return app;
});