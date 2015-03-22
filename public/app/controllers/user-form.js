define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("UserForm", function($http, $rootScope, $scope, $translate, $stateParams, Constants) {
    	
    	$scope.user = {};
    	var id = $stateParams.id; 
    	
    	// get edit user
    	$scope.query = function(id) {
    		$rootScope.method = "GET";
        	$rootScope.url = '/users/' + id;
        	$rootScope.data = {};
        	$rootScope.template(function(data, status) {
        		$scope.status = status;
        		$scope.user = data.datas;
        	});
    	};
    	
    	// store user
    	$scope.store = function() {
    		$rootScope.method = "POST";
        	$rootScope.url = '/users/store';
	    	$rootScope.data = $scope.user;
	    	debugger;
        	$rootScope.template(function(data, status) {
        		if(data.code == Constants.SUCCESS){
					$rootScope.successMsg(".alert", "Store successfully.");
				}else{
					$rootScope.errorMsg(".alert", "Store failed.");
				}
        	});
    	};

    	// if edit user
    	if(id != null && id != ""){
			$scope.query(id);
		};

	})
});
