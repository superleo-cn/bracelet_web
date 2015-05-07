define(['/app/filters/module.js'], function (filters) {
    'use strict';
    
    filters.filter('yyyyMMdd', function($filter){
	    return function(input){
	        if(input == null){ 
				return ""; 
			}
	        return $filter('date')(new Date(input), 'yyyy-MM-dd');
	    };
	});
    
    filters.filter('ddMMyyyy', function($filter){
	    return function(input){
	        if(input == null){ 
				return ""; 
			}
	        return $filter('date')(new Date(input), 'dd/MM/yyyy');
	    };
	});
    
    return filters;
});
