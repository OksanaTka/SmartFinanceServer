package finance.boundaries;

public class BankAccountDetailsBoundary {
	private String bankAccountDetailsId;

	private String bankId;
	private String bankBranch;
	private String bankAccountNumber;
	private String accountCode;
	private String accountPassword;

	public BankAccountDetailsBoundary() {
		super();
	}

	public String getBankAccountDetailsId() {
		return bankAccountDetailsId;
	}

	public void setBankAccountDetailsId(String bankAccountDetailsId) {
		this.bankAccountDetailsId = bankAccountDetailsId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
}
