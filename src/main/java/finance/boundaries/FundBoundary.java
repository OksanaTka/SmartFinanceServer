package finance.boundaries;

public class FundBoundary {
	
	private String fundId;
	private String fundName;
	private String fundIcon;
	
	public FundBoundary() {
		super();
	}

	public String getFundId() {
		return fundId;
	}
	
	public void setFundId(String funId) {
		this.fundId = funId;
	}
	
	public String getFundName() {
		return fundName;
	}
	
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	
	public String getFundIcon() {
		return fundIcon;
	}
	
	public void setFundIcon(String fundIcon) {
		this.fundIcon = fundIcon;
	}
	
}
