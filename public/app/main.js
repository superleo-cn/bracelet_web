// require JS config
require.config({
	baseUrl : '/assets/libs/',
	maps : {

	},
	paths : {
		'domReady' : '/assets/libs/domReady-2.0.1',
		'jquery' : "/assets/libs/jquery-1.11.1.min",
		'angular' : '/assets/libs/angular-1.3.7.min',
		'ngRoute' : '/assets/libs/angular-route-1.3.7.min',
		'ngResource' : '/assets/libs/angular-resource-1.3.7.min',
		'uiRoute' : '/assets/libs/angular-ui-router-0.2.13.min',
		'flot' : '/assets/libs/plugins/flot/jquery.flot.min',
		'resize' : '/assets/libs/plugins/flot/jquery.flot.resize.min',
		'pie' : '/assets/libs/plugins/flot/jquery.flot.pie.min',
		'categories' : '/assets/libs/plugins/flot/jquery.flot.categories.min',
	},
	shim : {
		'angular': {
            //The underscore script dependency should be loaded before loading backbone.js
            deps: ['jquery'],
            // use the global 'Backbone' as the module name.
            exports: 'angular'
        },
        'ngRoute': {
            deps: ['jquery', 'angular'],
            exports: 'ngRoute'
        },
        'ngResource': {
            deps: ['jquery', 'angular'],
            exports: 'ngResource'
        },
        'uiRoute': {
            deps: ['jquery', 'angular'],
            exports: 'uiRoute'
        },
        'flot': {
            deps: ['jquery'],
            exports: 'flot'
        },
        'resize': {
            deps: ['jquery', 'flot'],
            exports: 'resize'
        },
        'pie': {
            deps: ['jquery', 'flot'],
            exports: 'pie'
        },
        'categories': {
            deps: ['jquery', 'flot'],
            exports: 'categories'
        }
	}
});

require(['jquery', 'angular', 'uiRoute', 'domReady', 'flot'], function($, angular, uiRoute, domReady, flot) {

	console.log('Initialize App ' + angular);

	angular.module('myApp', ['ui.router'])
	.config(function($stateProvider, $urlRouterProvider) {
		
		// For any unmatched url, redirect to /state1
		$urlRouterProvider.otherwise("/dashboard");
		//
		// Now set up the states
		$stateProvider
	  	.state('dashboard', {
	  		url: "/dashboard",
	  		templateUrl: "assets/app/templates/dashboard.html",
	  		controller: function($http, $scope) {
	  			$scope.method = 'GET';
	  		    $scope.url = '/api/findByDate/20150101000000';
	  		    
	  		    // My Ajax Request Template
	  		    var requestTemplate = function(operation) {
	  		    	$scope.code = null;
	  		    	$scope.response = null;
	  		    	$scope.params = null;
	  		    	
	  		    	$http({
	  		    		method: $scope.method, 
	  		    		url: $scope.url, 
	  		    		params: $scope.params
	  		    	}).success(operation)
	  		    	.error(function(data, status) {
	  		          	$scope.data = data || "Request failed";
	  		          	$scope.status = status;
	  		    	});
	  		    };
	  		    
	  			/*
                 * Flot Interactive Chart
                 * -----------------------
                 */
                var interactive_plot = $.plot("#interactive", [[0, 0]], {
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
                
                var updateInterval = 5000; //Fetch data ever x milliseconds
                var realtime = "on"; //If == to on then fetch data every x seconds. else stop fetching
                function update() {
                	// We use an inline data source in the example, usually data would
                    // be fetched from a server
                	requestTemplate(function(data, status) {
     	  		    	var datas = [];
     		          	if(data){
     		          		var list = data.datas;
     		          		$.each( list, function( key, value ) {
     			          		datas.push([key, value.temperature])
     			          	});
     		          	}
     		         
     		          	interactive_plot.setData([datas]);

     		          	// Since the axes don't change, we don't need to call plot.setupGrid()
     		          	interactive_plot.draw();
     		          	if (realtime === "on"){
     		          		setTimeout(update, updateInterval);
     		          	}
     		    	});
                }

                //INITIALIZE REALTIME DATA FETCHING
                if (realtime === "on") {
                    update();
                }
                //REALTIME TOGGLE
                $("#realtime .btn").click(function() {
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
	  		}
	    })
	    .state('about', {
	    	url: "/about",
	    	templateUrl: "assets/app/templates/about.html"
	    })
	});
	
	angular.element(document).ready(function() {
		angular.bootstrap(document, ['myApp']);
    });
	
});