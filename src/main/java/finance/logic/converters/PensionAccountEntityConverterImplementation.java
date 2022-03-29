package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.PensionAccountBoundary;
import finance.data.PensionAccountEntity;

@Component
public class PensionAccountEntityConverterImplementation implements EntityConverter<PensionAccountEntity, PensionAccountBoundary>{

	@Override
	public PensionAccountBoundary toBoundary(PensionAccountEntity entity) {
		PensionAccountBoundary boundary = new PensionAccountBoundary();
		boundary.setPensionId(entity.getPensionId());
		boundary.setFundId(entity.getFundId());
		boundary.setEmployerDeposit(entity.getEmployerDeposit());
		boundary.setWorkerDeposit(entity.getWorkerDeposit());
		boundary.setCompensation(entity.getCompensation());
		boundary.setManagementFee(entity.getManagementFee());
		boundary.setUserId(entity.getUserId());
		boundary.setTotalAmount(entity.getTotalAmount());
		return boundary;
	}

	@Override
	public PensionAccountEntity fromBoundary(PensionAccountBoundary boundary) {
		PensionAccountEntity entity = new PensionAccountEntity();
		entity.setFundId(boundary.getFundId());
		entity.setEmployerDeposit(boundary.getEmployerDeposit());
		entity.setWorkerDeposit(boundary.getWorkerDeposit());
		entity.setCompensation(boundary.getCompensation());
		entity.setManagementFee(boundary.getManagementFee());
		entity.setUserId(boundary.getUserId());
		entity.setTotalAmount(boundary.getTotalAmount());
		return entity;
	}

}
