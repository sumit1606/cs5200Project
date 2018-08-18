/**
 * Created by sumitbhanwala
 */
(function () {
    angular
        .module("GetYourAppointment")
        .factory("DoctorService" ,DoctorService)

    function DoctorService($http) {
        var api = {
        	"findDoctorById":findDoctorById,
        	"findAppointmentsForDoctor": findAppointmentsForDoctor,
        	"removeAppointment":removeAppointment,
        	"getAllPlans":getAllPlans,
        	"getAllPlanforDoctor":getAllPlanforDoctor,
        	"deletePlanFromDoctor":deletePlanFromDoctor,
        	"addPlanToSupported": addPlanToSupported,
			"updateDoctorById":updateDoctorById

        };
        return api;

        function findDoctorById(id) {
        	return $http.get("/api/doctor/"+id);
        }
        
        function findAppointmentsForDoctor(id) {
        	return $http({
        	    url: "/api/doctor/"+id+"/appointments", 
        	    method: "GET"
        	 });
        }

        function updateDoctorById(id , user) {
            return $http.put("api/doctor/"+id,user);
        }
        
        function removeAppointment(id, key) {
        	return $http({
        		url: "/api/doctor/"+id+"/appointments/"+key,
        		method: "DELETE"
        	});
        }
        
        function getAllPlans(){
        	return $http({
        	    url: "/api/plans", 
        	    method: "GET"
        	 }); 
        }
        
        function getAllPlanforDoctor(id) {
        	return $http({
        	    url: "/api/doctor/"+id+"/plans", 
        	    method: "GET"
        	 }); 
        }
        
        function deletePlanFromDoctor(did, pid) {
        	return $http({
        	    url: "/api/doctor/"+did+"/plans/"+pid, 
        	    method: "DELETE"
        	 }); 
        }
        
        function addPlanToSupported (did, pid ) {
          	return $http({
        	    url: "/api/doctor/"+did+"/plans/"+pid, 
        	    method: "PUT"
        	 }); 
        }
        
        
    };
    
    
})();