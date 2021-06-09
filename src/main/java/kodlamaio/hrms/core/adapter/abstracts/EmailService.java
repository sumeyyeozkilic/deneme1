package kodlamaio.hrms.core.adapter.abstracts;

import kodlamaio.hrms.entities.concretes.User;

public interface EmailService {
	
    boolean verify(User user);
	
	void sendMail(String email,String message);
}
