package finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BankAccountDetailsBoundary;
import finance.logic.BankAccountDetailsService;

@RestController
public class BankAccountDetailsController {

	private BankAccountDetailsService bankAccountDetailsService;

	@Autowired
	public void setBankAccountDetailsService(BankAccountDetailsService bankAccountDetailsService) {
		this.bankAccountDetailsService = bankAccountDetailsService;
	}

	public BankAccountDetailsService getBankAccountDetailsService() {
		return bankAccountDetailsService;
	}

	@RequestMapping(path = "/bankAccountDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<BankAccountDetailsBoundary> createBankAccountDetails(
			@RequestBody List<BankAccountDetailsBoundary> bankAccountDetails) {
		return bankAccountDetailsService.createBankAccountDetails(bankAccountDetails);
	}

}
