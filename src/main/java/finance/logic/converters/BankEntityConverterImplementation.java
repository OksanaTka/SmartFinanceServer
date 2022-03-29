package finance.logic.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import finance.boundaries.BankBoundary;
import finance.data.BankEntity;
import finance.utils.Utils;

@Component
public class BankEntityConverterImplementation implements EntityConverter<BankEntity, BankBoundary> {

	private Utils utils;

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	@Override
	public BankBoundary toBoundary(BankEntity entity) {
		BankBoundary bb = new BankBoundary();
		bb.setBankId(entity.getBankId());
		bb.setBankName(entity.getBankName());
		bb.setBankIcon(entity.getBankIcon());
		return bb;
	}

	@Override
	public BankEntity fromBoundary(BankBoundary boundary) {
		BankEntity be = new BankEntity();
		be.setBankId(boundary.getBankId());
		be.setBankName(boundary.getBankName());
		be.setBankIcon(boundary.getBankIcon());
		return be;
	}

}
