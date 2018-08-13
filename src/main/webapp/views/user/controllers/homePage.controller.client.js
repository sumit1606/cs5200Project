(function() {

    angular
        .module("GetYourAppointment")
        .controller("HomePageController",HomePageController);

    function HomePageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.Doctor = {};
        vm.Specialty = {};
        vm.searchedDoctors = {};
        appointmentDoc = {}
        function init() {

        }
        init();


        vm.searchDoctor = function() {
            var promise = UserService.findDoctorByName(vm.Doctor.firstName, vm.Doctor.lastName);
            promise.then(function (response) {
                vm.searchedDoctors = response.data;
            },function (error) {
                console.log(error);
            })
        };

        vm.redirectToSearchDoc = function(fName, lName){
            for(d in vm.searchedDoctors) {
                if(vm.searchedDoctors[d].fName === fName && vm.searchedDoctors[d].lName === lName){
                    appointmentDoc = vm.searchedDoctors[d];
                    break;
                }
            }
            UserService.creatAppointmentDoctor(appointmentDoc);
        };


        vm.searchBySpecialty = function() {
            console.log(vm.Specialty.name);
            var promise = UserService.findDoctorBySpecialty(vm.Specialty.name);
            promise.then(function (response) {
                vm.searchedDoctors = response.data;
            },function (error) {
                console.log(error);
            })
        };


        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();