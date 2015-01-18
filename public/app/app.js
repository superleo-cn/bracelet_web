define([
    'angular',
    'uiRouter',
    'controllers'
], function (ng) {
    'use strict';

    return ng.module('app', [
		'app.controllers',
		'ui.router'
    ]);
});