package ru.bevz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class LoaderService {
	private static final String ERROR_TITLE = "Ошибка";

	public static String getConfigPath() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = "";
		try {
			path = Objects.requireNonNull(classLoader.getResource("config.properties")).getPath();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(
							null,
							"Чтение по null!\nВернётся пустая строка!",
							ERROR_TITLE,
							JOptionPane.ERROR_MESSAGE
			);
		}
		return path;
	}

	public Map<UUID, Application> readStorageFromDB(String filepath) {
		File json = new File(filepath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, new TypeReference<List<Application>>() {

			}).stream().collect(Collectors.toMap(Application::getId, Function.identity()));
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

	public String getDBPath() {
		return getValueFromConfigByKey("db.path");
	}

	public String getValueFromConfigByKey(String key) {
		Properties property = new Properties();
		String value = "";
		try (FileInputStream fis = new FileInputStream(getConfigPath())) {
			property.load(fis);
			value = property.getProperty(key);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(
							null,
							"Файл конфигурации не найден!\nВернётся пустая строка!",
							ERROR_TITLE,
							JOptionPane.ERROR_MESSAGE
			);
		} catch (SecurityException e) {
			JOptionPane.showMessageDialog(
							null,
							"Нет доступа к файлу конфигурации!\nВернётся пустая строка!",
							ERROR_TITLE,
							JOptionPane.ERROR_MESSAGE
			);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
							null,
							"Ошибка получения данных!\nВернётся пустая строка!",
							ERROR_TITLE,
							JOptionPane.ERROR_MESSAGE
			);
		}
		return value;
	}
}
