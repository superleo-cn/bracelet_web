define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate, $cookies) {
    	$scope.currentUser = {};
    	$scope.logout = function() {
    		$rootScope.url = "/logoutJson";
    		$rootScope.params = {};
    		$rootScope.data = {};
    		$rootScope.method = "POST";
	    	$rootScope.template(function(data, status) {
	    		$rootScope.redirect("/");
	    	});
 	    }
    	
    	if($cookies.current_id == null || $cookies.current_id == ""){
			$rootScope.redirect("#users");
			event.preventDefault();
		};
    	
		$scope.currentUser.current_id = $cookies.current_id;
		$scope.currentUser.current_username = $cookies.current_username;
		$scope.currentUser.current_realname = $cookies.current_realname;
		$scope.currentUser.current_role = $cookies.current_role;
		$scope.currentUser.current_create_date = $cookies.current_create_date;
		
	})
});
