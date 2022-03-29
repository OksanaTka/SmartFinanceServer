package finance.boundaries;

public class NewFundDetails {
	private String fundId;
	private String fundName;
	private String fundIcon;
	
	public NewFundDetails() {
		super();
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

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
}
