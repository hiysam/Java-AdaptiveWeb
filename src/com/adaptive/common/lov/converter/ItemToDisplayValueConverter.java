package com.adaptive.common.lov.converter;

public interface ItemToDisplayValueConverter <T> {	
	Object convertToDisplayValue(T item);
}
