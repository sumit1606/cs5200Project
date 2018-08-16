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
            "creatAppointment":creatAppointment,
            "createUser":createUser,
            "createDoctor":createDoctor,
            "createHealthProvider":createHealthProvider,
        };
        return api;

        function test() {
        	return $http.get("/api/user/test");
        }

        // adding trailing slash as spring is truncating the part after.
        function getUserByEmail(email) {
            
        }

        function createUser(user) {
            return $http.post("/api/user", user);
        }

        function createDoctor(doctor) {
            return $http.post("/api/doctor", doctor);
        }

        function createHealthProvider(heathprovider) {
            return $http.post("/api/heathprovider", heathprovider);
        }

        
        function creatAppointment(appointment) {
        	return $http.post("/api/user/appointment", appointment);
        }

        function findUserByCredentials(emailAddress , password) {
        	
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
        
    };
    
    
})();