define([
    'angular',
    'uiRouter',
    'ngCookies',
    'controllers',
    'translate',
    'loaderStaticFiles',
    'storagekey',
    'storageLocal',
    'storageCookie'
], function (ng) {
    'use strict';

    var app = ng.module('app', [
		'app.controllers',
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
    
    // change language
    app.run(function($rootScope, $cookieStore, $translate) {
        $rootScope.setLang = function(key) {
            $translate.use(key);
        };
    });
    
    return app;
});