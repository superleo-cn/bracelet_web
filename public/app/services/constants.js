define(['/app/services/module.js'], function (services) {
    'use strict';
    //define global variable 
    services.factory('Constants', function() {
    	return {
    		SUCCESS : '1',
    		FAILTURE : '0',
    		ERROR : '-1',
    		ADMIN : 'Admin',
    		USER : 'User',
    		DOCTOR : 'Doctor'
    	};
    });
});
