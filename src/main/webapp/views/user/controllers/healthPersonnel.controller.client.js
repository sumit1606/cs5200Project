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

        function init() {
            var promise = UserService.findHealthPersonnelById(vm.userId);
            promise.then(function (response) {
                vm.heathPersonnel = response.data;
                var promise1 = UserService.findHealthProviderById(vm.heathPersonnel.hprovider.id);
                promise1.then(function (response1) {
                    vm.healthProvider = response1.data;
                    vm.allPlansByPersonnel =vm.healthProvider.plans;
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
                vm.allPlansByPersonnel =vm.healthProvider.plans;
                closeModal();
                $timeout(function () {
                }, 250);
            },function (error) {
                console.log("error");
            })
        };

        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();