package ru.bevz;

import java.time.LocalDate;
import java.util.UUID;

//Информация о действиях совершенных над заявкой
public class Action {
	private UUID actionId;
	private String employeeId;
	private ActionType actionType;
	private LocalDate executionTime;

	public Action() {
	}

	public Action(UUID actionId, String employeeId, ActionType actionType, LocalDate executionTime) {
		this.actionId = actionId;
		this.employeeId = employeeId;
		this.actionType = actionType;
		this.executionTime = executionTime;
	}

	public UUID getActionId() {
		return actionId;
	}

	public void setActionId(UUID actionId) {
		this.actionId = actionId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public LocalDate getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(LocalDate executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		return "Action{" +
						"actionId=" + actionId +
						", employeeId='" + employeeId + '\'' +
						", actionType=" + actionType +
						", executionTime=" + executionTime +
						'}';
	}
}
