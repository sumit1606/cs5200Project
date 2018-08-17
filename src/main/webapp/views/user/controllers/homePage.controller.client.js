(function() {

    angular
        .module("GetYourAppointment")
        .controller("HomePageController",HomePageController);

    function HomePageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.Doctor = {};
        var patient;
        vm.Specialty = {};
        vm.searchedDoctors = {};
        vm.date = {};
        vm.appointmentDoc = {};
        vm.displaySearchByName;
        
        function init() {
            var promise = UserService.findPatientById(vm.userId);
            promise.then(function (response) {
                patient = response.data;

            },function (error) {
                console.log(error);
            })
        }

        init();


        vm.selectDoctorForAppointment = function(fName, lName){
            console.log(fName);
            console.log(lName);
            for(d in vm.searchedDoctors) {
                 if(vm.searchedDoctors[d].fName === fName && vm.searchedDoctors[d].lName === lName){
                     vm.appointmentDoc = vm.searchedDoctors[d];
                     break;
                 }
             }
        };

        vm.bookAppointment = function(){
            appointment = {};
            console.log(vm.appointmentDoc);
            appointment.doctor = vm.appointmentDoc;
            appointment.patient = patient;
            var promise = UserService.createAppointment(patient.id, appointment);
            promise.then(function (response) {
               var apt = response;
               console.log(apt);

            },function (error) {
                console.log(error);
            })
        };

        vm.searchDoctor = function() {
        	vm.searchedDoctors = {};
        	vm.displaySearchByName = true;
            var promise = UserService.findDoctorByName(vm.Doctor.firstName, vm.Doctor.lastName);
            promise.then(function (response) {
                vm.searchedDoctors = response.data;
                console.log(vm.searchedDoctors);
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
        	vm.searchedDoctors = {};
        	vm.displaySearchByName = false;
            console.log(vm.Specialty.name);
            var promise = UserService.findDoctorBySpecialty(vm.Specialty.name);
            promise.then(function (response) {
                vm.searchedDoctors = response.data;
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