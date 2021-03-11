package ru.bevz;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Action {
	private UUID actionId;
	private String employeeId;
	private ActionType actionType;
	private LocalDate executionTime;

}
