package kodlamaio.hrms.core.adapter.abstracts;


public interface UserCheckService {
	
	boolean checkIfRealPerson(String nationalityId, String firstName, String lastName, String yearOfBirth);
}
