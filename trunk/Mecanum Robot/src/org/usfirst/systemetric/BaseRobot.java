package org.usfirst.systemetric;

import org.usfirst.systemetric.robotics.Arm;
import org.usfirst.systemetric.robotics.Grabber;
import org.usfirst.systemetric.robotics.navigation.MecanumDrive;
import org.usfirst.systemetric.sensors.LineSensor;
import org.usfirst.systemetric.sensors.LineSensor12V;
import org.usfirst.systemetric.sensors.LineTracer;
import org.usfirst.systemetric.util.OrthogonalMecanumDriveFactory;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.parsing.IMechanism;

/**
 * @author Eric
 * 
 * A singleton containing all the hardware used by the robot. Full of magic numbers for where everything is plugged in.
 */
public class BaseRobot implements IMechanism {
	private static BaseRobot instance;

	public Arm               arm;
	public Grabber           grabber;
	public MecanumDrive      drive;
	public Compressor        compressor;

	public LineTracer        lineSensor;
	
	public Timer             roundTimer;

	private BaseRobot() {
		// Create the driving robot
		drive = OrthogonalMecanumDriveFactory.DEFAULT_ROBOT;

		// Create the compressor
		compressor = new Compressor(1, 1);

		// Create the arm
		arm = new Arm(6);

		// Create the grabber
		grabber = new Grabber(2, 1);

		// Create the line sensor
		lineSensor = new LineTracer(new LineSensor[] {
			new LineSensor12V(15, 8),
		    new LineSensor12V(14, 7),
		    new LineSensor12V(13, 8),
		    new LineSensor12V(12, 6),
		    new LineSensor12V(11, 4),
		    new LineSensor12V(10, 5),
		    new LineSensor12V(9, 4)
		}, 35);
	}

	/**
	 * @return The single instance of BaseRobot
	 */
	public static BaseRobot getInstance() {
		return instance == null ? instance = new BaseRobot() : instance;
	}
}