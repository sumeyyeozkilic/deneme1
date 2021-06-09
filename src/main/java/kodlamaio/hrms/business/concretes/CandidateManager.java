package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.DataResult;
import kodlamaio.hrms.core.ErrorDataResult;
import kodlamaio.hrms.core.ErrorResult;
import kodlamaio.hrms.core.Result;
import kodlamaio.hrms.core.SuccessDataResult;
import kodlamaio.hrms.core.SuccessResult;
import kodlamaio.hrms.core.adapter.abstracts.EmailService;
import kodlamaio.hrms.core.adapter.abstracts.UserCheckService;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService{

	private UserCheckService userCheckService;
	private CandidateDao candidateDao;
	private EmailService emailService;
	
	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserCheckService userCheckService, EmailService emailService) {
		this.candidateDao = candidateDao;
		this.userCheckService=userCheckService;
		this.emailService=emailService;
	}
	
	public boolean controlField(Candidate candidate) {
		if(candidate.getFirstName() !=null && candidate.getLastName() !=null && candidate.getNationalityId() != null && 
				candidate.getDateOfBirth() != null && candidate.getEmail() !=null && candidate.getPassword() !=null) {
			return true;
		}
		return false;
	}
	
	public DataResult<Candidate> notExistNationalityIdInDb(String nationalityId){
		if(candidateDao.findAllByNationalityId(nationalityId)== null) {
			return new SuccessDataResult<Candidate>(candidateDao.findByNationalityId(nationalityId));
	} 
	        return new ErrorDataResult<Candidate>(candidateDao.findByNationalityId(nationalityId));
	}
	
	public DataResult<Candidate> notExistEmailInDb(String email) {
		if(candidateDao.findByEmail(email) == null){
			return new SuccessDataResult<Candidate>(candidateDao.findByEmail(email));
			
	}
		return new ErrorDataResult<Candidate>(candidateDao.findByEmail(email));
	}
	
	
	@Override
	public DataResult<List<Candidate>> getAll() {
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(),"Data Listelendi");
	}
 
	
	@Override
	public Result add(Candidate candidate) {
		if(!controlField(candidate)) {
			return new ErrorResult("Zorunlu alanlar doldurulmalıdır.");
		}
		
		if(!userCheckService.checkIfRealPerson(candidate.getNationalityId(), candidate.getFirstName(), candidate.getLastName(), candidate.getDateOfBirth())) {
			return new ErrorResult("Mernis tarafından geçersiz kullanıcı");
		}
		
		if(!notExistEmailInDb(candidate.getEmail()).isSuccess()) {
			return new ErrorResult("Var olan email.");
		}
		
		if(!notExistNationalityIdInDb(candidate.getNationalityId()).isSuccess()) {
			return new ErrorResult("Var olan kullanıcı.");
		}
		
		candidateDao.save(candidate);
		emailService.sendMail(candidate.getEmail(), null);
		return new SuccessResult("Başarılı bir şekilde sisteme kaydedildi");
	}

	

}
