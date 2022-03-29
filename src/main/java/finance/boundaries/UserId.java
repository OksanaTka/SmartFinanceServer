package finance.boundaries;

public class UserId {
	private String email;

	public UserId() {
		super();
	}

	public UserId(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object other) {
		UserId uid = (UserId) other;
		return this.getEmail().equals(uid.getEmail());
	}

}
