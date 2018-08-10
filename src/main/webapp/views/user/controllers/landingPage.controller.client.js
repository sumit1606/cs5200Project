(function() {

    angular
        .module("GetYourAppointment")
        .controller("LandingPageController",LandingPageController);

    function LandingPageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.user = {};
  
        function init() {
        	UserService
            .test()
            .then(function () {
            	console.log("Call successfull")
            });
        }
        init();
        
        
        vm.login = function() {
//            var promise = UserService.findUserByCredentials(vm.user.emailAddress, vm.user.password);
//            promise.then(function (response) {
//                 $window.sessionStorage.token = response.data.token;
//                 // you can use the below code to store data in the local storage rather the session storage
//                 // $localStorage.currentUser = {email :vm.user.emailAddress, token: response.data.token};
//                closeModal();
//                // TODO getting the UserId from the response
//                $timeout(function () {
//                    $location.url("/user/profilePage/"+ response.data.email);
//                }, 350);
//            },function (error) {
//                console.log(error);
//            })
         };

//        function createUser () {
//            var promise = UserService.createUser(vm.user);
//            promise.then(function (response) {
//                closeModal();
//                // TODO getting the UserId from the response
//                $timeout(function () {
//                        $location.url("/user/profilePage/"+ response.data.email);
//                }, 350);
//            },function (error) {
//                console.log(error);
//            })
//        };

        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();