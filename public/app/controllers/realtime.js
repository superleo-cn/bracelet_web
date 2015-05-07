define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Realtime", function($http, $rootScope, $scope, $translate, HttpService) {
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
	    
	    // Report URL
	    var braceletId = $("#braceletId").val();
	    HttpService.url = '/api/findRealtimeList/' + braceletId + '/' + getDateTime();
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
	    	}else if("integratedData" == chart){
	    		flag = 5;
	    	}
	    	update();
	    };
	    
        var updateInterval = 5000; //Fetch data ever x milliseconds
        var realtime = "on"; //If == to on then fetch data every x seconds. else stop fetching
        function update() {
        	if (realtime === "on"){
	        	// We use an inline data source in the example, usually data would
	            // be fetched from a server
        		HttpService.url = '/api/findRealtimeList/' + braceletId + '/' + getDateTime();
        		HttpService.get(function(data, status) {
	  		    	var temperatureDatas = [];
	  		    	var plusDatas = [];
	  		    	var motionDatas = [];
	  		    	var sbpDatas = [];
	  		    	var dbpDatas = [];
		          	if(data){
		          		var list = data.datas;
		          		$.each( list, function( key, value ) {
		          			temperatureDatas.push([key, value.temperature]);
		          			plusDatas.push([key, value.pulseState]);
		          			motionDatas.push([key, value.motionState]);
		          			sbpDatas.push([key, value.sbp]);
		          			dbpDatas.push([key, value.dbp]);
			          	});
		          	}
		         
		          	if(flag == 1){
		          		temperatureChart = getTemperatureChart(temperatureDatas.length-1);
		          		temperatureChart.setData([temperatureDatas]);
		          		temperatureChart.draw();
		          	}else if(flag == 2){
		          		plusChart = getPlusChart(plusDatas.length-1);
		          		plusChart.setData([plusDatas]);
		          		plusChart.draw();
		          	}else if(flag == 3){
		          		motionChart = getMotionChart(motionDatas.length-1);
		          		motionChart.setData([motionDatas]);
		          		motionChart.draw();
		          	}else if(flag == 4){
		          		bloodPressureChart = getBloodPressureChart(sbpDatas.length-1);
		          		bloodPressureChart.setData([sbpDatas, dbpDatas]);
		          		bloodPressureChart.draw();
		          	}else if(flag == 5){
		          		
		          	}
		          	
		          	setTimeout(update, updateInterval);
	        	});
        	}
        };

        //INITIALIZE REALTIME DATA FETCHING
        if (realtime === "on") {
            update();
        }
        //REALTIME TOGGLE
        $("#temperatureBtn .btn").click(function() {
            if ($(this).data("toggle") === "on") {
                realtime = "on";
            }
            else {
                realtime = "off";
            }
            update();
        });
        /*
         * END INTERACTIVE CHART
         */
        
        function getTemperatureChart(xmax){
	        return $.plot("#temperatureChart", [[0, 0]], {
	            grid: {
	                borderColor: "#f3f3f3",
	                borderWidth: 1,
	                tickColor: "#f3f3f3"
	            },
	            series: {
	                shadowSize: 0, // Drawing is faster without shadows
	                color: "#3c8dbc"
	            },
	            lines: {
	                fill: true, //Converts the line chart to area chart
	                color: "#3c8dbc"
	            },
	            yaxis: {
	                min: 0,
	                max: 50,
	                show: true
	            },
	            xaxis: {
	            	min: 0,
	            	max: xmax,
	                show: true
	            }
	        });
        }
        
        function getPlusChart(xmax){
        	return $.plot("#plusChart", [[0, 0]], {
	            grid: {
	                borderColor: "#f3f3f3",
	                borderWidth: 1,
	                tickColor: "#f3f3f3"
	            },
	            series: {
	                shadowSize: 0, // Drawing is faster without shadows
	                color: "#3c8dbc"
	            },
	            lines: {
	                fill: true, //Converts the line chart to area chart
	                color: "#3c8dbc"
	            },
	            yaxis: {
	                min: 0,
	                max: 100,
	                show: true
	            },
	            xaxis: {
	            	min: 0,
	            	max: xmax,
	                show: true
	            }
	        });
        }
        
        function getMotionChart(xmax){
        	return $.plot("#motionChart", [[0, 0]], {
	            grid: {
	                borderColor: "#f3f3f3",
	                borderWidth: 1,
	                tickColor: "#f3f3f3"
	            },
	            series: {
	                shadowSize: 0, // Drawing is faster without shadows
	                color: "#3c8dbc"
	            },
	            lines: {
	                fill: true, //Converts the line chart to area chart
	                color: "#3c8dbc"
	            },
	            yaxis: {
	                min: 0,
	                max: 1,
	                show: true
	            },
	            xaxis: {
	            	min: 0,
	            	max: xmax,
	                show: true
	            }
	        });
        }
        
        function getBloodPressureChart(xmax){
        	var line1 = [[1,60], [2,50], [3,60], [4,60]];
        	var line2 = [[1,140], [2,150], [3,130], [4,160]];
        	return $.plot("#bloodPressureChart", [line1, line2], {
	            grid: {
	                borderColor: "#f3f3f3",
	                borderWidth: 1,
	                tickColor: "#f3f3f3"
	            },
	            series: {
	                shadowSize: 0, // Drawing is faster without shadows
	                color: "#3c8dbc"
	            },
	            lines: {
	                fill: true, //Converts the line chart to area chart
	                color: "#3c8dbc"
	            },
	            yaxis: {
	                min: 40,
	                max: 175,
	                show: true
	            },
	            xaxis: {
	            	min: 0,
	            	max: xmax,
	                show: true
	            },
	            legend:{ show: true }
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
