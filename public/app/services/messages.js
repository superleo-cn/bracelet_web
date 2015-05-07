define(['/app/services/module.js'], function (services) {
    'use strict';
    services.service("MessageService", function($timeout, $window) {
    	// show success message;
        this.successMsg = function(div, msg) {
            $(div).removeClass("alert-danger").addClass("alert-success");
            $(div + " span").html(msg);
            $(div).show();
        };
        
        // show error message;
        this.errorMsg = function(div, msg) {
            $(div).removeClass("alert-success").addClass("alert-danger");
            $(div + " span").html(msg);
            $(div).show();
        };
    	
	});
    
});
