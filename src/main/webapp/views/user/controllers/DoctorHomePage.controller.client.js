(function() {

    angular
        .module("GetYourAppointment")
        .controller("DoctorHomePageController",DoctorHomePageController);

    function DoctorHomePageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;

        function init() {

        }
        init();

        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();