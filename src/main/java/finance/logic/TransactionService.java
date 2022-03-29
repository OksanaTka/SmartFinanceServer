package finance.logic;

import java.util.List;

import finance.boundaries.TransactionBoundary;

public interface TransactionService {
	public TransactionBoundary createTransaction(TransactionBoundary transactionBoundary);

	public TransactionBoundary getSpecificTransaction(String transactionId);

	public List<TransactionBoundary> getTransactionsByUserId(String userId);

	public List<TransactionBoundary> getTransactionsByUserIdAndByTypeAndByCategoryAndDateAfter(String userId, List<String> categoryId, String date);

	public void deleteTransaction(String transactionId);

	public List<TransactionBoundary> getAllUserTransactionsByType(String userId, String transactionType);

	public List<TransactionBoundary> getAllUserTransactionsByCategory(String userId, String categoryId);
}
