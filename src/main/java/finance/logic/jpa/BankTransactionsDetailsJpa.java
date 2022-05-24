package finance.logic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<BankTransactionsDetailsBoundary> createBankTransactionsDetails(
			List<BankTransactionsDetailsBoundary> bankTransactionsDetails) {
		
		List<BankTransactionsDetailsEntity> entityList = new ArrayList<>();
		for (BankTransactionsDetailsBoundary boundary : bankTransactionsDetails) {
			utils.assertNull(boundary);
			utils.assertNull(boundary.getBankId());
			utils.assertNull(boundary.getAmount());
			utils.assertNull(boundary.getBankBranch());
			utils.assertNull(boundary.getBankAccountNumber());
			utils.assertNull(boundary.getCategoryId());
			utils.assertNull(boundary.getDate());

			BankTransactionsDetailsEntity entity = this.entityConverter.fromBoundary(boundary);
			entity = this.bankTransactionsDetailsDao.save(entity);
			entityList.add(entity);
		}
		return entityList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}
