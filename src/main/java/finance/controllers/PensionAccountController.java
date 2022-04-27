package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.NewPensionAccountDetails;
import finance.boundaries.PensionAccountBoundary;
import finance.logic.PensionAccountService;

@RestController
public class PensionAccountController {
	private PensionAccountService pensionAccountService;

	@Autowired
	public void setPensionAccountService(PensionAccountService pensionAccountService) {
		this.pensionAccountService = pensionAccountService;
	}

	@RequestMapping(path = "/pensionAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PensionAccountBoundary createPensionAccount(@RequestBody NewPensionAccountDetails details) {
		return pensionAccountService.createPensionAccount(details);
	}

	@RequestMapping(path = "/pensionAccount/{pensionAccountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PensionAccountBoundary getSpecificBankAccount(@PathVariable("pensionAccountId") String pensionAccountId) {
		return pensionAccountService.getSpecificPensionAccount(pensionAccountId);
	}

	@RequestMapping(path = "/pensionAccount/{userId}/{fundId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updatePensionAccount(@PathVariable("userId") String userId, @PathVariable("fundId") String fundId) {
		pensionAccountService.updatePensionAccount(userId, fundId);
	}

	@RequestMapping(path = "/pensionAccount/getAll/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PensionAccountBoundary[] getAllPensionAccounts(@PathVariable("userId") String userId) {
		return pensionAccountService.getAllPensionAccounts(userId).toArray(new PensionAccountBoundary[0]);
	}

	@RequestMapping(path = "/pensionAccount/{userId}/{pensionAccountId}", method = RequestMethod.DELETE)
	public void deletePensionAccount(@PathVariable("userId") String userId,
			@PathVariable("pensionAccountId") String pensionAccountId) {
		pensionAccountService.deletePensionAccount(userId, pensionAccountId);
	}

}
