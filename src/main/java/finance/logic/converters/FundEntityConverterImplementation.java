package finance.logic.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import finance.boundaries.FundBoundary;
import finance.data.FundEntity;
import finance.utils.Utils;

@Component
public class FundEntityConverterImplementation implements EntityConverter<FundEntity, FundBoundary>{

	private Utils utils;

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}
	
	@Override
	public FundBoundary toBoundary(FundEntity entity) {
		FundBoundary fundBoundary = new FundBoundary();
		fundBoundary.setFundId(entity.getFundId());
		fundBoundary.setFundName(entity.getFundName());
		fundBoundary.setFundIcon(entity.getFundIcon());
		return fundBoundary;
	}

	@Override
	public FundEntity fromBoundary(FundBoundary boundary) {
		FundEntity fundEntity = new FundEntity();
		fundEntity.setFundId(boundary.getFundId());
		fundEntity.setFundName(boundary.getFundName());
		fundEntity.setFundIcon(boundary.getFundIcon());
		return fundEntity;
	}
	
}
