(function() {

    angular
        .module("GetYourAppointment")
        .controller("healthPersonnelController",healthPersonnelController);

    function healthPersonnelController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.heathPersonnel ;
        vm.healthProvider;
        vm.plan;
        vm.doctors={};
        vm.patients={};

        function init() {
            var promise = UserService.findHealthPersonnelById(vm.userId);
            promise.then(function (response) {
                vm.heathPersonnel = response.data;
                vm.updatedUser = response.data;
                var promise1 = UserService.findHealthProviderById(vm.heathPersonnel.hprovider.id);
                promise1.then(function (response1) {
                    vm.healthProvider = response1.data;
                    var promise2 = UserService.findAllPlans(vm.heathPersonnel.id, vm.healthProvider.id);
                    promise2.then(function(resp) {
                    	vm.allPlansByPersonnel = resp.data;
                    	vm.findAllDoctorsInWithThisProvider();
                    	vm.findAllPatientsInThisProvider();
                    	console.log(vm.allPlansByPersonnel);
                    })
                    
                },function (error) {
                    console.log(error);
                })
            },function (error) {
                console.log(error);
            });
        }
        init();




        vm.createPlan = function () {
            var promise = UserService.addPlanToProvider(vm.plan,vm.healthProvider.id);
            promise.then(function (res) {
                vm.healthProvider = res.data;
                var promise2 = UserService.findAllPlans(vm.heathPersonnel.id, vm.healthProvider.id);
                promise2.then(function(resp) {
                	vm.allPlansByPersonnel = resp.data;
                	console.log(vm.allPlansByPersonnel);
                	closeModal();
                })
                $timeout(function () {
                }, 250);
            },function (error) {
                console.log("error");
            })
        };


        vm.updateUser = function(){
            var promise = UserService.updateHealthPersonnelById(vm.heathPersonnel.id, vm.updatedUser);
            promise.then(function (response) {
                closeModal();
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };
        
        vm.removePlan = function(pid){
        	var promise = UserService.deletePlanById(pid);
        	promise.then((res)=> {
        		init();
        	})  
        }

        vm.redirectToFollowersFollowing = function(){
            $location.url("/user/FollowPage/"+vm.userId);
        };

        vm.logout = function () {
            $location.url("/");
        };
        
        vm.findAllDoctorsInWithThisProvider = function() {
        	var promise = UserService.findAllDoctorsInThisProvider(vm.userId);
        	promise.then((response) => {     		
        		vm.doctors = response.data;
        	})
        }
        
        vm.findAllPatientsInThisProvider = function () {
        	var promise = UserService.findAllPatientsInThisProvider(vm.userId);
        	promise.then((response) => {     		
        		vm.patients = response.data;
        		console.log(vm.patients);
        	})
        }
        
        vm.removeDoctorFromPlan = function(docid, planName) {
        	var promise = UserService.removeDoctorFromPlan(docid, planName);
        	promise.then((response)=>{
        		vm.findAllDoctorsInWithThisProvider();
        	})
        }
        
        
        vm.removePatientFromPlan = function(patid, planName) {
        	var promise = UserService.removePatientFromPlan(patid, planName);
        	promise.then((response)=>{
        		vm.findAllPatientsInThisProvider();
        	})
        }

        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();