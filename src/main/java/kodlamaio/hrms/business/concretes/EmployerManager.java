package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.DataResult;
import kodlamaio.hrms.core.ErrorDataResult;
import kodlamaio.hrms.core.ErrorResult;
import kodlamaio.hrms.core.Result;
import kodlamaio.hrms.core.SuccessDataResult;
import kodlamaio.hrms.core.SuccessResult;
import kodlamaio.hrms.core.adapter.abstracts.EmailService;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager implements EmployerService{
	
	private EmployerDao employerDao;
	private EmailService emailService;

	@Autowired
	public EmployerManager(EmployerDao employerDao, EmailService emailService) {
		this.employerDao = employerDao;
		this.emailService=emailService;
	}

    public Result controlField(Employer employer){
		
	 if(employer.getCompanyName() !=null && employer.getEmail() !=null && employer.getPhoneNumber() !=null && 
		   employer.getWebAddress() !=null && employer.getPassword() !=null) {
			
			return new SuccessResult(true);
	}
		return new ErrorResult(false);
    }
	
    public Result webAddressDomainControl(String webAddress, String email ) {
		
		String emailSplit = email.split("@")[1];
		
		if(!webAddress.contains(emailSplit)) {
			
			return new ErrorResult(false);
		}
	
		return new SuccessResult(true);
	}
    
    public DataResult<Employer> notExistEmailInDb(String email) {
		if(employerDao.findByEmail(email) == null){
			
			return new SuccessDataResult<Employer>(employerDao.findByEmail(email));	
		}
		return new ErrorDataResult<Employer>(employerDao.findByEmail(email));
		
	    }
	
	@Override
	public DataResult<List<Employer>> getAll(String companyName) {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),"Data Listelendi");
	}

	
	@Override
	public Result add(Employer employer) {
		
		if(!controlField(employer).isSuccess()) {
			return new ErrorResult("Zorunlu alanlar doldurulmalıdır.");
		}
		
		if(!webAddressDomainControl(employer.getWebAddress(), employer.getEmail()).isSuccess()) {
			return new ErrorResult("Emailinizle web sitenizinizin e maili ayni olmalı.");
		}
		
        if(!notExistEmailInDb(employer.getEmail()).isSuccess()){
			
			return new ErrorResult("Exist mail in db");
        }
		
		employerDao.save(employer);
		emailService.sendMail(employer.getEmail(), null);
		return new SuccessResult(true, "Kaydınız başarılı bir şkeilde gerçekleştirildi");
	}
	
}

