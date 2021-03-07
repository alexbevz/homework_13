package ru.bevz;

import java.time.LocalDate;
import java.util.UUID;

//Информация о действиях совершенных над заявкой
public class Action {
	private UUID actionId;
	private String employeeId;
	private ActionType actionType;
	private LocalDate executionTime;

	//generate getters, setters and constructors
}
