/** attach controllers to this module 
 * if you get 'unknown {x}Provider' errors from angular, be sure they are
 * properly referenced in one of the module dependencies in the array.
 * below, you can see we bring in our services and constants modules 
 * which avails each controller of, for example, the `config` constants object.
 **/
define([
	'/app/controllers/top.js',
	'/app/controllers/nav.js',
    '/app/controllers/realtime.js',
    '/app/controllers/history.js',
    '/app/controllers/login.js',
    '/app/controllers/users.js',
    '/app/controllers/user-form.js',
    '/app/controllers/bracelets.js',
    '/app/controllers/bracelet-form.js',
    '/app/controllers/codes.js',
    '/app/controllers/timeline.js'
], function () {});
