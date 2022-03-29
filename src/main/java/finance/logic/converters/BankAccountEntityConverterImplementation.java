package finance.logic.converters;


import org.springframework.stereotype.Component;


import finance.boundaries.BankAccountBoundary;
import finance.data.BankAccountEntity;

@Component
public class BankAccountEntityConverterImplementation implements EntityConverter<BankAccountEntity, BankAccountBoundary>{

	@Override
	public BankAccountBoundary toBoundary(BankAccountEntity entity) {
		BankAccountBoundary ib = new BankAccountBoundary();
		ib.setAccountId(entity.getAccountId());
		ib.setBankId(entity.getBankId());
		ib.setUserId(entity.getUserId());
		ib.setAccountCode(entity.getAccountCode());
		ib.setAccountPassword(entity.getAccountPassword());
		return ib;
	}

	@Override
	public BankAccountEntity fromBoundary(BankAccountBoundary boundary) {
		BankAccountEntity ie = new BankAccountEntity();
		ie.setBankId(boundary.getBankId());
		ie.setUserId(boundary.getUserId());
		ie.setAccountCode(boundary.getAccountCode());
		ie.setAccountPassword(boundary.getAccountPassword());
		
		return ie;
	}


}
