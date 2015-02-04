define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("TopCtrl", function($http, $rootScope, $scope, $translate) {
    	var braceletId = $("#braceletId").val();
    	if(!braceletId || braceletId == ""){
    		alert("请您先登录");
    		$rootScope.redirect("/");
    	}
    	
    	$scope.logout = function() {
    		$rootScope.redirect("/");
 	    }
	})
});
