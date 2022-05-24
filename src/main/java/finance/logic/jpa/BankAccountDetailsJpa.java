package finance.logic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<BankAccountDetailsBoundary> createBankAccountDetails(List<BankAccountDetailsBoundary> bankAccountDetails) {
		
		List<BankAccountDetailsEntity> entityList = new ArrayList<>();
		for (BankAccountDetailsBoundary boundary : bankAccountDetails) {
			utils.assertNull(boundary);
			utils.assertNull(boundary.getBankId());
			utils.assertNull(boundary.getAccountCode());
			utils.assertNull(boundary.getAccountPassword());
			utils.assertNull(boundary.getBankAccountNumber());
			utils.assertNull(boundary.getBankBranch());

			
			// Check if the bank account details already exists
			List<BankAccountDetailsEntity> banksAccountDetail = this.bankAccountDetailsDao
					.findAllByBankIdAndBankBranchAndBankAccountNumber(boundary.getBankId(),
							boundary.getBankBranch(), boundary.getBankAccountNumber());
			if (!banksAccountDetail.isEmpty()) {
				throw new UnsupportedExecption("Bank Account Details already exists");
			}
	
			BankAccountDetailsEntity entity = this.entityConverter.fromBoundary(boundary);
			entity = this.bankAccountDetailsDao.save(entity);
			entityList.add(entity);
		}
		return entityList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}
