package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.BankAccountEntity;


public interface BankAccountDao extends PagingAndSortingRepository<BankAccountEntity,String> {

	public List<BankAccountEntity> findAllByAccountId(
			@Param("accountId") String accountId);
	
	public List<BankAccountEntity> findAllByAccountCode(
			@Param("accountCode") String accountCode);
	
	public List<BankAccountEntity> findAllByUserId(
			@Param("userId") String userId);
	
	public List<BankAccountEntity> findAllByBankId(
			@Param("bankId") String bankId);
	
}
