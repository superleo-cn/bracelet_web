define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("BraceletForm", function($http, $rootScope, $scope, $translate, $stateParams, Constants, MessageService) {
    	
    	$scope.bracelet = {};
    	var id = $stateParams.id; 
    	
    	// get edit bracelet
    	$scope.query = function(id) {
    		$rootScope.method = "GET";
        	$rootScope.url = '/bracelets/' + id;
        	$rootScope.data = {};
        	$rootScope.template(function(data, status) {
        		$scope.status = status;
        		$scope.bracelet = data.datas;
        	});
    	};
    	
    	// store bracelet
    	$scope.store = function() {
    		$rootScope.method = "POST";
        	$rootScope.url = '/bracelets/store';
	    	$rootScope.data = $scope.bracelet;
	    	debugger;
        	$rootScope.template(function(data, status) {
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
