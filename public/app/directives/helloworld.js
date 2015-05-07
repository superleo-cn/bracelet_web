define(['/app/directives/module.js'], function (directives) {
    'use strict';
    directives.directive('helloworld', ['version', 'MessageService', 'AuthService', function (version, MessageService, AuthService) {
    	return {
    		restrict: 'AE',
    		replace: 'true',
    		template: '<h3>Hello World!!'+version+'</h3>'
    	};
    }]);
});
