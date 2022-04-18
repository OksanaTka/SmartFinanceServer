package finance.logic;

import java.util.List;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.BankTransactionsDetailsBoundary;
import finance.boundaries.TransactionBoundary;

public interface TransactionService {
	public TransactionBoundary createTransaction(TransactionBoundary transactionBoundary);

	public TransactionBoundary getSpecificTransaction(String transactionId);

	public List<TransactionBoundary> getTransactionsByUserId(String userId);

	public List<TransactionBoundary> getTransactionsByUserIdAndCategoryAndDateAfter(String userId, List<String> categoryId, String date);
	
	public List<TransactionBoundary> getTransactionsByUserIdAndDateAfter(String userId, String date);


	public void deleteTransaction(String transactionId);

	public List<TransactionBoundary> getAllUserTransactionsByCategory(String userId, String categoryId);
	
	public List<TransactionBoundary> getAllTransactionsFromBankApi(BankAccountBoundary bankAccountBoundary);

}
