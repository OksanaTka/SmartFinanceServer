package finance.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Bank_Transactions_Details")
public class BankTransactionsDetailsEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String bankTransactionsDetailsId;

	private String bankId;
	private String bankBranch;
	private String bankAccountNumber;
	private String categoryId;
	private String amount;
	private String date;

	public BankTransactionsDetailsEntity() {
		super();
	}

	public String getBankTransactionsDetailsId() {
		return bankTransactionsDetailsId;
	}

	public void setBankTransactionsDetailsId(String bankTransactionsDetailsId) {
		this.bankTransactionsDetailsId = bankTransactionsDetailsId;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
