package kodlamaio.hrms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobPositionService;
import kodlamaio.hrms.core.DataResult;
import kodlamaio.hrms.core.Result;
import kodlamaio.hrms.entities.concretes.JobPosition;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jobs")
public class JobPositionController {
	
	private JobPositionService jobPositionService;

	@Autowired
	public JobPositionController(JobPositionService jobPositionService) {
		this.jobPositionService = jobPositionService;
	}
	
	
	
	@GetMapping("/getall")
	public DataResult<List<JobPosition>> getAll(String title){
		return this.jobPositionService.getAll(title);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody JobPosition jobPosition) {
       return this.jobPositionService.add(jobPosition);	
    }
}