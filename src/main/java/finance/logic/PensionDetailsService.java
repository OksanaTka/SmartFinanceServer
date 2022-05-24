package finance.logic;

import java.util.List;

import finance.boundaries.PensionDetailsBoundary;

public interface PensionDetailsService {
	
	public List<PensionDetailsBoundary> createPensionDetails(List<PensionDetailsBoundary> pensionDetailsBoundary);

}
