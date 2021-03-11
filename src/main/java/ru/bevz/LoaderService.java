package ru.bevz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoaderService {

	public static Map<UUID, Application> readStorageFromDB(String filepath) {
		File json = new File(filepath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, new TypeReference<List<Application>>() {

			}).stream().collect(Collectors.toMap(Application::getId, Function.identity()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
							null,
							"Файл отсутствует или нет прав доступа!\nВернёться new HashMap<>()!",
							"Ошибка",
							JOptionPane.ERROR_MESSAGE
			);
		}
		return new HashMap<>();
	}
}
