package finance.logic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.FundBoundary;
import finance.data.FundEntity;
import finance.data.dao.FundDao;
import finance.logic.FundService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.Utils;

@Service
public class FundJpa implements FundService{
	
	private FundDao fundDao;
	private Utils utils;
	private EntityConverter<FundEntity, FundBoundary> entityConverter;
	
	public FundJpa() {
		super();
	}
	
	@Autowired
	public void setFundDao(FundDao fundDao) {
		this.fundDao = fundDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}
	
	@Autowired
	public void setEntityConverter(EntityConverter<FundEntity, FundBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public List<FundBoundary> createFund(List<FundBoundary> fund) {
		
		List<FundEntity> entityList = new ArrayList<>();
		for (FundBoundary fundBoundary : fund) {
			utils.assertNull(fundBoundary);
			utils.assertNull(fundBoundary.getFundId());
			utils.assertNull(fundBoundary.getFundName());
			utils.assertNull(fundBoundary.getFundIcon());
			
			List<FundEntity> funds = this.fundDao.findAllByFundId(fundBoundary.getFundId());
			
			if(!funds.isEmpty()) {
				throw new ConflictException("Fund already exists " + fundBoundary.getFundId());
			}
			
			FundEntity entity = this.entityConverter.fromBoundary(fundBoundary);
			entity.setFundName(fundBoundary.getFundName().toLowerCase());
			entity.setFundIcon(fundBoundary.getFundIcon());
			entity = this.fundDao.save(entity);
			entityList.add(entity);
		}
		return entityList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<FundBoundary> getAllFunds(int page, int size) {
		return this.fundDao.findAll(PageRequest.of(page, size, Direction.ASC, "fundId")).getContent().stream()
				.map(this.entityConverter::toBoundary).collect(Collectors.toList());

	}
	

}
