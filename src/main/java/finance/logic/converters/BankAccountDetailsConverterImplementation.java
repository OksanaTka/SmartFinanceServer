package finance.logic.converters;

import org.springframework.stereotype.Component;
import finance.boundaries.BankAccountDetailsBoundary;
import finance.data.BankAccountDetailsEntity;

@Component
public class BankAccountDetailsConverterImplementation
		implements EntityConverter<BankAccountDetailsEntity, BankAccountDetailsBoundary> {

	@Override
	public BankAccountDetailsBoundary toBoundary(BankAccountDetailsEntity entity) {
		BankAccountDetailsBoundary boundary = new BankAccountDetailsBoundary();
		boundary.setAccountCode(entity.getAccountCode());
		boundary.setAccountPassword(entity.getAccountPassword());
		boundary.setBankAccountDetailsId(entity.getBankAccountDetailsId());
		boundary.setBankAccountNumber(entity.getBankAccountNumber());
		boundary.setBankBranch(entity.getBankBranch());
		boundary.setBankId(entity.getBankId());
		boundary.setBalance(entity.getBalance());
		return boundary;
	}

	@Override
	public BankAccountDetailsEntity fromBoundary(BankAccountDetailsBoundary boundary) {
		BankAccountDetailsEntity entity = new BankAccountDetailsEntity();
		entity.setAccountCode(boundary.getAccountCode());
		entity.setAccountPassword(boundary.getAccountPassword());
		entity.setBankAccountDetailsId(boundary.getBankAccountDetailsId());
		entity.setBankAccountNumber(boundary.getBankAccountNumber());
		entity.setBankBranch(boundary.getBankBranch());
		entity.setBankId(boundary.getBankId());
		entity.setBalance(boundary.getBalance());
		return entity;
	}

}
