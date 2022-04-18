package finance.logic;

import java.util.List;

import finance.boundaries.BankAccountBoundary;
import finance.boundaries.BankTransactionsDetailsBoundary;

public interface BankTransactionsDetailsService {
	
	public BankTransactionsDetailsBoundary createBankTransactionsDetails(BankTransactionsDetailsBoundary bankTransactionsDetails);

	

}
