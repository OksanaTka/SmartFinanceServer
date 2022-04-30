package finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.TransactionBoundary;
import finance.logic.TransactionService;

@RestController
public class TransactionController {
	private TransactionService transactionService;

	public TransactionService getTransactionService() {
		return transactionService;
	}

	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@RequestMapping(path = "/transaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary createTransaction(@RequestBody TransactionBoundary details) {
		return transactionService.createTransaction(details);
	}

	@RequestMapping(path = "/transaction/getAll/{userId}/{categoryId}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getTransactionsByUserIdAndCategoryAndDateAfter(@PathVariable("userId") String userId,
			@PathVariable("categoryId") List<String> categoryId,@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  String date) {
		return transactionService.getTransactionsByUserIdAndCategoryAndDateAfter(userId, categoryId,date)
				.toArray(new TransactionBoundary[0]);
	}
	
	@RequestMapping(path = "/transaction/getAll/{userId}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getTransactionsByUserIdAndDateAfter(@PathVariable("userId") String userId,
			@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  String date) {
		return transactionService.getTransactionsByUserIdAndDateAfter(userId,date)
				.toArray(new TransactionBoundary[0]);
	}
	
	@RequestMapping(path = "/transaction/getAll/bankApi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getAllTransactionsFromBankApi(@RequestBody List<BankAccountBoundary> bankAccountBoundary) {
		return transactionService.getAllTransactionsFromBankApi(bankAccountBoundary)
				.toArray(new TransactionBoundary[0]);
	}
	
	@RequestMapping(path = "/transaction/prediction/{userId}/{categoryId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getPrediction(@PathVariable("userId") String userId,
			@PathVariable("categoryId") List<String> categoryId) {
		return transactionService.getYearPrediction(userId, categoryId);
	}
}
