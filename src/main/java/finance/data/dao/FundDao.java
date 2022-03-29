package finance.data.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import finance.data.FundEntity;

public interface FundDao extends PagingAndSortingRepository<FundEntity, String> {

	public List<FundEntity> findAllByFundId(@Param("fundId") String fundId, Pageable pageable);

	public List<FundEntity> findAllByFundId(@Param("fundId") String fundId);

}
