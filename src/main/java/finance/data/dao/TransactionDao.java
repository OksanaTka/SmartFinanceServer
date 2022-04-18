package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import finance.data.TransactionEntity;

public interface TransactionDao extends PagingAndSortingRepository<TransactionEntity, String> {

	public List<TransactionEntity> findAllByTransactionId(@Param("transactionId") String transactionId);

	public List<TransactionEntity> findAllByUserId(@Param("userId") String userId);

	// Get all expense/income of the user by categories id
	public List<TransactionEntity> findAllByUserIdAndCategoryIdInAndDateAfterOrderByDate(
			@Param("userId") String userId,
			 @Param("categoryId") List<String> categoryId,
			 @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date);
	
	public List<TransactionEntity> findAllByUserIdAndDateAfterOrderByDate(
			@Param("userId") String userId,
			 @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date);

	// Get all transactions of the user by category - Food/Clothes
	public List<TransactionEntity> findAllByUserIdAndCategoryId(@Param("userId") String userId,
			@Param("categoryId") String categoryId);

	// Get all transactions of the user by date
	public List<TransactionEntity> findAllByUserIdOrderByDate(@Param("userId") String userId);
	
	// Get all transactions of the user by date
		public List<TransactionEntity> findAllByUserIdAndBankAccountIdOrderByDate(@Param("userId") String userId, @Param("bankAccountId") String bankAccountId);

}
