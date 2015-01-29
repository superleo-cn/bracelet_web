define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate) {
    	$scope.logout = function() {
 	    	alert("测试注销");
 	    }
	})
});
