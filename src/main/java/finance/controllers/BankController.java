package finance.controllers;

import java.util.ArrayList;
import java.util.List;

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
	public List<BankBoundary> createBank(@RequestBody List<NewBankDetails> details) {
		List<BankBoundary> boundaries = new ArrayList<>();
		for (NewBankDetails newBankDetails : details) {
			BankBoundary bank = new BankBoundary();
			bank.setBankId(newBankDetails.getBankId());
			bank.setBankName(newBankDetails.getBankName());
			bank.setBankIcon(newBankDetails.getBankIcon());
			boundaries.add(bank);
		}
		return bankService.createBank(boundaries);
	}

	@RequestMapping(path = "/bank/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BankBoundary[] getAllBanks(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = pageSize) int size) {
		return bankService.getAllBanks(page, size).toArray(new BankBoundary[0]);
	}

}
