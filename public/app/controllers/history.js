define(['/app/controllers/module.js', 'highstock'], function (controllers, highstock) {
	'use strict';
    controllers.controller("History", function($http, $rootScope, $scope, $translate, $cookies, $stateParams, HttpService, Constants) {
    	// get DateTime yyyyMMddHHmmss
	    var getDateTime = function(){
	    	var dd = new Date()
	    	var val = dd.getFullYear()
						+ (dd.getMonth() < 9 ? '0' + (dd.getMonth()+1) : (dd.getMonth()+1))
	    				+ (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate()))
	       return val;
	    }

		var getStartDate = function(){
			return getMonthBefore(1);
		}

		var getMonthBefore = function(n){
			var dd = new Date();
			var year = dd.getFullYear();
			var month = (dd.getMonth() < 9 ? '0' + (dd.getMonth() + 1 - n) : (dd.getMonth() + 1 - n));
			var day = (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate()));
			return Date.UTC(year, month, day);
		}

		var intervalTime = 5 * 1000;
	    
	    // start
        var id = $stateParams.id; 
        $scope.braceletId = "0";
        if(id != null && id != ""){
        	HttpService.url = '/braceletsByUserId/' + id;
     	    HttpService.postParams = {};
          	HttpService.getParams = {};
          	HttpService.get(function(data, status) {
          		if(data){
    				if(data.code == Constants.SUCCESS){
    					$scope.bracelets = data.datas;
    					if($scope.bracelets == null || $scope.bracelets.length == 0){
    		     	    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
    		     	    }else{
    		     	    	$scope.braceletId = $scope.bracelets[0].braceletId;
    		     	    }
    				}else{
    			    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
    				}
    			}
        	});
        }else{
     	    $scope.bracelets = jQuery.parseJSON($cookies.current_bracelets);
			if(typeof $scope.bracelets != 'object') {
				$scope.bracelets = jQuery.parseJSON($scope.bracelets);
			}
     	    $scope.braceletId = "0";
     	    if($scope.bracelets == null || $scope.bracelets.length == 0){
     	    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
     	    }else{
     	    	$scope.braceletId = $scope.bracelets[0].braceletId;
     	    }
        }
	    
        // Report URL
	    HttpService.url = '/api/findHistoryList/' + $scope.braceletId + '/day/' + getDateTime();
	    HttpService.postParams = {};
     	HttpService.getParams = {};
     	
		/*
         * Flot Interactive Chart
         * -----------------------
         */
	    var temperatureChart = null;
	    var plusChart = null;
	    var motionChart = null;
	    var bloodPressureChart = null;
	    var flag = 1;
	    
	    $scope.switchTab = function(chart){
	    	if("temperatureChart" == chart){
	    		flag = 1;
	    	}else if("plusChart" == chart){
	    		flag = 2;
	    	}else if("motionChart" == chart){
	    		flag = 3;
	    	}else if("bloodPressureChart" == chart){
	    		flag = 4;
	    	}
	    	update("month");
	    };

	    $scope.changeBracelet = function(){
	    	if($scope.braceletId != "0"){
	    		update("month");
	    	}
	    };
	    
        function update(type) {
        	// We use an inline data source in the example, usually data would
            // be fetched from a server
        	HttpService.url = '/api/findHistoryList/' + $scope.braceletId + '/' + type + '/' + getDateTime();
        	HttpService.get(function(data, status) {
  		    	var temperatureDatas = [];
  		    	var pulseDatas = [];
  		    	var motionDatas = [];
  		    	var sbpDatas = [];
  		    	var dbpDatas = [];
	          	if(data){
	          		var list = data.datas;
	          		$.each( list, function( key, value ) {
	          			temperatureDatas.push(value.temperature);
						pulseDatas.push(value.pulseState);
	          			motionDatas.push(value.motionState);
	          			sbpDatas.push(value.sbp);
	          			dbpDatas.push(value.dbp);
		          	});
	          	}
	         
	          	if(flag == 1){
					getChart('temperatureChart', 'Temperature Chart', temperatureDatas, '°C');
	          	}else if(flag == 2){
					getChart('pulseChart', 'Pulse Chart', pulseDatas, '');
	          	}else if(flag == 3){
					getChart('motionChart', 'Motion Chart', motionDatas, '');
	          	}else if(flag == 4){
					getMutilChart('bloodPressureChart', 'Blood Chart', sbpDatas, dbpDatas, '')
	          	}
	          	
	    	});
        };
        
        update("month");
      
        /*
         * END INTERACTIVE CHART
         */
        function getChart(div, title, datas, suffix){
        	return $('#' + div).highcharts('StockChart', {
				rangeSelector : {
					selected : 1
				},
				series: [{
					data: datas,
					pointStart: getStartDate(),
					pointInterval: intervalTime,
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				}]

			});
        }

		function getMutilChart(div, title, datas1, datas2, suffix){
			var seriesOptions = [
				{
					name: 'sbp',
					data: datas1,
					pointStart: getStartDate(),
					pointInterval: intervalTime,
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				},
				{
					name: 'dbp',
					data: datas2,
					pointStart: getStartDate(),
					pointInterval: intervalTime,
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				}
			];

			return $('#' + div).highcharts('StockChart', {
				rangeSelector : {
					selected : 1
				},
				series: seriesOptions
			});
		}

        $scope.$on('$viewContentLoaded', function() {

        });
        
        $scope.$on('$destroy', function() {

        })
        
	})
});
