(function() {

    angular
        .module("GetYourAppointment")
        .controller("AdminPageController",AdminPageController);

    function AdminPageController ($http,$location, $routeParams, $timeout, UserService, $window) {
        // By default we will be handling all the promise using than
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.newUser ={};
        vm.currentAppointments={};
   
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
                // vm.getAllAppointments();
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
    }
})();