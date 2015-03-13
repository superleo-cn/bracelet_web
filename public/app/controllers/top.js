define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate, $cookies	) {
    	$scope.logout = function() {
    		$rootScope.redirect("/");
 	    }
    	
    	if($cookies.current_id == null || $cookies.current_id == ""){
			$rootScope.redirect("/");
			event.preventDefault();
		};
    	
	})
});
