package finance.data.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.BankEntity;

public interface BankDao extends PagingAndSortingRepository<BankEntity, String> {

	public List<BankEntity> findAllByBankId(@Param("bankId") String bankId, Pageable pageable);
	
	public List<BankEntity> findAllByBankId(@Param("bankId") String bankId);

}
