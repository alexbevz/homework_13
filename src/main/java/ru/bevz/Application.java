package ru.bevz;

import java.util.List;
import java.util.UUID;

//Информация о заявке клиента
public class Application {
	private UUID id;
	private String clientId;
	private List<Action> actions;
	private List<Product> products;

	public Application() {
	}

	public Application(UUID id, String clientId, List<Action> actions, List<Product> products) {
		this.id = id;
		this.clientId = clientId;
		this.actions = actions;
		this.products = products;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Application{" +
						"id=" + id +
						", clientId='" + clientId + '\'' +
						", actions=" + actions +
						", products=" + products +
						'}';
	}

	//generate getters, setters and constructors
}