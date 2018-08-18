(function() {

    angular
        .module("GetYourAppointment")
        .controller("AdminPageController",AdminPageController);

    function AdminPageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;

        function init() {
            var promise = UserService.findPatientById(vm.userId);
            promise.then(function (response) {
                vm.getAllAppointments();
            },function (error) {
                console.log(error);
            });
        }

        init();


        vm.getAllAppointments= function(){
            // Getting all the appointments for the user
            var promise1 = UserService.findAppointmentsForPatient(vm.userId);
            promise1.then(function (response) {
                vm.currentAppointments = response.data;
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };

        // Function for closing the modal
        vm.closeModal =  function () {
            $('.modal').modal('hide');
        }
    }
})();