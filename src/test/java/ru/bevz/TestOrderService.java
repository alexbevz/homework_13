package ru.bevz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: составить выборку для тестирования
class TestOrderService {
	private static final String ERROR_TITLE = "Ошибка";
	private static final Map<String, List<DataTestOrderService>> data = readDataForTest();

	private static void setAbsolutelyAccessible(Field field) {
		try {
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// ignored exceptions
		}
	}

	private static Map<String, List<DataTestOrderService>> readDataForTest() {
		File json = new File(LoaderService.getValueFromConfigByKey("file-test.path"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, new TypeReference<Map<String, List<DataTestOrderService>>>() {
			});
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
							null,
							"Файл отсутствует или нет прав доступа!\nВернётся new HashMap<>()!",
							ERROR_TITLE,
							JOptionPane.ERROR_MESSAGE
			);
		}
		return new HashMap<>();
	}

	private static Stream<Arguments> provideDataForTestOrderServiceByMethodName(String name) {
		Stream<Arguments> stream = Stream.of();
		for (DataTestOrderService data : data.get(name)) {
			if (data.values == null) {
				stream = Stream.concat(
								stream,
								Stream.of(Arguments.of(
												data.storage,
												data.expectedResult
								)));
			} else {
				stream = Stream.concat(
								stream,
								Stream.of(Arguments.of(
												data.storage,
												data.expectedResult,
												data.values
								)));
			}
		}
		return stream;
	}

	private static Stream<Arguments> provideDataForTestFindApplicationsByClientId() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindApplicationsWithoutProducts() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestGetActionsByEmployee() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindApplicationsByActionType() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindProductsByDescription() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindClientsByActionAndProducts() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindNewApplications() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindClientProducts() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static Stream<Arguments> provideDataForTestFindEmployeeActions() {
		return provideDataForTestOrderServiceByMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private static void replaceStorage(OrderService orderService, Map<UUID, Application> storage) {
		try {
			Field fieldStorage = orderService.getClass().getDeclaredField("storage");
			setAbsolutelyAccessible(fieldStorage);
			fieldStorage.set(orderService, storage);
		} catch (Exception e) {
			// ignored exception
		}
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindApplicationsByClientId")
	void testFindApplicationsByClientId(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					HashMap<String, Object> values
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listUUID = orderService.findApplicationsByClientId((String) values.get("clientId"))
						.stream()
						.map(x -> x.getId().toString())
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listUUID);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindApplicationsWithoutProducts")
	void testFindApplicationsWithoutProducts(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listApplication = orderService.findApplicationsWithoutProducts()
						.stream()
						.map(x -> x.getId().toString())
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listApplication);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestGetActionsByEmployee")
	void testGetActionsByEmployee(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					HashMap<String, Object> values
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listActionId = orderService.getActionsByEmployee((String) values.get("employeeId"))
						.stream()
						.map(x -> x.getActionId().toString())
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listActionId);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindApplicationsByActionType")
	void testFindApplicationsByActionType(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					HashMap<String, Object> values
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listUUID = orderService.findApplicationsByActionType((ActionType) values.get("actionType"))
						.stream()
						.map(x -> x.getId().toString())
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listUUID);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindProductsByDescription")
	void testFindProductsByDescription(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					HashMap<String, Object> values
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listProductId = orderService.findProductsByDescription((String) values.get("description"))
						.stream()
						.map(Product::getProductId)
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listProductId);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindClientsByActionAndProducts")
	void testFindClientsByActionAndProducts(
					HashMap<UUID, Application> storage,
					Set<Object> expected
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		Set<String> listClients = orderService.findClientsByActionAndProducts();
		Assertions.assertEquals(expected, listClients);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindNewApplications")
	void testFindNewApplications(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listUUID = orderService.findNewApplications()
						.stream()
						.map(x -> x.getId().toString())
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listUUID);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindClientProducts")
	void testFindClientProducts(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					HashMap<String, Object> values
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		List<String> listProductId = orderService.findClientProducts((String) values.get("clientId"))
						.stream()
						.map(Product::getProductId)
						.collect(Collectors.toList());
		Assertions.assertEquals(expected, listProductId);
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindEmployeeActions")
	void testFindEmployeeActions(
					HashMap<UUID, Application> storage,
					HashMap<String, ActionType> expected
	) {
		OrderService orderService = new OrderService();
		replaceStorage(orderService, storage);

		Map<String, ActionType> mapEmployeeActions = orderService.findEmployeeActions();
		Assertions.assertEquals(expected, mapEmployeeActions);
	}
}

