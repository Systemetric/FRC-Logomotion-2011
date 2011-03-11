package org.usfirst.systemetric.controllers;

import org.usfirst.systemetric.OperatorConsole;
import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.util.VectorSmoother;

import edu.wpi.first.wpilibj.GenericHID;

public class StrafeDriveController implements Controller {
	/** The speed to run the robot at when the trigger is pressed */
	public static final double CRAWL_FACTOR  = 0.5;
	public static final double ZOOM_FACTOR  = 1.5;

	/**
	 * Radius of the circular zone in the middle of the Joystick in which no
	 * speed is set
	 */

	public static final double DEAD_ZONE     = 0.05;

	/**
	 * The higher this value the faster the acceleration. Between 1 and 0
	 */
	public static final double SMOOTH_FACTOR = 0.1;

	MecanumDrive               drive;
	VectorSmoother             smoother;

	public StrafeDriveController(MecanumDrive drive) {
		this.drive = drive;
		smoother = new VectorSmoother(SMOOTH_FACTOR);
	}

	private Vector addDeadZone(Vector vector) {
		double magnitude = vector.length();

		if (magnitude < DEAD_ZONE) {
			return Vector.ZERO;
		} else {
			// The vector to subtract from the drive direction to account for
			// the dead zone
			Vector deadZoneOffset = vector.unit().times(DEAD_ZONE);

			// Amount to multiply by to ensure that after subtraction, the
			// vector can still hit the maximum of (1,1)
			double multiplyFactor = magnitude / (1 - DEAD_ZONE);

			return vector.minus(deadZoneOffset).times(multiplyFactor);
		}
	}
	
	private double addDeadZone(double d) {
		double magnitude = Math.abs(d);

		if (d > DEAD_ZONE) {
			return (d - DEAD_ZONE) / (1 - DEAD_ZONE);
		} else if (d < -DEAD_ZONE) {
			return (d + DEAD_ZONE) / (1 - DEAD_ZONE);
		} else {
			return 0;
		}
	}

	public void controlWith(OperatorConsole cb) {
		GenericHID joystick = cb.driveJoystick;

		// Get information from joystick
		Vector driveVector = new Vector(-joystick.getX(), joystick.getY());
		double turnSpeed = -joystick.getTwist();

		driveVector = addDeadZone(driveVector);
		turnSpeed = addDeadZone(turnSpeed);

		// If trigger is pressed, use fine control
		if (joystick.getTrigger())
			driveVector = driveVector.times(CRAWL_FACTOR);
		else if(joystick.getRawButton(2))
			driveVector = driveVector.times(ZOOM_FACTOR);
			

		driveVector = smoother.smooth(driveVector);

		drive.set(driveVector, turnSpeed);
	}

}