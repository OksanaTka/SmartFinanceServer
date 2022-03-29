package finance.logic;

import java.util.List;

import finance.boundaries.BankAccountBoundary;

public interface BankAccountService {

	public BankAccountBoundary createBankAccount(BankAccountBoundary bankAccount);

	public BankAccountBoundary getSpecificBankAccount(String bankAccountId);

	public List<BankAccountBoundary> getAllBankAccounts(String userId);

	public void deleteBankAccount(String userId, String bankAccountId);

}
