package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.BankAccountDetailsEntity;

public interface BankAccountDetailsDao extends PagingAndSortingRepository<BankAccountDetailsEntity,String>{

	public List<BankAccountDetailsEntity> findAllByBankIdAndBankBranchAndBankAccountNumber(
			@Param("bankId") String bankId,
			@Param("bankBranch") String bankBranch,
			@Param("bankAccountNumber") String bankAccountNumber);
	
	
	public List<BankAccountDetailsEntity> findAllByBankIdAndAccountCodeAndAccountPassword(
			@Param("bankId") String bankId,
			@Param("accountCode") String accountCode,
			@Param("accountPassword") String accountPassword);
}
