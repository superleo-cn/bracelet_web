define(['/app/controllers/module.js', 'pagination'], function (controllers) {
    'use strict';
    controllers.controller("Timeline", function ($http, $rootScope, $filter, $scope, $translate, $cookies, $stateParams, HttpService, Constants) {
        // get DateTime yyyyMMddHHmmss
        var getDateTime = function () {
            var dd = new Date()
            var val = '' + dd.getFullYear()
                + (dd.getMonth() < 9 ? '0' + (dd.getMonth() + 1) : (dd.getMonth() + 1))
                + (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate()))
            return val;
        }

        var colors = ["bg-red", "bg-teal", "bg-yellow", "bg-blue", "bg-green", "bg-purple", "bg-aqua"];

        $scope.userId = $cookies.current_id;
        HttpService.url = '/findEventsByUser/' + $scope.userId + '/' + getDateTime();
        HttpService.postParams = {};
        HttpService.getParams = {};

        $scope.pagination = function (data, status) {
            var count = Object.keys(data.datas).length;
            if (count > 0) {
                $scope.status = status;
                $scope.datas = data.datas;
                $scope.total = count;
            } else {
                $scope.datas = {};
                $scope.total = 0;
            }
        };

        $scope.fetch = function (data, status) {
            $scope.status = status;
            $scope.datas = data.datas;
        };

        $scope.getType = function (type) {
            if (type == "1") {
                return "fa-envelope bg-blue";
            } else if (type == "2") {
                return "fa-user bg-aqua";
            } else if (type == "3") {
                return "fa-comments bg-yellow";
            }
        };

        $scope.getBgColor = function (index) {
            return colors[index % 7];
        };

        HttpService.get($scope.pagination);

    })
});
