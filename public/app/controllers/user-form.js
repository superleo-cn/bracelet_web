define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("UserForm", function($http, $rootScope, $scope, $translate, $stateParams, Constants, MessageService, HttpService) {
    	$scope.user = {};
    	var id = $stateParams.id; 
    	
    	// get edit user
    	$scope.query = function(id) {
    		HttpService.url = '/users/' + id;
    		HttpService.postParams = {};
         	HttpService.getParams = {};
        	HttpService.get(function(data, status) {
        		$scope.status = status;
        		$scope.user = data.datas;
        	});
    	};
    	
    	// store user
    	$scope.store = function() {
    		HttpService.url = '/users/store';
    		HttpService.postParams = $scope.user;
         	HttpService.getParams = {};
         	HttpService.post(function(data, status) {
        		if(data.code == Constants.SUCCESS){
        			MessageService.successMsg(".alert", "Store successfully.");
				}else{
					MessageService.errorMsg(".alert", "Store failed.");
				}
        	});
    	};

    	// if edit user
    	if(id != null && id != ""){
			$scope.query(id);
		};

	})
});
