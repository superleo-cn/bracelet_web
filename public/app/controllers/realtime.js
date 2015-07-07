define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Realtime", function($http, $rootScope, $scope, $translate, $cookies, $stateParams, HttpService, Constants) {
    	// get DateTime yyyyMMddHHmmss
	    var getDateTime = function(){
	    	var dd = new Date()
	    	var val = '' + dd.getFullYear() 
	    				+ (dd.getMonth() < 9 ? '0' + (dd.getMonth()+1) : (dd.getMonth()+1)) 
	    				+ (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate())) 
	    				+ (dd.getHours() < 10 ? '0' + dd.getHours() : dd.getHours())
	    				+ (dd.getMinutes() < 10 ? '0' + dd.getMinutes() : dd.getMinutes())
	    				+ (dd.getSeconds() < 10 ? '0' + dd.getSeconds() : dd.getSeconds())
	       return val;
	    }

		var intervalTime = 30 * 1000; //Fetch data ever x milliseconds
        var realtime = "on"; //If == to on then fetch data every x seconds. else stop fetching
		var flag = 0; // no chart to be displayed
		$scope.braceletId = "0"; // default bracelet id
		var id = $stateParams.id;

        if(id != null && id != ""){
        	HttpService.url = '/braceletsByUserId/' + id;
     	    HttpService.postParams = {};
          	HttpService.getParams = {};
          	HttpService.get(function(data, status) {
          		if(data){
    				if(data.code == Constants.SUCCESS){
    					$scope.bracelets = data.datas;
    					if($scope.bracelets == null || $scope.bracelets.length == 0){
    		     	    	realtime = "off";
    		     	    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
    		     	    }else{
    		     	    	$scope.braceletId = $scope.bracelets[0].braceletId;
    		     	    }
    				}else{
    					realtime = "off";
    			    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
    				}
    			}
        	});
        }else{
        	$scope.bracelets = jQuery.parseJSON($cookies.current_bracelets);
			if(typeof $scope.bracelets != 'object') {
				$scope.bracelets = jQuery.parseJSON($scope.bracelets);
			}
     	    if($scope.bracelets == null || $scope.bracelets.length == 0){
     	    	realtime = "off";
     	    	$scope.bracelets[0] = {"braceletId" : "0", "name" : "You don't have any bracelet"};
     	    }else{
     	    	$scope.braceletId = $scope.bracelets[0].braceletId;
     	    }
        }
	    
        // Report URL
	    HttpService.url = getUrl();
	    HttpService.postParams = {};
     	HttpService.getParams = {};
	    
	    /*
	     * INITIALIZE BUTTON TOGGLE
	     * ------------------------
	     * The switcher for data on/off
	     */
	    $('.btn-group[data-toggle="btn-toggle"]').each(function() {
	        var group = $(this);
	        $(this).find(".btn").click(function(e) {
	            group.find(".btn.active").removeClass("active");
	            $(this).addClass("active");
	            e.preventDefault();
	        });

	    });
	    
		/*
         * Flot Interactive Chart
         * -----------------------
         */
		function init(){
			flag = 1;
			getChart('temperatureChart', 'Temperature Chart', '°C', 1);
		}

		function getUrl(flag){
			return '/api/findRealtimeList/' + $scope.braceletId + '/' + getDateTime() + "/" + flag;
		}

	    $scope.switchTab = function(chart){
	    	if("temperatureChart" == chart){
	    		flag = 1;
				setTimeout(function () {
					getChart('temperatureChart', 'Temperature Chart', '°C', 1);
				}, 1000);
	    	}else if("pulseChart" == chart){
	    		flag = 2;
				setTimeout(function () {
					getChart('pulseChart', 'Pulse Chart', '', 2);
				}, 1000);
	    	}else if("motionChart" == chart){
	    		flag = 3;
				setTimeout(function () {
					getChart('motionChart', 'Motion Chart', '', 3);
				}, 1000);
	    	}else if("bloodPressureChart" == chart){
	    		flag = 4;
				setTimeout(function () {
					getMutilChart('bloodPressureChart', 'Blood Pressure Chart', '', 4);
				}, 1000);
	    	}else if("integratedData" == chart){
	    		flag = 5;
	    	}
	    };
	    
	    $scope.changeBracelet = function(){
			init();
	    };

		function getHttpData(series, series2, isFirst) {
			if (realtime === "on"){
				// We use an inline data source in the example, usually data would
				// be fetched from a server
				HttpService.url = getUrl(isFirst);
				HttpService.get(function(data, status) {
					if(data){
						var list = data.datas;
						$.each( list, function( key, value ) {
							if(flag == 1){
								series.addPoint([value.createDate, value.temperature], true, true);
							}else if(flag == 2){
								series.addPoint([value.createDate, value.pulseState], true, true);
							}else if(flag == 3){
								series.addPoint([value.createDate, value.motionState], true, true);
							}else if(flag == 4){
								series.addPoint([value.createDate, value.sbp], true, true);
								series2.addPoint([value.createDate, value.dbp], true, true);
							}
						});
					}
				});
			}
		};

        //INITIALIZE REALTIME DATA FETCHING, FIRST TIME
		init();

        //REALTIME TOGGLE
        $("#temperatureBtn .btn").click(function() {
            if ($(this).data("toggle") === "on") {
                realtime = "on";
            }
            else {
                realtime = "off";
            }
        });

		Highcharts.setOptions({
			global: {
				useUTC: false
			}
		});
        /*
         * END INTERACTIVE CHART
         */
		function getChart(div, title, suffix, chartNum){
			return $('#' + div).highcharts({
				chart: {
					type: 'spline',
					animation: Highcharts.svg, // don't animate in old IE
					marginRight: 10,
					events: {
						load: function () {
							// set up the updating of the chart each second
							var series = this.series[0];
							setTimeout(function () {
								if(chartNum == flag) {
									getHttpData(series, null, true);
								}
							}, 1000);
							setInterval(function () {
								if(chartNum == flag) {
									getHttpData(series, null, false);
								}
							}, intervalTime);
						}
					},
				},
				title: {
					text: ""
				},
				xAxis: {
					type: 'datetime',
					tickPixelInterval: 150
				},
				yAxis: {
					title: {
						text: ''
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				legend: {
					enabled: false
				},
				series: [{
					data: (function () {
						// generate an array of random data
						var data = [],
							time = (new Date('2014-01-01')).getTime();
						for (var i = -24; i < 0; i += 1) {
							data.push({
								x: time + i * intervalTime,
								y: 0
							});
						}
						return data;
					}()),
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				}],
				chartNum: chartNum
			});
		}

		function getMutilChart(div, title, suffix, chartNum){
			var seriesOptions = [
				{
					name: 'sbp',
					data: (function () {
						// generate an array of random data
						var data = [],
							time = (new Date('2014-01-01')).getTime();
						for (var i = -24; i < 0; i += 1) {
							data.push({
								x: time + i * intervalTime,
								y: 0
							});
						}
						return data;
					}()),
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				},
				{
					name: 'dbp',
					data: (function () {
						// generate an array of random data
						var data = [],
							time = (new Date('2014-01-01')).getTime();
						for (var i = -24; i < 0; i += 1) {
							data.push({
								x: time + i * intervalTime,
								y: 0
							});
						}
						return data;
					}()),
					tooltip: {
						valueDecimals: 1,
						valueSuffix: suffix
					}
				}
			];

			return $('#' + div).highcharts({
				chart: {
					type: 'spline',
					animation: Highcharts.svg, // don't animate in old IE
					marginRight: 10,
					events: {
						load: function () {
							// set up the updating of the chart each second
							var series = this.series[0];
							var series2 = this.series[1];
							setTimeout(function () {
								if(chartNum == flag) {
									getHttpData(series, series2, true);
								}
							}, 1000);
							setInterval(function () {
								if(chartNum == flag) {
									getHttpData(series, series2, false);
								}
							}, intervalTime);
						}
					},
				},
				title: {
					text: ''
				},
				xAxis: {
					type: 'datetime',
					tickPixelInterval: 150
				},
				yAxis: {
					title: {
						text: ''
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				legend: {
					enabled: false
				},
				series: seriesOptions,
				chartNum: chartNum
			});
		}

        $scope.$on('$viewContentLoaded', function() {
        	realtime = "on";
        });
        
        $scope.$on('$destroy', function() {
        	realtime = "off";
        })
        
	})
});
