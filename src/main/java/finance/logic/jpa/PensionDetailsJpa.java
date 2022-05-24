package finance.logic.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<PensionDetailsBoundary> createPensionDetails(List<PensionDetailsBoundary> pensionDetailsBoundary) {
		
		List<PensionDetailsEntity> listEntity = new ArrayList<>();
		for (PensionDetailsBoundary boundary : pensionDetailsBoundary) {
			utils.assertNull(boundary);
			utils.assertNull(boundary.getFundId());
			utils.assertNull(boundary.getCode());
			utils.assertNull(boundary.getPhone());
			utils.assertNull(boundary.getIdentityNumber());
			
			//check if PensionAccountDetails already exists
			List<PensionDetailsEntity> pensionDetails = this.pensionDetailsDao
					.findAllByFundIdAndIdentityNumber(boundary.getFundId(),
							boundary.getIdentityNumber());
			
			if (!pensionDetails.isEmpty()) {
				throw new UnsupportedExecption("Pension Details already exists");
			}
			
			PensionDetailsEntity entity = this.entityConverter.fromBoundary(boundary);
			entity = this.pensionDetailsDao.save(entity);
			listEntity.add(entity);
		}
		return listEntity.stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
	}

}
