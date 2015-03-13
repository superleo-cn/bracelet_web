define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("NavCtrl", function($http, $rootScope, $scope, $translate, $cookies, AuthService) {
    	$scope.isAdmin = function(){
    		return AuthService.isAdmin($cookies);
    	};
    	
    	$scope.isUser = function(){
    		return AuthService.isUser($cookies);
    	};
    	
    	$scope.isDoctor = function(){
    		return AuthService.isDoctor($cookies);
    	}
    	
	})
});
