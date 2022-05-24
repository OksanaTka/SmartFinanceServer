package finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.PensionDetailsBoundary;
import finance.logic.PensionDetailsService;

@RestController
public class PensionDetailsController {
	
	private PensionDetailsService pensionDetailsService;

	@Autowired
	public void setPensionDetailsService(PensionDetailsService pensionDetailsService) {
		this.pensionDetailsService = pensionDetailsService;
	}

	public PensionDetailsService getPensionDetailsService() {
		return pensionDetailsService;
	}
	
	@RequestMapping(path = "/pensionDetails", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<PensionDetailsBoundary> createPensionDetails(
			@RequestBody List<PensionDetailsBoundary> pensionDetailsBoundary) {
		return pensionDetailsService.createPensionDetails(pensionDetailsBoundary);
	}
	
}
