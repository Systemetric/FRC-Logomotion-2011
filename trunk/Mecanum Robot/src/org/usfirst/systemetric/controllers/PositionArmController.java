package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.robotics.PositionControlledArm;
import org.usfirst.systemetric.robotics.PositionControlledArm.PegPosition;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

public class PositionArmController {
	PositionControlledArm arm;
	PegPosition           position;

	public PositionArmController(PositionControlledArm arm) {
		this.arm = arm;
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.armJoystick;

		if (joystick.getRawButton(11)) {
			position = PegPosition.BOTTOM;
		} else if (joystick.getRawButton(12)) {
			position = PegPosition.BOTTOM_OFFSET;
		} else if (joystick.getRawButton(9)) {
			position = PegPosition.MIDDLE;
		} else if (joystick.getRawButton(10)) {
			position = PegPosition.MIDDLE_OFFSET;
		}		
		try {
			arm.moveTo(position);
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}