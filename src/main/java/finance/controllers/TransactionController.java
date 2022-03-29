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

	@RequestMapping(path = "/transaction/getAll/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getTransactionsByUserId(@PathVariable("userId") String userId) {
		return transactionService.getTransactionsByUserId(userId).toArray(new TransactionBoundary[0]);
	}

	@RequestMapping(path = "/transaction/getAll/{userId}/{categoryId}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getTransactionsByUserIdAndTypeAndCategoryAndDateAfter(@PathVariable("userId") String userId,
			@PathVariable("categoryId") List<String> categoryId,@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  String date) {
		System.out.println("---------------------------> "+ date);
		return transactionService.getTransactionsByUserIdAndByTypeAndByCategoryAndDateAfter(userId, categoryId,date)
				.toArray(new TransactionBoundary[0]);
	}
	
	
	@RequestMapping(path = "/transaction/{transactionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary getSpecificTransaction(@PathVariable("transactionId") String transactionId) {
		return transactionService.getSpecificTransaction(transactionId);
	}

	@RequestMapping(path = "/transaction/{transactionId}", method = RequestMethod.DELETE)
	public void deleteTransaction(@PathVariable("transactionId") String transactionId) {
		transactionService.deleteTransaction(transactionId);
	}

	@RequestMapping(path = "/transaction/type/{userId}/{transactionType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getAllUserTransactionsByType(@PathVariable("userId") String userId,
			@PathVariable("transactionType") String transactionType) {
		return transactionService.getAllUserTransactionsByType(userId, transactionType)
				.toArray(new TransactionBoundary[0]);
	}

	@RequestMapping(path = "/transaction/category/{userId}/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionBoundary[] getAllUserTransactionsByCategory(@PathVariable("userId") String userId,
			@PathVariable("categoryId") String categoryId) {
		return transactionService.getAllUserTransactionsByCategory(userId, categoryId)
				.toArray(new TransactionBoundary[0]);
	}

}