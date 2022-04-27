package finance.logic;

import java.util.List;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.TransactionBoundary;

public interface TransactionService {
	public TransactionBoundary createTransaction(TransactionBoundary transactionBoundary);

	public List<TransactionBoundary> getTransactionsByUserIdAndCategoryAndDateAfter(String userId, List<String> categoryId, String date);
	
	public List<TransactionBoundary> getTransactionsByUserIdAndDateAfter(String userId, String date);
	
	public List<TransactionBoundary> getAllTransactionsFromBankApi(List<BankAccountBoundary> bankAccountBoundary);
}
