package ru.bevz;

import lombok.Data;

//Информация о продуктах прикрепленных к заявке
@Data
public class Product {
	private String productId;
	private String description;

}
