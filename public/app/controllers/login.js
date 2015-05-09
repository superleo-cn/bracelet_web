define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Login", function($rootScope, $scope, $translate, Constants, MessageService, HttpService) {
	    // My login URL
    	HttpService.url = $("#form").prop("action");
     	HttpService.postParams = {};
     	HttpService.getParams = {};
	    
	    /*
	     * INITIALIZE BUTTON ACTION
	     * ------------------------
	     * The login function
	     */
	    $scope.login = function() {
	    	HttpService.postParams = $scope.params;
	    	HttpService.post($scope.fetch);
	    }
	    
	    $scope.lockLogin = function() {
	    	HttpService.url = $("#url").val();
	    	HttpService.postParams.username = $("#username").val();
	    	HttpService.postParams.password = $("#password").val();
	    	HttpService.post($scope.fetch);
	    }
	    
	    $scope.fetch = function(data, status) {
	    	if(data){
				if(data.code == Constants.SUCCESS){
					MessageService.successMsg(".alert", "Login successfully.");
					$rootScope.redirect("/index");
				}else{
					MessageService.errorMsg(".alert", "Login failed.");
				}
			}
    	};
        
	})
});
