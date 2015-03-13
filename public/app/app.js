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
        
        // show success message;
        $rootScope.successMsg = function(div, msg) {
            $(div).removeClass("alert-danger").addClass("alert-success");
            $(div + " span").html(msg);
            $(div).show();
        };
        
        // show error message;
        $rootScope.errorMsg = function(div, msg) {
            $(div).removeClass("alert-success").addClass("alert-danger");
            $(div + " span").html(msg);
            $(div).show();
        };
        
        // close alert box
        $('.close').click(function(event) {
        	$('.alert').hide();
        	return;
        })
        
        // my ajax template
        $rootScope.currentUser = {};
        $rootScope.data = {};
        $rootScope.params = {};
        $rootScope.method = "POST";
    	$rootScope.template = function(operation) {
	    	$http({
	    		method: $rootScope.method, 
	    		url: $rootScope.url, 
	    		params: $rootScope.params,
	    		data: $rootScope.params
	    	}).success(operation)
	    	.error(function(data, status) {
	    		$rootScope.data = data || "Request failed";
	    		$rootScope.status = status;
	    	});
	    };
	    
	    // redirect URL
	    $rootScope.redirect = function(url){
		    $timeout(function(){
				$window.location.href = url;
			}, 100);
    	};
    	
    	// get current login Uer
	    $rootScope.getCurrentUser = function(){
	    	$rootScope.currentUser.braceletId = $("#braceletId").val();
    	};
    	
    	// authentication
    	$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
    		
	    });
	    
    });
    
	app.filter('yyyyMMdd', function($filter){
	    return function(input){
	        if(input == null){ 
				return ""; 
			}
	        return $filter('date')(new Date(input), 'yyyy-MM-dd');
	    };
	});
    
    return app;
});