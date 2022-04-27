package finance.logic.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.BankTransactionsDetailsBoundary;
import finance.data.BankTransactionsDetailsEntity;
import finance.data.dao.BankTransactionsDetailsDao;
import finance.logic.BankTransactionsDetailsService;
import finance.logic.converters.EntityConverter;
import finance.utils.Utils;

@Service
public class BankTransactionsDetailsJpa implements BankTransactionsDetailsService {

	private BankTransactionsDetailsDao bankTransactionsDetailsDao;
//	private BankAccountDao bankAccountDao;
//	private BankDao bankDao;
//	private UserDao userDao;
	private Utils utils;
	private EntityConverter<BankTransactionsDetailsEntity, BankTransactionsDetailsBoundary> entityConverter;

	@Autowired
	public void setBankTransactionsDetailsDao(BankTransactionsDetailsDao bankTransactionsDetailsDao) {
		this.bankTransactionsDetailsDao = bankTransactionsDetailsDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setEntityConverter(
			EntityConverter<BankTransactionsDetailsEntity, BankTransactionsDetailsBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	public BankTransactionsDetailsJpa() {
		super();
	}

	@Override
	public BankTransactionsDetailsBoundary createBankTransactionsDetails(
			BankTransactionsDetailsBoundary bankTransactionsDetails) {
		utils.assertNull(bankTransactionsDetails);
		utils.assertNull(bankTransactionsDetails.getBankId());
		utils.assertNull(bankTransactionsDetails.getAmount());
		utils.assertNull(bankTransactionsDetails.getBankBranch());
		utils.assertNull(bankTransactionsDetails.getBankAccountNumber());
		utils.assertNull(bankTransactionsDetails.getCategoryId());
		utils.assertNull(bankTransactionsDetails.getDate());
		
		
		
		

		BankTransactionsDetailsEntity entity = this.entityConverter.fromBoundary(bankTransactionsDetails);
		entity = this.bankTransactionsDetailsDao.save(entity);

		return this.entityConverter.toBoundary(entity);
	}


}
