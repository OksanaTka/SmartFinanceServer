package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BankBoundary;
import finance.boundaries.NewBankDetails;
import finance.logic.BankService;

@RestController
public class BankController {

	private BankService bankService;
	private final String pageSize = "10";

	@Autowired
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	@RequestMapping(path = "/bank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BankBoundary createBank(@RequestBody NewBankDetails details) {
		BankBoundary bank = new BankBoundary();
		bank.setBankId(details.getBankId());
		bank.setBankName(details.getBankName());
		bank.setBankIcon(details.getBankIcon());
		return bankService.createBank(bank);
	}

	@RequestMapping(path = "/bank/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BankBoundary[] getAllBanks(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = pageSize) int size) {
		return bankService.getAllBanks(page, size).toArray(new BankBoundary[0]);
	}

}
