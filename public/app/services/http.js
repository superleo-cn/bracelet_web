define(['/app/services/module.js'], function (services) {
    'use strict';
    services.service("HttpService", function($http) {
    	this.postParams = {};
    	this.getParams = {};
    	this.url = "";
    	this.message = "";
    	this.status = "";
        //http method
        this.post = function(operation){
	    	$http({
	    		method: "POST", 
	    		url: this.url, 
	    		params: this.getParams,
	    		data: this.postParams
	    	}).success(operation)
	    	.error(function(data, status) {
	    		this.message = data || "Request failed";
	    		this.status = status;
	    	});
        }
        
        this.get = function(operation){
	    	$http({
	    		method: "GET", 
	    		url: this.url, 
	    		params: this.getParams,
	    		data: {}
	    	}).success(operation)
	    	.error(function(data, status) {
	    		this.message = data || "Request failed";
	    		this.status = status;
	    	});
        }
        
	});
    
    services.factory("RestService", function($resource) {
    	this.postParams = {};
    	this.getParams = {};
    	this.url = "";
    	this.message = "";
    	this.status = "";
    	return $resource(
    		'/loginJson',
    		{},
            {
                "loginJson": {method: "POST", params: this.postParams, isArray: false}
            }
        );
	});

});
