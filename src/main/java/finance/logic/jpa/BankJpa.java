package finance.logic.jpa;

import java.util.ArrayList;
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
	public List<BankBoundary> createBank(List<BankBoundary> banks) {
		
		List<BankEntity> entityList = new ArrayList<>();
		for (BankBoundary bankBoundary : banks) {
			utils.assertNull(bankBoundary);
			utils.assertNull(bankBoundary.getBankId());
			utils.assertNull(bankBoundary.getBankName());
			utils.assertNull(bankBoundary.getBankIcon());
			
			List<BankEntity> entityBanks = this.bankDao.findAllByBankId(bankBoundary.getBankId());
			
			if(!entityBanks.isEmpty()) {
				throw new ConflictException("Bank already exists " + bankBoundary.getBankId());
			}

			BankEntity entity = this.entityConverter.fromBoundary(bankBoundary);
			entity.setBankId(bankBoundary.getBankId());
			entity.setBankName(bankBoundary.getBankName());
			entity.setBankIcon(bankBoundary.getBankIcon());
			entity = this.bankDao.save(entity);
			entityList.add(entity);
		}
		return entityList.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<BankBoundary> getAllBanks(int page, int size) {
		return this.bankDao.findAll(PageRequest.of(page, size, Direction.ASC, "bankId")).getContent().stream()
				.map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}
