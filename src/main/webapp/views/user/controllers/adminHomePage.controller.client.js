(function() {

    angular
        .module("GetYourAppointment")
        .controller("AdminPageController",AdminPageController);

    function AdminPageController ($http,$location, $routeParams, $timeout, UserService, DoctorService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.newUser ={};
        vm.currentAppointments={};
        vm.doctors = [];
        vm.patients =[];
        vm.healthPersonnel =[];
        vm.plans=[];
        
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
                "userType": "healthPersonnel",
                "label": "HealthPersonnel"
            },
            {
                "userType": "admin",
                "label": "Admin"
            }
        ];


        function init() {
            var promise = UserService.findAdminById(vm.userId);
            promise.then(function (response) {
                vm.user = response.data;
                vm.updatedUser = response.data;
                vm.getAllAppointments();
                vm.getAllDoctors();
                vm.getAllPatients();
                vm.getAllHealthPersonnel();
                vm.getAllPlans();
            },function (error) {
                console.log(error);
            });
            vm.userType =  vm.userOptions[0];
        }

        init();


        vm.getAllAppointments= function(){
            var promise = UserService.findAllAppointment();
            promise.then(function (response) {
                vm.currentAppointments = response.data;
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };

        vm.createUser =  function () {
            var promise;
            if(vm.userType.userType == "patient"){
                promise = UserService.createUser(vm.newUser);
            } else if (vm.userType.userType == "doctor"){
                promise = UserService.createDoctor(vm.newUser);
            } else if(vm.userType.userType == "healthPersonnel") {
                vm.newUser.hprovider = vm.providerName;
                promise = UserService.createHealthPersonnel(vm.newUser);
            }  else if(vm.userType.userType == "admin") {
                vm.newUser.dtype= "admin";
                promise = UserService.createAdmin(vm.newUser);
            }
            promise.then(function(response) {
                console.log(response);
                vm.closeModal();
                $timeout(function () {
                }, 250);

            },function (err) {
                console.log(err);
            });
        };


        vm.updateUser = function(){
            var promise = UserService.updateAdminById(vm.user.id, vm.updatedUser);
            promise.then(function (response) {
                vm.closeModal();
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };
        
        vm.removeAppointment=function(id) {
        	var promise = UserService.removeAppointment(vm.userId, id);
        	promise.then(function(){
        		vm.getAllAppointments();
        	}, function(error) {
        		
        	})
        }

        vm.logout = function () {
            $location.url("/");
        };

        // Function for closing the modal
        vm.closeModal =  function () {
            $('.modal').modal('hide');
        }
        
        vm.getAllDoctors = function () {
        	var promise = UserService.getAllDoctors();
        	promise.then((res)=> {
        		vm.doctors = res.data;
        	})
        }
        
        vm.getAllPatients = function () {
        	var promise = UserService.getAllPatients();
        	promise.then((res)=> {
        		vm.patients = res.data;
        	})
        }
        
        vm.getAllHealthPersonnel = function () {
        	var promise = UserService.getAllHealthPersonnels();
        	promise.then((res)=> {
        		vm.healthPersonnel = res.data;
        	})
        }
        
        vm.getAllPlans = function() {
        	var promise = UserService.getAllPlans();
        	promise.then((res)=> {
        		vm.plans = res.data;
        	})
        }
        
        vm.removeDoctor = function(id) {
        	var promise = DoctorService.deleteDoctorById(id);
        	promise.then((res)=> {
        		vm.doctors = res.data;
        		vm.getAllAppointments();
        	})
        }
        
        vm.removePatient = function(id) {
        	var promise = UserService.deletePatientById(id);
        	promise.then((res)=> {
        		vm.patients = res.data;
        		vm.getAllAppointments();
        	})
        }
        
        vm.removeHealthPersonnel = function(id) {
        	var promise = UserService.deleteHealthPersonnelById(id);
        	promise.then((res)=> {
        		vm.healthPersonnel = res.data;
        	})
        }
        
        vm.removeHealthPlans = function(pid) {
        	var promise = UserService.deletePlanById(pid);
        	promise.then((res)=> {
        		vm.plans = res.data;
        	})       	
        }
        
        vm.removeAppointment = function(aid) {
        	var promise = UserService.deleteAppointmentById(aid);
        	promise.then(function(){
        		vm.getAllAppointments();
        	}, function(error) {
        		
        	})
        }
    }
})();