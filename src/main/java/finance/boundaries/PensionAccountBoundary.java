package finance.boundaries;

public class PensionAccountBoundary {
	
	private String pensionId;
	private String fundId;
	private String userId;
	private String employerDeposit;
	private String workerDeposit;
	private String compensation;
	private String totalAmount;
	private String managementFee;
	
	public PensionAccountBoundary() {
		super();
	}


	public String getPensionId() {
		return pensionId;
	}


	public void setPensionId(String pensionId) {
		this.pensionId = pensionId;
	}


	public String getFundId() {
		return fundId;
	}


	public void setFundId(String fundId) {
		this.fundId = fundId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getEmployerDeposit() {
		return employerDeposit;
	}


	public void setEmployerDeposit(String employerDeposit) {
		this.employerDeposit = employerDeposit;
	}


	public String getWorkerDeposit() {
		return workerDeposit;
	}


	public void setWorkerDeposit(String workerDeposit) {
		this.workerDeposit = workerDeposit;
	}


	public String getCompensation() {
		return compensation;
	}


	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}


	public String getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getManagementFee() {
		return managementFee;
	}


	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}

}
