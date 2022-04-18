package finance.logic;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.BankAccountDetailsBoundary;

public interface BankAccountDetailsService {

	public BankAccountDetailsBoundary createBankAccountDetails(BankAccountDetailsBoundary bankAccountDetails);
}
