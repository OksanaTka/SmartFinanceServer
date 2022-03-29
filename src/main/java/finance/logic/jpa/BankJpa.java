package finance.logic.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finance.boundaries.BankBoundary;
import finance.data.BankEntity;
import finance.data.dao.BankDao;
import finance.logic.BankService;
import finance.logic.converters.EntityConverter;
import finance.utils.ConflictException;
import finance.utils.Utils;

@Service
public class BankJpa implements BankService {

	private BankDao bankDao;
	private Utils utils;
	private EntityConverter<BankEntity, BankBoundary> entityConverter;

	public BankJpa() {
		super();
	}

	@Autowired
	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<BankEntity, BankBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	@Transactional
	public BankBoundary createBank(BankBoundary bank) {
		utils.assertNull(bank);
		utils.assertNull(bank.getBankId());
		utils.assertNull(bank.getBankName());
		utils.assertNull(bank.getBankIcon());
		
		List<BankEntity> banks = this.bankDao.findAllByBankId(bank.getBankId());
		
		if(!banks.isEmpty()) {
			throw new ConflictException("Bank already exists " + bank.getBankId());
		}

		BankEntity entity = this.entityConverter.fromBoundary(bank);
		entity.setBankId(bank.getBankId());
		entity.setBankName(bank.getBankName());
		entity.setBankIcon(bank.getBankIcon());

		entity = this.bankDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BankBoundary> getAllBanks(int page, int size) {
		return this.bankDao.findAll(PageRequest.of(page, size, Direction.ASC, "bankId")).getContent().stream()
				.map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}
