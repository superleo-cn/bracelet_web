define(['/app/services/module.js'], function (services) {
    'use strict';
    services.service("AuthService", function(Constants){

    	this.isAdmin = function($cookies){
    		if($cookies.current_role == Constants.ADMIN){
    			return true;
    		}
    		return false;
    	};
    	
    	this.isUser = function($cookies){
    		if($cookies.current_role == Constants.USER || $cookies.current_role == Constants.ADMIN){
    			return true;
    		}
    		return false;
    	};
    	
    	this.isDoctor = function($cookies){
    		if($cookies.current_role == Constants.DOCTOR || $cookies.current_role == Constants.ADMIN){
    			return true;
    		}
    		return false;
    	}        
    });      
    
});
