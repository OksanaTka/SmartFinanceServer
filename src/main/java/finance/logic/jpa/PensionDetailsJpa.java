package finance.logic.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finance.boundaries.PensionDetailsBoundary;
import finance.data.PensionDetailsEntity;
import finance.data.dao.PensionDetailsDao;
import finance.logic.PensionDetailsService;
import finance.logic.converters.EntityConverter;
import finance.utils.UnsupportedExecption;
import finance.utils.Utils;

@Service
public class PensionDetailsJpa implements PensionDetailsService{
	
	private Utils utils;
	private PensionDetailsDao pensionDetailsDao;
	private EntityConverter< PensionDetailsEntity, PensionDetailsBoundary> entityConverter;

	public PensionDetailsJpa() {
		super();
	}

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Autowired
	public void setPensionDetailsDao(PensionDetailsDao pensionDetailsDao) {
		this.pensionDetailsDao = pensionDetailsDao;
	}

	@Autowired
	public void setEntityConverter(EntityConverter<PensionDetailsEntity, PensionDetailsBoundary> entityConverter) {
		this.entityConverter = entityConverter;
	}

	@Override
	public PensionDetailsBoundary createPensionDetails(PensionDetailsBoundary pensionDetailsBoundary) {
		utils.assertNull(pensionDetailsBoundary);
		utils.assertNull(pensionDetailsBoundary.getFundId());
		utils.assertNull(pensionDetailsBoundary.getCode());
		utils.assertNull(pensionDetailsBoundary.getPhone());
		utils.assertNull(pensionDetailsBoundary.getIdentityNumber());
		
		//check if PensionAccountDetails already exists
		List<PensionDetailsEntity> pensionDetails = this.pensionDetailsDao
				.findAllByFundIdAndIdentityNumber(pensionDetailsBoundary.getFundId(),
						pensionDetailsBoundary.getIdentityNumber());
		
		if (!pensionDetails.isEmpty()) {
			throw new UnsupportedExecption("Pension Details already exists");
		}
		
		PensionDetailsEntity entity = this.entityConverter.fromBoundary(pensionDetailsBoundary);
		entity = this.pensionDetailsDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

}
