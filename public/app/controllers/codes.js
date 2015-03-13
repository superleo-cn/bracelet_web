define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("Codes", function($http, $rootScope, $scope, $translate) {
    	
    	$rootScope.method = "POST";
    	$rootScope.url = '/codes';
    	$rootScope.data = {};
        $rootScope.params = {};
    	
    	$scope.pagination = function(data, status) {
    		if (data.datas.recordList) {
    			$scope.status = status;
    			$scope.data = data.datas.recordList;
    		} else {
    			$scope.data = {};
    			$scope.total = 0;
    		}
    		// reset the pagination
    		$(".p").pagination({
    			items : data.datas.recordCount,
    			itemsOnPage : data.datas.pageSize,
    			cssStyle : 'compact-theme',
    			hrefTextPrefix: "#/codes?page-",
    			onPageClick : function(pageNumber) {
    				$rootScope.params = {"currentPage" : pageNumber};
    				$rootScope.template($scope.fetch);
    			}
    		});
    	};
    	
    	$scope.fetch = function(data, status) {
    		$scope.status = status;
    		$scope.data = data.datas.recordList;
    		$scope.total = data.datas.recordCount;
    	};
    	
    	$rootScope.template($scope.pagination);
    	
	})
});
