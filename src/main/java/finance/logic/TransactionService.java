package finance.logic;

import java.util.List;

import finance.boundaries.TransactionBoundary;

public interface TransactionService {
	public TransactionBoundary createTransaction(TransactionBoundary transactionBoundary);

	public TransactionBoundary getSpecificTransaction(String transactionId);

	public List<TransactionBoundary> getTransactionsByUserId(String userId);

	public List<TransactionBoundary> getTransactionsByUserIdAndAndCategoryAndDateAfter(String userId, List<String> categoryId, String date);

	public void deleteTransaction(String transactionId);

	public List<TransactionBoundary> getAllUserTransactionsByCategory(String userId, String categoryId);
}
