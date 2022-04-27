package finance.logic;

import java.util.List;

import finance.boundaries.NewPensionAccountDetails;
import finance.boundaries.PensionAccountBoundary;

public interface PensionAccountService {

	public PensionAccountBoundary createPensionAccount(NewPensionAccountDetails pensionAccount);

	public PensionAccountBoundary getSpecificPensionAccount(String pensionId);

	public List<PensionAccountBoundary> getAllPensionAccounts(String userId);

	public void deletePensionAccount(String userId, String pensionId);

	public PensionAccountBoundary updatePensionAccount(String userId, String fundId);
}
