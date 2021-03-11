package ru.bevz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


public class TestOrderService {

	private static Stream<Arguments> provideDataForTestFindApplicationsByClientId() {
		String filepath = "D:\\Aleksandr\\Study\\IT\\IdeaProjects\\homework_13\\src\\test\\resources\\test1.json";
		Stream<Arguments> str = Stream.of(Arguments.of(12));
		List<UUID> data = new ArrayList<UUID>();
		data.add(UUID.fromString("c21d07e7-fe0f-4226-a974-da447217d949"));
		return Stream.of(
						Arguments.of(
										LoaderService.readStorageFromDB(filepath),
										"33333",
										new ArrayList<UUID>()
						),
						Arguments.of(
										LoaderService.readStorageFromDB(filepath),
										"333333",
										data
						)
		);
	}

	private static Stream<Arguments> provideDataForTestFindApplicationsWithoutProducts() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestGetActionsByEmployee() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindApplicationsByActionType() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindProductsByDescription() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindClientsByActionAndProducts() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindNewApplications() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindClientProducts() {
		return Stream.of(
						Arguments.of()
		);
	}

	private static Stream<Arguments> provideDataForTestFindEmployeeActions() {
		return Stream.of(

		);
	}

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

	@ParameterizedTest
	@MethodSource("provideDataForTestFindApplicationsByClientId")
	public void TestFindApplicationsByClientId(HashMap<UUID, Application> storage, String value, ArrayList<UUID> expected) throws Exception {
		OrderService orderService = new OrderService();
		Field fieldStorage = orderService.getClass().getDeclaredField("storage");
		setAbsolutelyAccessible(fieldStorage);

		fieldStorage.set(orderService, storage);

		List<UUID> listUUID = new ArrayList<>();
		for (Application app : orderService.findApplicationsByClientId(value)) {
			listUUID.add(app.getId());
		}
		Assertions.assertEquals(listUUID, expected);
	}
}
