package ru.bevz;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Application {
	private UUID id;
	private String clientId;
	private List<Action> actions;
	private List<Product> products;

}