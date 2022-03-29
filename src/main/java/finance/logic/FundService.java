package finance.logic;

import java.util.List;

import finance.boundaries.FundBoundary;

public interface FundService {
	
	public FundBoundary createFund(FundBoundary fund);

	public List<FundBoundary> getAllFunds(int page, int size);

}
