package finance.logic.converters;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
	//	boundary.setDate(localDateTimeAttributeConverter.convertToEntityAttribute(entity.getDate()));
		boundary.setDate(entity.getDate());
		boundary.setTransactionType(entity.getTransactionType());
		boundary.setUserId(entity.getUserId());
		boundary.setTransactionId(entity.getTransactionId());
		return boundary;
	}

	@Override
	public TransactionEntity fromBoundary(TransactionBoundary boundary) {
		TransactionEntity entity = new TransactionEntity();
		entity.setUserId(boundary.getUserId());
		entity.setBankAccountId(boundary.getBankAccountId());
		entity.setTransactionType(boundary.getTransactionType());
		//entity.setDate(localDateTimeAttributeConverter.convertToDatabaseColumn(boundary.getDate()));
		entity.setDate(boundary.getDate());
		entity.setAmount(boundary.getAmount());
		entity.setCategoryId(boundary.getCategoryId());
		return entity;
	}
	

}


