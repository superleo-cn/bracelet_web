// require JS config
require.config({
	baseUrl : '/assets/libs/',
	maps : {

	},
	paths : {
		'domReady' : '/libs/domReady-2.0.1',
		'jquery' : "/libs/jquery-1.11.1.min",
		'jquery.cookie' : "/libs/jquery.cookie-1.4.1",
		'angular' : '/libs/angular-1.3.7.min',
		'ngRoute' : '/libs/angular-route-1.3.7.min',
		'ngResource' : '/libs/angular-resource-1.3.7.min',
		'uiRouter' : '/libs/angular-ui-router-0.2.13.min',
		'translate' : '/libs/angular-translate-2.5.2.min',
		'ngCookies' : '/libs/angular-cookies-1.3.7.min',
		'loaderStaticFiles' : '/libs/angular-translate-loader-static-files-2.5.2.min',
		'storagekey' : '/libs/angular-translate-storage-key-2.5.2',
		'storageLocal' : '/libs/angular-storage-local-2.5.2',
		'storageCookie' : '/libs/angular-translate-storage-cookie-2.5.2',
		'jquery.flot' : '/libs/plugins/flot/jquery.flot.min',
		'resize' : '/libs/plugins/flot/jquery.flot.resize.min',
		'pie' : '/libs/plugins/flot/jquery.flot.pie.min',
		'pagination' : '/libs/plugins/pagination/jquery.simplePagination',
		'categories' : '/libs/plugins/flot/jquery.flot.categories.min',
        'highcharts' : '/libs/plugins/highcharts/highcharts',
        'highstock' : '/libs/plugins/highcharts/highstock',
		'app' : '/app/app',
		'routes' : '/app/routes',
		'controllers' : '/app/controllers/index',
		'directives' : '/app/directives/index',
		'filters' : '/app/filters/index',
		'services' : '/app/services/index',
	},
	shim : {
		'jquery': {
            exports: '$'
        },
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
        'ngCookies': {
            deps: ['jquery', 'angular'],
            exports: 'ngCookies'
        },
        'uiRouter': {
            deps: ['jquery', 'angular'],
            exports: 'uiRouter'
        },
        'translate': {
            deps: ['jquery', 'angular'],
            exports: 'translate'
        },
        'loaderStaticFiles': {
            deps: ['translate'],
            exports: 'loaderStaticFiles'
        },
        'storagekey': {
            deps: ['ngCookies', 'translate'],
            exports: 'storagekey'
        },
        'storageLocal': {
            deps: ['ngCookies', 'translate'],
            exports: 'storageLocal'
        },
        'storageCookie': {
            deps: ['ngCookies', 'translate'],
            exports: 'storageCookie'
        },
        'jquery.flot': {
            deps: ['jquery', 'angular'],
            exports: '$.plot'
        },
        highcharts: {
            deps: ['jquery', 'angular'],
            exports: "highcharts"
        },
        highstock: {
            deps: ['jquery', 'angular'],
            exports: "highstock"
        }
	},
	deps: [
        // kick start application... see bootstrap.js
	    '/app/bootstrap.js'
	]
});
