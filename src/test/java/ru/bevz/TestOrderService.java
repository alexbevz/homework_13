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
import java.util.stream.Stream;

//TODO: реализовать все тесты для методов класса OrderService
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
			//TODO: решить проблему чтения всех файлов,
			return mapper.readValue(
							json,
							new TypeReference<Map<String, List<DataTestOrderService>>>() {
							}
			);
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

	private static Stream<Arguments> provideDataForTestFindApplicationsByClientId() {
		Stream<Arguments> stream = Stream.of();
		for (DataTestOrderService data : data.get(Thread.currentThread().getStackTrace()[1].getMethodName())) {
			stream = Stream.concat(
							stream,
							Stream.of(Arguments.of(
											data.storage,
											data.expectedResult,
											data.values.get(0)
											//TODO: автоматизировать передачу values отдельными значениями аргументов
							)));
		}
		return stream;
	}

	@ParameterizedTest
	@MethodSource("provideDataForTestFindApplicationsByClientId")
	void TestFindApplicationsByClientId(
					HashMap<UUID, Application> storage,
					ArrayList<Object> expected,
					String clientId
	) throws Exception {
		OrderService orderService = new OrderService();
		Field fieldStorage = orderService.getClass().getDeclaredField("storage");

		setAbsolutelyAccessible(fieldStorage);
		fieldStorage.set(orderService, storage);

		List<String> listUUID = new ArrayList<>();
		for (Application app : orderService.findApplicationsByClientId(clientId)) {
			listUUID.add(app.getId().toString());
		}

		Assertions.assertEquals(expected, listUUID);
	}
}
