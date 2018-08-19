(function() {

    angular
        .module("GetYourAppointment")
        .controller("DoctorHomePageController",DoctorHomePageController);

    function DoctorHomePageController ($http,$location, $routeParams, $timeout, DoctorService, $window) {
   
        var vm = this;
        vm.userId = $routeParams.uidS;
        vm.currentAppointments = {};
        vm.plansTaken = [];
        vm.doc;

        
        function init() {
        	var promise = DoctorService.findDoctorById(vm.userId);
        	promise.then((response) => {
        		vm.doc = response.data;
                vm.updatedUser = response.data;
        		vm.getAllAppointments();
        		vm.plansTakenByDoc(vm.userId );
                vm.getAllPlans();
                vm.findAllBlogs();
        	})
        }

        init();

        vm.getAllAppointments= function(){
            // Getting all the appointments for the user
            var promise1 = DoctorService.findAppointmentsForDoctor(vm.userId);
            promise1.then(function (response) {
                vm.currentAppointments = response.data;
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };


        vm.removeAppointment = function(key) {
        	var promise = DoctorService.removeAppointment(vm.userId, key);
        	promise.then(function(){
        		vm.getAllAppointments();
        	}, function(error) {
        		
        	})

        }
        
        vm.plansTakenByDoc = function(id) {
        	var promise = DoctorService.getAllPlanforDoctor(vm.userId);
        	promise.then((res)=>{
        		vm.plansTaken = res.data;
        	})
        	
        }

        vm.deletePlan= function(id) {
        	var promise = DoctorService.deletePlanFromDoctor(vm.userId, id);
        	promise.then((res)=>{
        		vm.plansTaken = res.data.plansSupported;
        	})
        }
        
        vm.addPlanToSupported = function(pid) {
        	var promise = DoctorService.addPlanToSupported(vm.userId, pid);
        	promise.then((res)=>{
        		vm.plansTaken = res.data.plansSupported;
        	})
        }

        vm.getAllPlans=function() {
        	var promise = DoctorService.getAllPlans();
        	promise.then((res)=>{
        		vm.allPlans = res.data;
        	})
        }

        vm.updateUser = function () {
            var promise = DoctorService.updateDoctorById(vm.doc.id, vm.updatedUser);
            promise.then(function (response) {
                closeModal();
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };

        vm.logout = function () {
            $location.url("/");
        };

        vm.createBlog =  function(){
            var promise = DoctorService.addBlogToDoctor(vm.doc.id, vm.currentBlog);
            promise.then(function (response) {
                closeModal();
                console.log(response);
                vm.findAllBlogs();
            },function (error) {
                console.log(error);
            })
        };

        vm.findAllBlogs =  function(){
            var promise = DoctorService.findBlogsByDoctor(vm.doc.id);
            promise.then(function (response) {
                vm.allBlogs = response.data;
                console.log(response);
            },function (error) {
                console.log(error);
            })
        };

        vm.deleteBlog = function(blogId) {
            var promise = DoctorService.deleteBlogById(blogId);
            promise.then(function (response) {
                vm.findAllBlogs();
                init();
            },function (error) {
                console.log(error);
            });
        };

        vm.editBlog = function(blog){
            vm.updatedBlog = blog;
        };

        vm.updateBlog = function () {
            var promise = DoctorService.updateBlog(vm.updatedBlog);
            promise.then(function (response) {
                closeModal();
                vm.findAllBlogs();
                init();
            },function (error) {
                console.log(error);
            });
        };


        vm.redirectToFollowersFollowing = function(){
            $location.url("/user/FollowPage/"+vm.userId);
        };


        // Function for closing the modal
        function closeModal() {
            $('.modal').modal('hide');
        }
    }
})();