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
        vm.appointmentDoc = {};
        vm.displaySearchByName;
        vm.currentAppointments={};
        vm.date = new Date("2018-08-20");
        vm.plansTaken=null;
        vm.planPresent=false;
        vm.allPlans=[];
        vm.tempp;
        
        function init() {
            var promise = UserService.findPatientById(vm.userId);
            promise.then(function (response) {
                patient = response.data;
                vm.updatedUser = response.data;
                vm.getAllAppointments();
                vm.plansTakenByPat(vm.userId );
                vm.getAllPlans();
                
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


        vm.selectDoctorForAppointment = function(fName, lName){
            for(d in vm.searchedDoctors) {
                 if(vm.searchedDoctors[d].fName === fName && vm.searchedDoctors[d].lName === lName){
                     vm.appointmentDoc = vm.searchedDoctors[d];
                     break;
                 }
             }
        };

        vm.bookAppointment = function(){
            appointment = {};
            appointment.doctor = vm.appointmentDoc;
            appointment.patient = patient;
            appointment.date = vm.date;
            appointment.reason = vm.reason;
            var promise = UserService.createAppointment(patient.id, appointment);

            promise.then(function (response) {
            	vm.closeModal();
                $timeout(function () {
                	vm.getAllAppointments();
                	vm.getAllPlans();
                }, 250);
                

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


        vm.removeAppointment = function(key) {
        	var promise = UserService.removeAppointment(vm.userId, key);
        	promise.then(function(){
        		vm.getAllAppointments();
        	}, function(error) {
        		
        	})

        }
        vm.plansTakenByPat = function(id) {
        	var promise = UserService.getAllPlanforPatient(id);
        	promise.then((res)=>{
        		if (res.data.name.length>0){
        			vm.plansTaken = res.data;
        			vm.planPresent=true;
        		}
        			
        	})
        	
        }
        
        vm.deletePlan= function(id) {
        	vm.planPresent=false;
        	vm.plansTaken==null;
        	var promise = UserService.deletePlanFromPatient(vm.userId, id);
        	promise.then((res)=>{
        		
        	})
        }
        
        
        vm.getAllPlans=function() {
        	var promise = UserService.getAllPlans();
        	promise.then((res)=>{
        		vm.allPlans = res.data;
        	})
        }
        
        
        vm.replacePlan = function(pid) {
        	vm.planPresent=false;
        	vm.plansTaken==null;
        	var promise = UserService.replacePlan(vm.userId, pid);
        	promise.then((res)=>{
        		if (res.data.healthInsurancePlan.name.length>0){
        			vm.plansTaken = res.data.healthInsurancePlan;
        			vm.planPresent=true;
        		}
        	})
        }
        
        vm.updateUser = function(){
            var promise = UserService.updatePatientById(vm.userId, vm.updatedUser);
            promise.then(function (response) {
                vm.closeModal();
            },function (error) {
                console.log(error);
            })
        };

        
        // Function for closing the modal
        vm.closeModal =  function () {
            $('.modal').modal('hide');
        }
        
    };


})();