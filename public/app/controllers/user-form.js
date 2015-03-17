define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("UserForm", function($http, $rootScope, $scope, $translate, $stateParams) {
    	
    	$rootScope.method = "GET";
    	$scope.user = {};
    	var id = $stateParams.id; 
    	
    	// get edit user
    	$scope.query = function(id) {
        	$rootScope.url = '/users/' + id;
        	$rootScope.data = {};
        	$rootScope.template(function(data, status) {
        		$scope.status = status;
        		$scope.user = data.datas;
        	});
    	};
    	
    	// store user
    	$scope.query = function(id) {
        	$rootScope.url = '/users/store';
	    	$rootScope.data = $scope.user;
        	$rootScope.template(function(data, status) {
        		alert("successfully.");
        	});
    	};

    	// if edit user
    	if(id != null && id != ""){
			$scope.query(id);
		};

	})
});
