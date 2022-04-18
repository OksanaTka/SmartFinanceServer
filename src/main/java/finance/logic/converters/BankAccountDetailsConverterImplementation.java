package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.BankAccountDetailsBoundary;
import finance.data.BankAccountDetailsEntity;

@Component
public class BankAccountDetailsConverterImplementation
		implements EntityConverter<BankAccountDetailsEntity, BankAccountDetailsBoundary> {

	@Override
	public BankAccountDetailsBoundary toBoundary(BankAccountDetailsEntity entity) {
		BankAccountDetailsBoundary ib = new BankAccountDetailsBoundary();
		ib.setAccountCode(entity.getAccountCode());
		ib.setAccountPassword(entity.getAccountPassword());
		ib.setBankAccountDetailsId(entity.getBankAccountDetailsId());
		ib.setBankAccountNumber(entity.getBankAccountNumber());
		ib.setBankBranch(entity.getBankBranch());
		ib.setBankId(entity.getBankId());

		return ib;
	}

	@Override
	public BankAccountDetailsEntity fromBoundary(BankAccountDetailsBoundary boundary) {
		BankAccountDetailsEntity ie = new BankAccountDetailsEntity();
		ie.setAccountCode(boundary.getAccountCode());
		ie.setAccountPassword(boundary.getAccountPassword());
		ie.setBankAccountDetailsId(boundary.getBankAccountDetailsId());
		ie.setBankAccountNumber(boundary.getBankAccountNumber());
		ie.setBankBranch(boundary.getBankBranch());
		ie.setBankId(boundary.getBankId());

		return ie;
	}

}
