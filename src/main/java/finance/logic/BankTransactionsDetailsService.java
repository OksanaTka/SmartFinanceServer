package finance.logic;

import java.util.List;

import finance.boundaries.BankTransactionsDetailsBoundary;

public interface BankTransactionsDetailsService {
	
	public List<BankTransactionsDetailsBoundary> createBankTransactionsDetails(List<BankTransactionsDetailsBoundary> bankTransactionsDetails);

}
