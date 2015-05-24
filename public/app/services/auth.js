define(['/app/services/module.js'], function (services) {
    'use strict';
    services.service("AuthService", function(Constants){

		this.currentUserId = function($cookies){
			if($cookies.current_id != null && $cookies.current_id != ""){
				return $cookies.current_id;
			}
			return null;
		};

    	this.isLogin = function($cookies){
    		if($cookies.current_id != null && $cookies.current_id != ""){
    			return true;
    		}
    		return false;
    	};
    	
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
