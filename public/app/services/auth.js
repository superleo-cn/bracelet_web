define(['/app/services/module.js'], function (services) {
    'use strict';
    services.service("AuthService", function(){
    	this.isAdmin = function($cookies){
    		if($cookies.current_role == "Admin"){
    			return true;
    		}
    		return false;
    	};
    	
    	this.isUser = function($cookies){
    		if($cookies.current_role == "User" || $cookies.current_role == "Admin"){
    			return true;
    		}
    		return false;
    	};
    	
    	this.isDoctor = function($cookies){
    		if($cookies.current_role == "Doctor" || $cookies.current_role == "Admin"){
    			return true;
    		}
    		return false;
    	}        
    });      
    
});
