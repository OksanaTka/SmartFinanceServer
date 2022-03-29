package finance.logic.converters;

import org.springframework.stereotype.Component;
import finance.boundaries.TransactionBoundary;
import finance.data.TransactionEntity;

@Component
public class TransactionEntityConverterImplementation implements EntityConverter<TransactionEntity, TransactionBoundary>{
	
	@Override
	public TransactionBoundary toBoundary(TransactionEntity entity) {
		TransactionBoundary boundary = new TransactionBoundary();
		boundary.setAmount(entity.getAmount());
		boundary.setBankAccountId(entity.getBankAccountId());
		boundary.setCategoryId(entity.getCategoryId());
		boundary.setDate(entity.getDate());
		boundary.setUserId(entity.getUserId());
		boundary.setTransactionId(entity.getTransactionId());
		return boundary;
	}

	@Override
	public TransactionEntity fromBoundary(TransactionBoundary boundary) {
		TransactionEntity entity = new TransactionEntity();
		entity.setUserId(boundary.getUserId());
		entity.setBankAccountId(boundary.getBankAccountId());
		entity.setDate(boundary.getDate());
		entity.setAmount(boundary.getAmount());
		entity.setCategoryId(boundary.getCategoryId());
		return entity;
	}


}

