package finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.NewBankAccountDetails;
import finance.logic.BankAccountService;

@RestController
public class BankAccountController {
	private BankAccountService bankAccountsService;

	@Autowired
	public void setBankAccountsService(BankAccountService bankAccountsService) {
		this.bankAccountsService = bankAccountsService;
	}

	public BankAccountService getBankAccountsService() {
		return bankAccountsService;
	}

	@RequestMapping(path = "/bankAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BankAccountBoundary createBankAccount(@RequestBody NewBankAccountDetails details) {
		BankAccountBoundary bankAccount = new BankAccountBoundary();
		bankAccount.setBankId(details.getBankId());
		bankAccount.setUserId(details.getUserId());
		bankAccount.setAccountCode(details.getAccountCode());
		bankAccount.setAccountPassword(details.getAccountPassword());
		return bankAccountsService.createBankAccount(bankAccount);
	}

	@RequestMapping(path = "/bankAccount/{bankAccountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BankAccountBoundary getSpecificBankAccount(@PathVariable("bankAccountId") String bankAccountId) {
		return bankAccountsService.getSpecificBankAccount(bankAccountId);
	}

	@RequestMapping(path = "/bankAccount/getAll/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BankAccountBoundary[] getAllBankAccounts(@PathVariable("userId") String userId) {
		System.out.println("userId: "+userId);
		return bankAccountsService.getAllBankAccounts(userId).toArray(new BankAccountBoundary[0]);
	}

	@RequestMapping(path = "/bankAccount/{userId}/{bankAccountId}", method = RequestMethod.DELETE)
	public void deleteBankAccount(@PathVariable("userId") String userId, @PathVariable("bankAccountId") String bankAccountId) {
		bankAccountsService.deleteBankAccount(userId, bankAccountId);
	}
	
	@RequestMapping(path = "/bankAccount/balance", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody List<BankAccountBoundary> bankAccountBoundary) {
		bankAccountsService.updateBalance(bankAccountBoundary);
	}
}
