define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Login", function($rootScope, $http, $scope, $translate, Constants) {
	    // My login URL
    	$rootScope.url = $("#form").prop("action");
	    
	    /*
	     * INITIALIZE BUTTON ACTION
	     * ------------------------
	     * The login function
	     */
	    $scope.login = function() {
	    	$rootScope.template($scope.fetch);
	    }
	    
	    $scope.fetch = function(data, status) {
	    	if(data){
				if(data.code == Constants.SUCCESS){
					$rootScope.successMsg(".alert", "Login successfully.");
					$rootScope.redirect("/index");
				}else{
					$rootScope.errorMsg(".alert", "Login failed.");
				}
			}
    	};
        
	})
});
