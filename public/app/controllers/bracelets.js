define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("Bracelets", function($http, $rootScope, $scope, $translate, HttpService) {
        HttpService.url = '/bracelets';
    	HttpService.postParams = {};
    	HttpService.getParams = {};
    	
    	$scope.pagination = function(data, status) {
    		if (data.datas.recordList) {
    			$scope.status = status;
    			$scope.datas = data.datas.recordList;
    			//$scope.total = data.datas.totalRecord;
    			//$scope.sortColumnName = data.datas.sortColumnName;
    			//$scope.sortType = data.datas.sortType;
    			//$scope.start = getPageStart(data.datas);
    		} else {
    			$scope.datas = {};
    			$scope.total = 0;
    			//$(".auditBox").html("No Records found!");
    			//$(".auditBox").show();
    		}
    		// reset the pagination
    		$(".p").pagination({
    			items : data.datas.recordCount,
    			itemsOnPage : data.datas.pageSize,
    			cssStyle : 'compact-theme',
    			hrefTextPrefix: "#/bracelets?page-",
    			onPageClick : function(pageNumber) {
    				HttpService.postParams = {"currentPage" : pageNumber};
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
