package finance.boundaries;

public class PensionDetailsBoundary {
	
	private String pensionDetailsId;
	private String fundId;
	private String userId;
	private String employerDeposit;
	private String workerDeposit;
	private String compensation;
	private String totalAmount;
	private String managementFee;
	
	private String identityNumber;
	private String phone;
	private String code;
	
	public PensionDetailsBoundary() {
		super();
	}

	public String getPensionDetailsId() {
		return pensionDetailsId;
	}

	public void setPensionDetailsId(String pensionDetailsId) {
		this.pensionDetailsId = pensionDetailsId;
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

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
