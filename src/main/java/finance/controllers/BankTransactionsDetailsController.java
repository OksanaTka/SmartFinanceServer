package finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BankTransactionsDetailsBoundary;
import finance.logic.BankTransactionsDetailsService;

@RestController
public class BankTransactionsDetailsController {

	private BankTransactionsDetailsService bankTransactionsDetailsService;

	@Autowired
	public void setBankTransactionsDetailsService(BankTransactionsDetailsService bankTransactionsDetailsService) {
		this.bankTransactionsDetailsService = bankTransactionsDetailsService;
	}

	public BankTransactionsDetailsService getBankTransactionsDetailsService() {
		return bankTransactionsDetailsService;
	}

	@RequestMapping(path = "/bankTransactionsDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<BankTransactionsDetailsBoundary> createBankTransactionsDetails(
			@RequestBody List<BankTransactionsDetailsBoundary> bankTransactionsDetails) {
		return bankTransactionsDetailsService.createBankTransactionsDetails(bankTransactionsDetails);
	}
	
}
