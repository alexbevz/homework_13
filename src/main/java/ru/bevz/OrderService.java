package ru.bevz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class OrderService {
	private final Map<UUID, Application> storage = new HashMap<>();

	public OrderService() throws IOException {
		readStorageFromDB();
	}

	public static void main(String[] args) throws IOException {
		OrderService os = new OrderService();
		os.readStorageFromDB();
		System.out.println(os.findApplicationsByClientId("333333"));
		System.out.println(os.findApplicationsByActionType(ActionType.DELETE));
		System.out.println(os.findApplicationsWithoutProducts());
		System.out.println(os.findClientProducts("111111"));
		System.out.println(os.findClientsByActionAndProducts());
		System.out.println(os.findEmployeeActions());
		System.out.println(os.findNewApplications());
		System.out.println(os.findProductsByDescription("карта"));
		System.out.println(os.getActionsByEmployee("first"));
	}

	public void readStorageFromDB() throws IOException {
		String filepath = "D:\\Aleksandr\\Study\\IT\\IdeaProjects\\homework_13\\src\\main\\resources\\applications.json";
		File json = new File(filepath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Application> applications = mapper.readValue(json, new TypeReference<List<Application>>() {
		});

		for (Application app : applications) {
			storage.put(app.getId(), app);
		}
	}

	//найти все заявки по clientId
	public List<Application> findApplicationsByClientId(String clientId) {
		List<Application> applications = new ArrayList<>();
		for (Application app : storage.values()) {
			if (app.getClientId().equals(clientId)) {
				applications.add(app);
			}
		}
		return applications;
	}

	//найти всех заявки у которых нету продуктов
	public List<Application> findApplicationsWithoutProducts() {
		List<Application> applications = new ArrayList<>();
		for (Application app : storage.values()) {
			if (app.getProducts().isEmpty()) {
				applications.add(app);
			}
		}
		return applications;
	}

	//найти все действия которые были совершенны указанным сотрудником
	public List<Action> getActionsByEmployee(String employeeId) {
		List<Action> actions = new ArrayList<>();
		for (Application app : storage.values()) {
			for (Action act : app.getActions()) {
				if (act.getEmployeeId().equals(employeeId)) {
					actions.add(act);
				}
			}
		}
		return actions;
	}

	//найти все заявки у которых есть Action с указанным типом
	public List<Application> findApplicationsByActionType(ActionType actionType) {
		List<Application> applications = new ArrayList<>();
		for (Application app : storage.values()) {
			for (Action act : app.getActions()) {
				if (act.getActionType().equals(actionType)) {
					applications.add(app);
					break;
				}
			}
		}
		return applications;
	}

	//найти все продукты, у которых в описании (description) содержится указанное слово
	public List<Product> findProductsByDescription(String description) {
		List<Product> products = new ArrayList<>();

		for (Application app : storage.values()) {
			for (Product prod : app.getProducts()) {
				if (prod.getDescription().indexOf(description) != -1) {
					products.add(prod);
				}
			}
		}
		return products;
	}

	//найти всех клиентов, у которых отсутствует действие с типом DELETE и количество продуктов более одного
	// TODO
	public Set<String> findClientsByActionAndProducts() {
		Set<String> clients = new HashSet<>();
		for (Application app : storage.values()) {
			if (app.getProducts().size() > 1) {
				for (Action act : app.getActions()) {
					if (!act.getActionType().equals(ActionType.DELETE)) {
						clients.add(app.getClientId());
						break;
					}
				}
			}
		}
		return clients;
	}

	//найти все заявки, которые были созданы неделю назад (есть тип CREATE, но нет типа DElETE)
	public List<Application> findNewApplications() {
		List<Application> applications = new ArrayList<>();
		for (Application app : storage.values()) {
			boolean flagDelete = false;
			boolean flagCreate = false;
			for (Action act : app.getActions()) {
				if (act.getActionType().equals(ActionType.DELETE)) {
					flagDelete = true;
					break;
				} else if (!flagCreate && act.getExecutionTime().equals(LocalDate.now().minusWeeks(1)) && act.getActionType().equals(ActionType.CREATE)) {
					flagCreate = true;
				}
			}
			if (flagCreate && !flagDelete) {
				applications.add(app);
			}
		}
		return applications;
	}

	//найти все продукты клиента
	public List<Product> findClientProducts(String clientId) {
		List<Product> products = new ArrayList<>();
		for (Application app : storage.values()) {
			if (app.getClientId().equals(clientId)) {
				products.addAll(app.getProducts());
			}
		}
		return products;
	}

	//найти список всех сотрудников и их самых частых действий (пример, если сотрудник 1 выполнил 2 UPDATE и 1 READ - то возвращаем 1 + UPDATE)
	// TODO
	public Map<String, ActionType> findEmployeeActions() {
		Map<String, ActionType> employees = new HashMap<>();
		Set<String> setEmployees = new HashSet<>();
		for (Application app : storage.values()) {
			if (setEmployees.contains(app.getClientId())) {
				setEmployees.add(app.getClientId());
				TreeMap<ActionType, Integer> count = new TreeMap<>();
				count.put(ActionType.CREATE, 0);
				count.put(ActionType.UPDATE, 0);
				count.put(ActionType.READ, 0);
				count.put(ActionType.DELETE, 0);
				for (Application appEmp : storage.values()) {
					if (appEmp.getClientId().equals(app.getClientId())) {
						for (Action act : app.getActions()) {
							count.put(act.getActionType(), count.get(act.getActionType()) + 1);
						}
					}
				}
				employees.put(app.getClientId(), count.firstKey());
			}
		}
		return employees;
	}
}
