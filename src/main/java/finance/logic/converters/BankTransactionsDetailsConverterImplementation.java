package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.BankTransactionsDetailsBoundary;
import finance.data.BankTransactionsDetailsEntity;

@Component
public class BankTransactionsDetailsConverterImplementation
		implements EntityConverter<BankTransactionsDetailsEntity, BankTransactionsDetailsBoundary> {

	@Override
	public BankTransactionsDetailsBoundary toBoundary(BankTransactionsDetailsEntity entity) {
		BankTransactionsDetailsBoundary ib = new BankTransactionsDetailsBoundary();
		ib.setAmount(entity.getAmount());
		ib.setBankAccountNumber(entity.getBankAccountNumber());
		ib.setBankBranch(entity.getBankBranch());
		ib.setBankId(entity.getBankId());
		ib.setBankTransactionsDetailsId(entity.getBankTransactionsDetailsId());
		ib.setCategoryId(entity.getCategoryId());
		ib.setDate(entity.getDate());

		return ib;
	}

	@Override
	public BankTransactionsDetailsEntity fromBoundary(BankTransactionsDetailsBoundary boundary) {
		BankTransactionsDetailsEntity ie = new BankTransactionsDetailsEntity();
		ie.setAmount(boundary.getAmount());
		ie.setBankAccountNumber(boundary.getBankAccountNumber());
		ie.setBankBranch(boundary.getBankBranch());
		ie.setBankId(boundary.getBankId());
		ie.setBankTransactionsDetailsId(boundary.getBankTransactionsDetailsId());
		ie.setCategoryId(boundary.getCategoryId());
		ie.setDate(boundary.getDate());

		return ie;
	}

}
