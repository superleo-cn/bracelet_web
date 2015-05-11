define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate, $cookies, HttpService) {
    	$rootScope.currentUser = {};
    	
    	$scope.logout = function() {
    		HttpService.url = "/logoutJson";
    		HttpService.postParams = {};
         	HttpService.getParams = {};
         	HttpService.post(function(data, status) {
	    		$rootScope.redirect("/");
	    	});
 	    }
    	
    	$rootScope.currentUser.current_id = $cookies.current_id;
    	$rootScope.currentUser.current_username = $cookies.current_username;
    	$rootScope.currentUser.current_realname = $cookies.current_realname;
    	$rootScope.currentUser.current_role = $cookies.current_role;
    	$rootScope.currentUser.current_create_date = $cookies.current_create_date;
		
	})
});
