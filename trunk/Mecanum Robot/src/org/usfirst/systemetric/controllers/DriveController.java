package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.ControlBoard;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;

import edu.wpi.first.wpilibj.GenericHID;

public class DriveController implements Controllable {
	MecanumDrive drive;


	public DriveController(MecanumDrive drive) {
		this.drive = drive;
	}

	public void controlWith(ControlBoard cb) {
		GenericHID joystick = cb.driveJoystick;
		
		Vector driveVector = new Vector(
				joystick.getX(),
				joystick.getY());

		// If trigger is pressed, use fine control
		if (joystick.getTrigger())
			driveVector = driveVector.times(0.5);
		
		drive.setDriveVelocity(driveVector);

	}

}
