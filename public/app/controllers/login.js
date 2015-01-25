define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Login", function($http, $scope, $translate) {
	    // My Ajax Request Template
	    var requestTemplate = function(operation) {
	    	$scope.code = null;
	    	$scope.response = null;
	    	$scope.params = null;
	    	$scope.method = 'POST';
		    $scope.url = $("#form").prop("action");
	    	$http({
	    		method: $scope.method, 
	    		url: $scope.url, 
	    		params: {},
	    		data: $scope.params
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
	    $('.btn-group[data-toggle="btn-toggle"]').each(function() {
	        var group = $(this);
	        $(this).find(".btn").click(function(e) {
	            group.find(".btn.active").removeClass("active");
	            $(this).addClass("active");
	            e.preventDefault();
	        });

	    });
	    
		
        
	})
});
