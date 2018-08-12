(function() {

    angular
        .module("GetYourAppointment")
        .controller("LandingPageController",LandingPageController);

    function LandingPageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.Doctor = {};
        vm.Specialty = {};
        vm.searchedDoctors = {};
        appointmentDoc = {}
        function init() {
        	UserService
            .test()
            .then(function () {
            	console.log("Call successfull")
            });
        }
        init();
        
        
        vm.searchDoctor = function() {
        	var promise = UserService.findDoctorByName(vm.Doctor.firstName, vm.Doctor.lastName);
            promise.then(function (response) {
            	vm.searchedDoctors = response.data;
            },function (error) {
                console.log(error);
            })
        }
        
        vm.redirectToSearchDoc = function(fName, lName){
        	for(d in vm.searchedDoctors) {
        		if(vm.searchedDoctors[d].fName === fName && vm.searchedDoctors[d].lName === lName){
        			appointmentDoc = vm.searchedDoctors[d];
        			break;
        		}
        	}
        	UserService.creatAppointmentDoctor(appointmentDoc);
        }
        
        
        vm.searchBySpecialty = function() {
        	console.log(vm.Specialty.name);
        	var promise = UserService.findDoctorBySpecialty(vm.Specialty.name);
            promise.then(function (response) {
            	vm.searchedDoctors = response.data;
            },function (error) {
                console.log(error);
            })
        }
        
        
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

        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();