define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Login", function($http, $scope, $translate) {
	    // My Ajax Request Template
    	$scope.data = {};
    	$scope.params = {};
    	
    	$scope.template = function(operation) {
	    	$scope.method = 'POST';
		    $scope.url = $("#form").prop("action");
	    	$http({
	    		method: $scope.method, 
	    		url: $scope.url, 
	    		//params: {},
	    		params: $scope.params
	    	}).success(operation)
	    	.error(function(data, status) {
	          	$scope.data = data || "Request failed";
	          	$scope.status = status;
	    	});
	    };
	    
	    /*
	     * INITIALIZE BUTTON ACTION
	     * ------------------------
	     * The login function
	     */
	    $scope.login = function() {
	    	$scope.template($scope.fetch);
	    }
	    
	    $scope.fetch = function(data, status) {
	    	if(data){
				if(data.code == '1'){
					alert("Login successfully.");
					//window.location.href = $("#cmsHome").val();
				}else{
					alert("Login failture.");
				}
			}
    	};
        
	})
});
