package finance.logic;

import java.util.List;

import finance.boundaries.BankBoundary;

public interface BankService {

	public BankBoundary createBank(BankBoundary bank);

	public List<BankBoundary> getAllBanks(int page, int size);

}
