package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import finance.data.BankTransactionsDetailsEntity;

public interface BankTransactionsDetailsDao extends PagingAndSortingRepository<BankTransactionsDetailsEntity, String> {

	public List<BankTransactionsDetailsEntity> findAllByBankIdAndBankBranchAndBankAccountNumberAndDateAfter(
			@Param("bankId") String bankId, @Param("bankBranch") String bankBranch,
			@Param("bankAccountNumber") String bankAccountNumber,
			@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date);
	
	public List<BankTransactionsDetailsEntity> findAllByBankIdAndBankBranchAndBankAccountNumber(
			@Param("bankId") String bankId, @Param("bankBranch") String bankBranch,
			@Param("bankAccountNumber") String bankAccountNumber);


}
