(function() {

    angular
        .module("GetYourAppointment")
        .controller("FollowPageController",FollowPageController);

    function FollowPageController ($http,$location, $routeParams, $timeout, UserService, DoctorService, $window) {

        var vm = this;
        vm.userId = $routeParams.uidS;
        function init() {
            var promise = UserService.findPersonById(vm.userId);
            promise.then(function (response) {
                person = response.data;
                vm.followingDoctors = person.follows;
                allFollowedDoctorsBlog();
            },function (error) {
                console.log(error);
            });
        }

        init();


        vm.searchDoctor = function() {
            var promise = DoctorService.getDoctorByName(vm.Doctor.firstName, vm.Doctor.lastName);
            promise.then(function (response) {
                vm.searchedDoctors = response.data;
            },function (error) {
                console.log(error);
            });
        };


        vm.followDoctor = function(docId) {
            var promise = DoctorService.followDoctorById(docId,vm.userId);
            promise.then(function (response) {
                vm.searchedDoctors = [];
                vm.Doctor = {};
                init();
            },function (error) {
                console.log(error);
            });
        };

        vm.unfollowDoctor = function(docId) {
            var promise = DoctorService.unfollowDoctorById(docId,vm.userId);
            promise.then(function (response) {
                vm.searchedDoctors = [];
                vm.Doctor = {};
                init();
            },function (error) {
                console.log(error);
            });
        };

      function allFollowedDoctorsBlog(){
            var promise = UserService.getAllBlogs(vm.userId);
            promise.then(function (response) {
                vm.followedBlogs = response.data;
            },function (error) {
                console.log(error);
            })

        }



        vm.logout = function () {
            $location.url("/");
        };

        // Function for closing the modal
        vm.closeModal =  function () {
            $('.modal').modal('hide');
        };

    }
})();