package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.FundBoundary;
import finance.data.FundEntity;

@Component
public class FundEntityConverterImplementation implements EntityConverter<FundEntity, FundBoundary>{

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
