define(['/app/controllers/module.js'], function (controllers) {
	'use strict';
    controllers.controller("History", function($http, $rootScope, $scope, $translate) {
    	// get DateTime yyyyMMddHHmmss
	    var getDateTime = function(){
	    	var dd = new Date()
	    	var val = '' + dd.getFullYear() 
	    				+ (dd.getMonth() < 9 ? '0' + (dd.getMonth()+1) : (dd.getMonth()+1)) 
	    				+ (dd.getDate() < 10 ? '0' + (dd.getDate()) : (dd.getDate()))
	       return val;
	    }
	    
	    // Report URL
	    var braceletId = $("#braceletId").val();
	    $rootScope.url = '/api/findHistoryList/' + braceletId + '/day/' + getDateTime();
	    $rootScope.method = 'GET';
	    
	    
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
	    	update("day");
	    };
	    
	    $scope.swithType = function(type){
	    	update(type);
	    };
	    
        function update(type) {
        	// We use an inline data source in the example, usually data would
            // be fetched from a server
        	$rootScope.url = '/api/findHistoryList/' + braceletId + '/' + type + '/' + getDateTime();
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
	          	}
	          	
	    	});
        };
        
        update("day");
      
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
	                min: 30,
	                max: 45,
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
        
        $scope.$on('$viewContentLoaded', function() {

        });
        
        $scope.$on('$destroy', function() {

        })
        
	})
});
