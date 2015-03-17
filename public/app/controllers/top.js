define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate, $cookies) {
    	$scope.logout = function() {
    		$rootScope.url = "/logoutJson";
    		$rootScope.method = "POST";
	    	$rootScope.template(function(data, status) {
	    		$rootScope.redirect("/");
	    	});
 	    }
    	
    	if($cookies.current_id == null || $cookies.current_id == ""){
			$rootScope.redirect("#users");
			event.preventDefault();
		};
    	
	})
});
