define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("Codes", function($http, $rootScope, $scope, $translate, HttpService) {
    	HttpService.url = '/codes';
    	HttpService.postParams = {};
    	HttpService.getParams = {};
    	
    	$scope.pagination = function(data, status) {
    		if (data.datas.recordList) {
    			$scope.status = status;
    			$scope.datas = data.datas.recordList;
    		} else {
    			$scope.datas = {};
    			$scope.total = 0;
    		}
    		// reset the pagination
    		$(".p").pagination({
    			items : data.datas.recordCount,
    			itemsOnPage : data.datas.pageSize,
    			cssStyle : 'compact-theme',
    			hrefTextPrefix: "#/codes?page-",
    			onPageClick : function(pageNumber) {
    				$rootScope.datas = {"currentPage" : pageNumber};
    				HttpService.post($scope.fetch);
    			}
    		});
    	};
    	
    	$scope.fetch = function(data, status) {
    		$scope.status = status;
    		$scope.datas = data.datas.recordList;
    		$scope.total = data.datas.recordCount;
    	};
    	
    	HttpService.post($scope.pagination);
    	
	})
});
