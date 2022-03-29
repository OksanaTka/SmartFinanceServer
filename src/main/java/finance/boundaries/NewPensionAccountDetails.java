package finance.boundaries;

public class NewPensionAccountDetails {
	
	private String userId;
	private String fundId;
	
	public NewPensionAccountDetails() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	
}
