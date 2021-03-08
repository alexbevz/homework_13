package ru.bevz;

//Информация о продуктах прикрепленных к заявке
public class Product {
	private String productId;
	private String description;

	//generate getters, setters and constructors

	public Product() {
	}

	public Product(String productId, String description) {
		this.productId = productId;
		this.description = description;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product{" +
						"productId='" + productId + '\'' +
						", description='" + description + '\'' +
						'}';
	}
}
