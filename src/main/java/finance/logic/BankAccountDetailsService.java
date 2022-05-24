package finance.logic;

import java.util.List;

import finance.boundaries.BankAccountDetailsBoundary;

public interface BankAccountDetailsService {

	public List<BankAccountDetailsBoundary> createBankAccountDetails(List<BankAccountDetailsBoundary> bankAccountDetails);
}
