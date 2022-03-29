package finance.boundaries;

public class BankBoundary {
	
	private String bankId;
	private String bankName;
	private String bankIcon;
	
	
	public BankBoundary() {
		super();
	}

	public String getBankId() { 
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankIcon() {
		return bankIcon;
	}

	public void setBankIcon(String bankIcon) {
		this.bankIcon = bankIcon;
	}
	
}
