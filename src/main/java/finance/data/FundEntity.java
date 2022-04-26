package finance.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Fund")
public class FundEntity {
	
	@Id
	private String fundId;
	private String fundName;
	private String fundIcon;
	
	public FundEntity() {
		super();
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public String getFundId() {
		return fundId;
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
