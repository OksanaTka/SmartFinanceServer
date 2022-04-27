package finance.data.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import finance.data.PensionDetailsEntity;

public interface PensionDetailsDao extends PagingAndSortingRepository<PensionDetailsEntity,String>{
	
	public List<PensionDetailsEntity> findAllByFundIdAndIdentityNumber(
			@Param("fundId") String fundId,
			@Param("identityNumber") String identityNumber);
	
}
