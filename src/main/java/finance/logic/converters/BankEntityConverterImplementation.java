package finance.logic.converters;

import org.springframework.stereotype.Component;

import finance.boundaries.BankBoundary;
import finance.data.BankEntity;

@Component
public class BankEntityConverterImplementation implements EntityConverter<BankEntity, BankBoundary> {

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
