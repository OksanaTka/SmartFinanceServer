package finance.logic;

import java.util.List;

import finance.boundaries.BankBoundary;

public interface BankService {

	public List<BankBoundary> createBank(List<BankBoundary> banks);

	public List<BankBoundary> getAllBanks(int page, int size);

}
