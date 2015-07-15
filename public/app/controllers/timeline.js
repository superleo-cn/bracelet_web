define(['/app/controllers/module.js', 'pagination'], function (controllers) {
	'use strict';
    controllers.controller("Timeline", function($http, $rootScope, $scope, $translate, $cookies, $stateParams, HttpService, Constants) {
		// get DateTime yyyyMMddHHmmss
		var getDateTime = function(){
			var dd = new Date()
			var val = '' + dd.getFullYear()
				+ (dd.getMonth() < 9 ? '0' + (dd.getMonth()+1) : (dd.getMonth()+1))
				+ (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate()))
			return val;
		}

		$scope.userId = $cookies.current_id;
		HttpService.url = '/findEventsByUser/' + $scope.userId + '/' + getDateTime();
		HttpService.postParams = {};
		HttpService.getParams = {};

		$scope.pagination = function(data, status) {
			if (data.datas) {
				$scope.status = status;
				$scope.datas = data.datas;
			} else {
				$scope.datas = {};
				$scope.total = 0;
			}
		};

		$scope.fetch = function(data, status) {
			$scope.status = status;
			$scope.datas = data.datas;
		};

		HttpService.get($scope.pagination);

	})
});
