package finance.logic.converters;

import org.springframework.stereotype.Component;
import finance.boundaries.BankAccountBoundary;
import finance.data.BankAccountEntity;

@Component
public class BankAccountEntityConverterImplementation
		implements EntityConverter<BankAccountEntity, BankAccountBoundary> {

	@Override
	public BankAccountBoundary toBoundary(BankAccountEntity entity) {
		BankAccountBoundary boundary = new BankAccountBoundary();
		boundary.setAccountId(entity.getAccountId());
		boundary.setBankId(entity.getBankId());
		boundary.setUserId(entity.getUserId());
		boundary.setAccountCode(entity.getAccountCode());
		boundary.setAccountPassword(entity.getAccountPassword());
		boundary.setBankAccountNumber(entity.getBankAccountNumber());
		boundary.setBankBranch(entity.getBankBranch());
		boundary.setBalance(entity.getBalance());
		return boundary;
	}

	@Override
	public BankAccountEntity fromBoundary(BankAccountBoundary boundary) {
		BankAccountEntity entity = new BankAccountEntity();
		entity.setBankId(boundary.getBankId());
		entity.setUserId(boundary.getUserId());
		entity.setAccountCode(boundary.getAccountCode());
		entity.setAccountPassword(boundary.getAccountPassword());
		entity.setBankAccountNumber(boundary.getBankAccountNumber());
		entity.setBankBranch(boundary.getBankBranch());
		entity.setBalance(boundary.getBalance());
		return entity;
	}

}
