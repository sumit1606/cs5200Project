(function() {
    angular
        .module("GetYourAppointment")
        .factory('authInterceptor' , authInterceptor)
        .config(configuration);

    function authInterceptor($q, $window) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                if ($window.sessionStorage.token) {
                    config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
                }
                return config;
            },
            responseError: function (rejection) {
                if (rejection.status === 401) {
                    // handle the case where the user is not authenticated
                }
                return $q.reject(rejection);
            }
        };
    }

    function configuration($routeProvider,$httpProvider,$compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';
        $httpProvider.interceptors.push('authInterceptor');
        $routeProvider
        	.when("/", {
            templateUrl:"./views/user/templates/landingPage.view.client.html",
            controller:"LandingPageController",
            controllerAs:"model",
        	}).otherwise({
            redirectTo:"/"
        });
    }

})();