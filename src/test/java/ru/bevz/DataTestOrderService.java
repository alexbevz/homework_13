package ru.bevz;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class DataTestOrderService {
	Map<UUID, Application> storage;
	List<Object> expectedResult;
	List<Object> values;
}