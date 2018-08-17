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

        vm.userOptions = [
            {
                "userType": "patient",
                "label": "Patient"
            },
            {
                "userType": "doctor",
                "label": "Doctor"
            },
            {
                "userType": "healthpersonnel",
                "label": "HealthPersonnel"
            }
        ];
       
        vm.displaySearchByName;
        
        appointmentDoc = {}
        
        function init() {
            vm.userType =  vm.userOptions[0];
        }
        init();
        
        
        vm.searchDoctor = function() {
        	vm.searchedDoctors = {};
        	vm.displaySearchByName = true;
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

        vm.createUser =  function () {
            var promise;
            if(vm.userType.userType == "patient"){
                 promise = UserService.createUser(vm.user);
            } else if (vm.userType.userType == "doctor"){
                 promise = UserService.createDoctor(vm.user);
            } else if(vm.userType.userType == "healthPersonnel") {
                promise = UserService.createHealthPersonnel(vm.user);
            }
                promise.then(function(response) {
                    console.log(response);
                    var id = response.data.id;
                    closeModal();
                    $timeout(function () {
                        if(vm.userType.userType === "patient"){
                            $location.url("/user/patientHomePage/"+id);
                        } else if (vm.userType.userType === "doctor"){
                            $location.url("/user/DoctorHomePage/"+id);
                        } else if(vm.userType.userType === "healthPersonnel") {
                            $location.url("/user/healthPersonnelHomePage/"+id);
                        }
                    }, 250);

                },function (err) {
                    console.log(err);
                    if (err && err==='"Unauthorized"') {
                        vm.error = "Cannot login! Please Check Username and password";
                    }else {
                        vm.error = "Some Error occurred! Please try again.";
                    }
                });

        };



        vm.login = function() {
            var promise = UserService.findUserByCredentials(vm.user.emailAddress, vm.user.password);
            promise.then(function (response) {
                closeModalLogin();
                console.log(response.data);
                signedUser = response.data

                $timeout(function () {
                    if(signedUser.dtype === "patient"){
                        $location.url("/user/patientHomePage/"+signedUser.id);
                    } else if (signedUser.dtype === "doctor"){
                        $location.url("/user/DoctorHomePage/"+signedUser.id);
                    } else if(signedUser.dtype === "healthPersonnel") {
                        $location.url("/user/healthPersonnelHomePage/"+signedUser.id);
                    }
                }, 350);
            },function (error) {
                console.log(error);
            })

         };

        // Function for closing the modal
        function closeModal() {
            $('#myModalSignup').modal('hide');
        }

        function closeModalLogin() {
            $('#myModal').modal('hide');
        }
    }
})();