define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("BraceletForm", function($http, $rootScope, $scope, $translate, $stateParams, Constants, MessageService, HttpService) {
    	
    	$scope.bracelet = {};
    	var id = $stateParams.id; 
    	
    	// get edit bracelet
    	$scope.query = function(id) {
    		HttpService.url = '/bracelets/' + id;
	    	HttpService.postParams = {};
	    	HttpService.getParams = {};
	    	HttpService.get(function(data, status) {
        		$scope.status = status;
        		$scope.bracelet = data.datas;
        	});
    	};
    	
    	// store bracelet
    	$scope.store = function() {
	    	HttpService.url = '/bracelets/store';
	    	HttpService.postParams = $scope.bracelet;
	    	HttpService.getParams = {};
	    	
	    	HttpService.post(function(data, status) {
        		if(data.code == Constants.SUCCESS){
        			MessageService.successMsg(".alert", "Store successfully.");
				}else{
					MessageService.errorMsg(".alert", "Store failed.");
				}
        	});
    	};

    	// if edit bracelet
    	if(id != null && id != ""){
			$scope.query(id);
		};

	})
});
