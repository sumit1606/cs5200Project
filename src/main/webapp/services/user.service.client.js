/**
 * Created by sumitbhanwala
 */
(function () {
    angular
        .module("GetYourAppointment")
        .factory("UserService" ,userService)

    function userService($http) {
        var api = {
        	"test": test,
            "getUserByEmail": getUserByEmail,
            "findUserByCredentials": findUserByCredentials,
            "findDoctorByName": findDoctorByName,
            "findDoctorBySpecialty":findDoctorBySpecialty,
            "createAppointment":createAppointment,
            "createUser":createUser,
            "createDoctor":createDoctor,
            "createHealthPersonnel":createHealthPersonnel,
            "findPatientById":findPatientById,
            "findHealthPersonnelById":findHealthPersonnelById,
            "findAppointmentsForPatient":findAppointmentsForPatient,
            "findHealthProviderById":findHealthProviderById,
            "addPlanToProvider":addPlanToProvider,
            "findAppointmentsForPatient":findAppointmentsForPatient,
            "removeAppointment":removeAppointment,
            "deletePlanById":deletePlanById,
            "createAdmin":createAdmin,
            "findAllPlans":findAllPlans
        };
        return api;

        function test() {
        	return $http.get("/api/user/test");
        }

        // adding trailing slash as spring is truncating the part after.
        function getUserByEmail(email) {
            
        }

        function deletePlanById(hpid, pid) {
            return $http({
                url: "api/healthPersonnel/"+hpid+"/plan/"+pid,
                method: "DELETE"
            });

        }

        function addPlanToProvider(plan,providerId) {
            return $http.post("/api/healthProvider/"+providerId+"/plan",plan)
        }


        function findHealthPersonnelById(id) {
            return $http.get("api/healthPersonnel/"+id);
        }

        function findHealthProviderById(id) {
            return $http.get("api/healthProvider/"+id);
        }

        function findPatientById(id) {
            return $http.get("api/patient/"+id);
        }

        function createUser(user) {
            return $http.post("/api/user", user);
        }

        function createAdmin(admin) {
            return $http.post("/api/admin", admin);
        }

        function createDoctor(doctor) {
            return $http.post("/api/doctor", doctor);
        }

        function createHealthPersonnel(healthPersonnel) {
            return $http.post("/api/healthPersonnel", healthPersonnel);
        }

        
        function createAppointment(patientId, appointment) {
        	return $http.post("/api/patient/"+patientId+"/appointment", appointment);
        }

        function findUserByCredentials(emailAddress , password) {
            var person ={
                email: emailAddress,
                password: password
            };
            return $http.post("/api/user/login",person);
        }
        
        function findDoctorByName(firstName , lastName) {
        	return $http({
        	    url: "api/doctor/name", 
        	    method: "GET",
        	    params: {fName: firstName , lName: lastName}
        	 });
        }
        
        
        function findDoctorBySpecialty(name){
        	return $http({
        	    url: "api/doctor/specialty", 
        	    method: "GET",
        	    params: {name: name }
        	 });
        }
        
        function findAppointmentsForPatient(id) {
        	return $http({
        	    url: "api/patient/"+id+"/appointments", 
        	    method: "GET"
        	 });
        }
        
        function removeAppointment(id, key) {
        	return $http({
        		url: "api/patient/"+id+"/appointments/"+key,
        		method: "DELETE"
        	});
        }
        
        function findAllPlans(id, proId) {
         	return $http({
        	    url: "api/healthPersonnel/"+id+"/provider/"+proId+"/plans", 
        	    method: "GET"
        	 });
        }
        
    };
    
    
})();