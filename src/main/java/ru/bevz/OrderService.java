package ru.bevz;

import java.util.*;

public class OrderService {

	private final Map<UUID, Application> storage = Collections.emptyMap();

	//найти все заявки по clientId
	public List<Application> findApplicationsByClientId(String clientId) {
		throw new UnsupportedOperationException();
	}

	//найти всех заявки у которых нету продуктов
	public List<Application> findApplicationsWithoutProducts() {
		throw new UnsupportedOperationException();
	}

	//найти все действия которые были совершенны указанным сотрудником
	public List<Action> getActionsByEmployee(String employeeId) {
		throw new UnsupportedOperationException();
	}

	//найти все заявки у которых есть Action с указанным типом
	public List<Application> findApplicationsByActionType(ActionType actionType) {
		throw new UnsupportedOperationException();
	}

	//найти все продукты, у которых в описании (description) содержится указанное слово
	public List<Product> findProductsByDescription(String description) {
		throw new UnsupportedOperationException();
	}

	//найти всех клиентов, у которых отсутствует действие с типом DELETE и количество продуктов более одного
	public Set<String> findClientsByActionAndProducts() {
		throw new UnsupportedOperationException();
	}

	//найти все заявки, которые были созданы неделю назад (есть тип CREATE, но нет типа DElETE)
	public List<Application> findNewApplications() {
		throw new UnsupportedOperationException();
	}

	//найти все продукты клиента
	public List<Product> findClientProducts(String clientId) {
		throw new UnsupportedOperationException();
	}

	//найти список всех сотрудников и их самых частых действий (пример, если сотрудник 1 выполнил 2 UPDATE и 1 READ - то возвращаем 1 + UPDATE)
	public Map<String, ActionType> findEmployeeActions() {
		throw new UnsupportedOperationException();
	}
}
