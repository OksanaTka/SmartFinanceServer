package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.PensionDetailsBoundary;
import finance.data.PensionDetailsEntity;

@Component
public class PensionDetailsConverterImplementation implements EntityConverter<PensionDetailsEntity, PensionDetailsBoundary>{

	@Override
	public PensionDetailsBoundary toBoundary(PensionDetailsEntity entity) {
		PensionDetailsBoundary boundary = new PensionDetailsBoundary();
		boundary.setPensionDetailsId(entity.getPensionDetailsId());
		boundary.setFundId(entity.getFundId());
		boundary.setEmployerDeposit(entity.getEmployerDeposit());
		boundary.setWorkerDeposit(entity.getWorkerDeposit());
		boundary.setCompensation(entity.getCompensation());
		boundary.setTotalAmount(entity.getTotalAmount());
		boundary.setManagementFee(entity.getManagementFee());
		boundary.setIdentityNumber(entity.getIdentityNumber());
		boundary.setPhone(entity.getPhone());
		boundary.setCode(entity.getCode());
		return boundary;
	}

	@Override
	public PensionDetailsEntity fromBoundary(PensionDetailsBoundary boundary) {
		PensionDetailsEntity entity = new PensionDetailsEntity();
		entity.setFundId(boundary.getFundId());
		entity.setEmployerDeposit(boundary.getEmployerDeposit());
		entity.setWorkerDeposit(boundary.getWorkerDeposit());
		entity.setCompensation(boundary.getCompensation());
		entity.setTotalAmount(boundary.getTotalAmount());
		entity.setManagementFee(boundary.getManagementFee());
		entity.setIdentityNumber(boundary.getIdentityNumber());
		entity.setPhone(boundary.getPhone());
		entity.setCode(boundary.getCode());
		return entity;
	}

	

}
