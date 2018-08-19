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
			"updateDoctorById":updateDoctorById,
			"deleteDoctorById":deleteDoctorById,
			"getDoctorByName":getDoctorByName,
			"followDoctorById":followDoctorById,
			"unfollowDoctorById":unfollowDoctorById
        };
        return api;


        function getDoctorByName(firstName,lastName) {
            return $http({
                url: "/api/doctorByName",
                method: "GET",
                params: {fName: firstName , lName: lastName}
            });
        }

        function followDoctorById(did,pid) {
            return $http.get("/api/doctor/"+did+"/follow/"+pid);
        }

        function unfollowDoctorById(did,pid) {
            return $http.get("/api/doctor/"+did+"/unfollow/"+pid);
        }


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
        
        function deleteDoctorById(did) {
        	return $http({
        	    url: "/api/doctor/"+did, 
        	    method: "DELETE"
        	 }); 
        }
        
        
    };
    
    
})();