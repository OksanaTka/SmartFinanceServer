package finance.logic.converters;

import finance.data.PensionDetailsEntity;

public interface ExtendedEntityConverter<E, B> extends EntityConverter<E, B> {
	
	public E fromDetails(PensionDetailsEntity entity);
}
