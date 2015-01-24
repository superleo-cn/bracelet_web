define([
    'angular',
    'uiRouter',
    'ngCookies',
    'controllers',
    'translate',
    'loaderStaticFiles'
], function (ng) {
    'use strict';

    var app = ng.module('app', [
		'app.controllers',
		'ui.router',
		'pascalprecht.translate'
    ]);
    
    app.config(['$translateProvider', function ($translateProvider) {
    	
    	/*
    	$translateProvider.translations('en', {
    		TITLE: 'Hello',
    		FOO: 'This is a paragraph.',
    		BUTTON_LANG_EN: 'english',
    		BUTTON_LANG_DE: 'german'
    	});
    	$translateProvider.translations('zh', {
    		TITLE: '你好',
    		FOO: 'Dies ist ein Paragraph.',
    		BUTTON_LANG_EN: 'englisch',
    		BUTTON_LANG_DE: 'deutsch'
    	});
    	*/
    	
    	$translateProvider.useStaticFilesLoader({
    		prefix: '/app/l10n/',
    		suffix: '.json'
    	});
    	
    	
    	$translateProvider.preferredLanguage('en');
	}]);
    
    // change language
    app.run(function($rootScope, $translate) {
        $rootScope.setLang = function(key) {
            $translate.use(key);
        };
    });
    
    return app;
});