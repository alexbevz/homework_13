package ru.bevz;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class DataTestOrderService {
	Map<UUID, Application> storage;
	Object expectedResult;
	Object values;
}