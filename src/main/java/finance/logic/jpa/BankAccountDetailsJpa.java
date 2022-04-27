package finance.logic.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.BankAccountDetailsBoundary;
import finance.data.BankAccountDetailsEntity;

import finance.data.dao.BankAccountDetailsDao;
import finance.logic.BankAccountDetailsService;
import finance.logic.converters.EntityConverter;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class BankAccountDetailsJpa implements BankAccountDetailsService {

	private BankAccountDetailsDao bankAccountDetailsDao;
	private Utils utils;
	private EntityConverter<BankAccountDetailsEntity, BankAccountDetailsBoundary> entityConverter;

	@Autowired
	public void setEntityConverter(
			EntityConverter<BankAccountDetailsEntity, BankAccountDetailsBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Autowired
	public void setBankAccountDetailsDao(BankAccountDetailsDao bankAccountDetailsDao) {
		this.bankAccountDetailsDao = bankAccountDetailsDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	public BankAccountDetailsJpa() {
		super();
	}

	@Override
	public BankAccountDetailsBoundary createBankAccountDetails(BankAccountDetailsBoundary bankAccountDetails) {
		utils.assertNull(bankAccountDetails);
		utils.assertNull(bankAccountDetails.getBankId());
		utils.assertNull(bankAccountDetails.getAccountCode());
		utils.assertNull(bankAccountDetails.getAccountPassword());
		utils.assertNull(bankAccountDetails.getBankAccountNumber());
		utils.assertNull(bankAccountDetails.getBankBranch());

		
		// Check if the bank account details already exists
		List<BankAccountDetailsEntity> banksAccountDetail = this.bankAccountDetailsDao
				.findAllByBankIdAndBankBranchAndBankAccountNumber(bankAccountDetails.getBankId(),
						bankAccountDetails.getBankBranch(), bankAccountDetails.getBankAccountNumber());
		if (!banksAccountDetail.isEmpty()) {
			throw new UnsupportedExecption("Bank Account Details already exists");
		}
		

		BankAccountDetailsEntity entity = this.entityConverter.fromBoundary(bankAccountDetails);
		entity = this.bankAccountDetailsDao.save(entity);

		return this.entityConverter.toBoundary(entity);
	}

}
