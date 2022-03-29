package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.PensionAccountEntity;

public interface PensionAccountDao extends PagingAndSortingRepository<PensionAccountEntity, String> {

	public List<PensionAccountEntity> findAllByUserId(@Param("userId") String userId);

	public List<PensionAccountEntity> findAllByFundId(@Param("fundId") String fundId);

	public List<PensionAccountEntity> findAllByPensionId(@Param("pensionId") String pensionId);
}
