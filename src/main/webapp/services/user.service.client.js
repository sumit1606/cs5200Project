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
            "removeAppointment":removeAppointment,
            "deletePlanById":deletePlanById,
            "findAllPlans":findAllPlans,
            "getAllPlanforPatient":getAllPlanforPatient,
            "deletePlanFromPatient":deletePlanFromPatient,
            "getAllPlans":getAllPlans,
            "replacePlan":replacePlan,
            "createAdmin":createAdmin,
            "findAllAppointment":findAllAppointment,
            "findAdminById":findAdminById,
            "updateAdminById":updateAdminById,
            "updateHealthPersonnelById":updateHealthPersonnelById,
            "updatePatientById":updatePatientById,
            "getAllDoctors":getAllDoctors,
            "getAllHealthPersonnels":getAllHealthPersonnels,
            "getAllPatients":getAllPatients,
            "getAllPlans":getAllPlans,
            "findPersonById":findPersonById,
            "getAllBlogs":getAllBlogs,
            "deletePatientById":deletePatientById,
            "deleteHealthPersonnelById": deleteHealthPersonnelById,
            "deletePlanById":deletePlanById,
            "getAllHealthProviders":getAllHealthProviders,
            "removeHealthProvider":removeHealthProvider,
            "getAllBlogs":getAllBlogs,
            "removeBlog": removeBlog,
            "findAllDoctorsInThisProvider":findAllDoctorsInThisProvider,
            "findAllPatientsInThisProvider":findAllPatientsInThisProvider,
            "removeDoctorFromPlan":removeDoctorFromPlan,
            "removePatientFromPlan":removePatientFromPlan
        };
        return api;

        function test() {
        	return $http.get("/api/user/test");
        }

        function getAllBlogs(pid) {
            return $http.get("/api/person/"+pid+"/blogs");
        }

        function findPersonById(pid) {
            return $http.get("/api/person/"+pid);
        }

        function deletePlanById(hpid, pid) {
            return $http({
                url: "/api/healthPersonnel/"+hpid+"/plan/"+pid,
                method: "DELETE"
            });
        }
        
        
        function updateAdminById(id , user) {
            return $http.put("api/admin/"+id,user);
        }

        function updatePatientById(id , user) {
            return $http.put("api/patient/"+id,user);
        }

        function updateHealthPersonnelById(id , user) {
            return $http.put("api/healthPersonnel/"+id,user);
        }

        function addPlanToProvider(plan,providerId) {
            return $http.post("/api/healthProvider/"+providerId+"/plan",plan)
        }


        function findHealthPersonnelById(id) {
            return $http.get("/api/healthPersonnel/"+id);
        }

        function findAdminById(id) {
            return $http.get("api/admin/"+id);
        }

        function findHealthProviderById(id) {
            return $http.get("/api/healthProvider/"+id);
        }

        function findPatientById(id) {
            return $http.get("/api/patient/"+id);
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
        	    url: "/api/doctor/name", 
        	    method: "GET",
        	    params: {fName: firstName , lName: lastName}
        	 });
        }
        
        
        function findDoctorBySpecialty(name){
        	return $http({
        	    url: "/api/doctor/specialty", 
        	    method: "GET",
        	    params: {name: name }
        	 });
        }
        
        function findAppointmentsForPatient(id) {
        	return $http({
        	    url: "/api/patient/"+id+"/appointments", 
        	    method: "GET"
        	 });
        }

        function findAllAppointment() {
            return $http({
                url: "api/appointments",
                method: "GET"
            });
        }
        
        function removeAppointment(id, key) {
        	return $http({
        		url: "/api/patient/"+id+"/appointments/"+key,
        		method: "DELETE"
        	});
        }
        
        function findAllPlans(id, proId) {
         	return $http({
        	    url: "/api/healthPersonnel/"+id+"/provider/"+proId+"/plans", 
        	    method: "GET"
        	 });
        }
        
        function getAllPlanforPatient(id) {
        	return $http({
        	    url: "/api/patient/"+id+"/plans", 
        	    method: "GET"
        	 }); 
        }
        
        function deletePlanFromPatient(id, pid){
        	return $http({
        	    url: "/api/patient/"+id+"/plans/"+pid, 
        	    method: "DELETE"
        	 }); 
        }
        
        function getAllPlans() {
        	return $http({
        	    url: "/api/plans", 
        	    method: "GET"
        	 });         	
        }
        
        function replacePlan(patid, pid) {
        	return $http({
        	    url: "/api/patient/"+patid+"/plan/"+pid, 
        	    method: "PUT"
        	 });  
        }
        
        function getAllDoctors() {
        	return $http({
        	    url: "/api/doctors", 
        	    method: "GET"
        	 }); 
        }
        
        function getAllPatients() {
        	return $http({
        	    url: "/api/patients", 
        	    method: "GET"
        	 }); 
        }
        
        function getAllHealthPersonnels() {
        	return $http({
        	    url: "/api/healthPersonnels", 
        	    method: "GET"
        	 }); 
        }

        function getAllPlans(){
        	return $http({
        	    url: "/api/plans", 
        	    method: "GET"
        	 }); 
        }
        
        function deletePatientById(pid){
        	return $http({
        	    url: "/api/patient/"+pid, 
        	    method: "DELETE"
        	 }); 
        }
        
        function deleteHealthPersonnelById(hid) {
        	return $http({
        	    url: "/api/healthPersonnel/"+hid, 
        	    method: "DELETE"
        	 });         	
        }
        
        
        function deletePlanById(pid) {
        	return $http({
        	    url: "/api/plan/"+pid, 
        	    method: "DELETE"
        	 });         	
        }
        
        function deleteAppointmentById(aid) {
        	return $http({
        	    url: "/api/appointment/"+aid, 
        	    method: "DELETE"
        	 });         	
        }
        
        function getAllHealthProviders() {

            	return $http({
            	    url: "/api/healthProviders", 
            	    method: "GET"
            	 }); 
            
        }
        
        function removeHealthProvider(id) {
        	return $http({
        	    url: "/api/healthProvider/"+id, 
        	    method: "DELETE"
        	 });  
        }
        
        function getAllBlogs(){
        	return $http({
        	    url: "/api/blogs", 
        	    method: "GET"
        	 });         	
        }
        
        function removeBlog(id) {
        	return $http({
        	    url: "/api/blogs/"+id, 
        	    method: "DELETE"
        	 }); 
        }
        
        function findAllDoctorsInThisProvider(id) {
        	return $http({
        		url: "/api/healthPersonnel/"+id+"/doctors",
        		method: "GET"
        	})
        }
        
        function findAllPatientsInThisProvider(id) {
        	return $http({
        		url: "/api/healthPersonnel/"+id+"/patients",
        		method: "GET"
        	})
        }
        
        function removeDoctorFromPlan(did, planName) {
        	return $http({
        	    url: "/api/plan/"+planName+"/doctor/"+did, 
        	    method: "DELETE"
        	 }); 
        }
        
        function removePatientFromPlan(patid, planName) {
        	return $http({
        	    url: "/api/plan/"+planName+"/patient/"+patid, 
        	    method: "DELETE"
        	 }); 
        }
        
        
        
        
    };
    
    
})();