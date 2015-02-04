define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("Charts", function($http, $rootScope, $scope, $translate) {
    	// get DateTime yyyyMMddHHmmss
	    var getDateTime = function(){
	    	var dd = new Date()
	    	var val = '' + dd.getFullYear() 
	    				+ (dd.getMonth() < 9 ? '0' + (dd.getMonth()+1) : (dd.getMonth()+1)) 
	    				+ (dd.getDate() < 9 ? '0' + (dd.getDate()) : (dd.getDate())) 
	    				+ (dd.getHours() < 10 ? '0' + dd.getHours() : dd.getHours())
	    				+ (dd.getMinutes() < 10 ? '0' + dd.getMinutes() : dd.getMinutes())
	    				+ (dd.getSeconds() < 10 ? '0' + dd.getSeconds() : dd.getSeconds())
	       return val;
	    }
	    
	    // Report URL
	    var braceletId = $("#braceletId").val();
	    $rootScope.url = '/api/findDashboardList/' + braceletId + '/' + getDateTime();
	    $rootScope.method = 'GET';
	    
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
	    var flag = 1;
	    
	    $scope.switchTab = function(chart){
	    	if("temperatureChart" == chart){
	    		flag = 1;
	    	}else if("plusChart" == chart){
	    		flag = 2;
	    	}else if("motionChart" == chart){
	    		flag = 3;
	    	}
	    	update();
	    };
	    
        var updateInterval = 5000; //Fetch data ever x milliseconds
        var realtime = "on"; //If == to on then fetch data every x seconds. else stop fetching
        function update() {
        	// We use an inline data source in the example, usually data would
            // be fetched from a server
        	$rootScope.url = '/api/findDashboardList/' + braceletId + '/' + getDateTime();
        	$rootScope.template(function(data, status) {
  		    	var temperatureDatas = [];
  		    	var plusDatas = [];
  		    	var motionDatas = [];
	          	if(data){
	          		var list = data.datas;
	          		$.each( list, function( key, value ) {
	          			temperatureDatas.push([key, value.temperature]);
	          			plusDatas.push([key, value.pulseState]);
	          			motionDatas.push([key, value.motionState]);
		          	});
	          	}
	         
	          	if(flag == 1){
	          		temperatureChart = getTemperatureChart();
	          		temperatureChart.setData([temperatureDatas]);
	          		temperatureChart.draw();
	          	}else if(flag == 2){
	          		plusChart = getPlusChart();
	          		plusChart.setData([plusDatas]);
	          		plusChart.draw();
	          	}else if(flag == 3){
	          		motionChart = getMotionChart();
	          		motionChart.setData([motionDatas]);
	          		motionChart.draw();
	          	}
	          	
	          	if (realtime === "on"){
	          		setTimeout(update, updateInterval);
	          	}
	    	});
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
        
        function getTemperatureChart(){
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
	            	max: 29,
	                show: true
	            }
	        });
        }
        
        function getPlusChart(){
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
	            	max: 29,
	                show: true
	            }
	        });
        }
        
        function getMotionChart(){
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
	            	max: 29,
	                show: true
	            }
	        });
        }
        
	})
});
