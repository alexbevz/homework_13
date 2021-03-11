package ru.bevz;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class OrderService {
	private final Map<UUID, Application> storage;
	private final String filepath;

	public OrderService() {
		filepath = "D:\\Aleksandr\\Study\\IT\\IdeaProjects\\homework_13\\src\\main\\resources\\applications.json";
		storage = LoaderService.readStorageFromDB(filepath);
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
				if (prod.getDescription().contains(description)) {
					products.add(prod);
				}
			}
		}
		return products;
	}

	//найти всех клиентов, у которых отсутствует действие с типом DELETE и количество продуктов более одного
	public Set<String> findClientsByActionAndProducts() {
		Set<String> clients = new HashSet<>();
		Map<String, Integer> countProd = new HashMap<>();
		for (Application app : storage.values()) {
			if (!countProd.containsKey(app.getClientId())) {
				countProd.put(app.getClientId(), 0);
				clients.add(app.getClientId());
			}
			countProd.put(app.getClientId(), countProd.get(app.getClientId()) + app.getProducts().size());
			for (Action act : app.getActions()) {
				if (act.getActionType().equals(ActionType.DELETE)) {
					clients.remove(app.getClientId());
				}
			}
		}
		for (Map.Entry<String, Integer> cln : countProd.entrySet()) {
			if (countProd.get(cln.getKey()) < 2) {
				clients.remove(cln.getKey());
			}
		}
		//countProd.entrySet().stream().filter(x -> x.getValue() < 2).forEach(x -> clients.remove(x.getKey()));
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
	public Map<String, ActionType> findEmployeeActions() {
		Map<String, ActionType> employees = new HashMap<>();
		Map<String, Map<ActionType, Integer>> empMerits = new HashMap<>();
		for (Application app : storage.values()) {
			for (Action act : app.getActions()) {
				if (!empMerits.containsKey(act.getEmployeeId())) {
					Map<ActionType, Integer> merits = new EnumMap<>(ActionType.class);
					for (ActionType at : ActionType.values()) {
						merits.put(at, 0);
					}
					empMerits.put(act.getEmployeeId(), merits);
				}
				empMerits.get(act.getEmployeeId()).put(act.getActionType(), empMerits.get(act.getEmployeeId()).get(act.getActionType()) + 1);
			}
		}
		for (Map.Entry<String, Map<ActionType, Integer>> emp : empMerits.entrySet()) {
			ActionType max = ActionType.READ;
			for (ActionType at : empMerits.get(emp.getKey()).keySet()) {
				if (empMerits.get(emp.getKey()).get(max) < empMerits.get(emp.getKey()).get(at)) {
					max = at;
				}
			}
			employees.put(emp.getKey(), max);
		}
		return employees;
	}
}
