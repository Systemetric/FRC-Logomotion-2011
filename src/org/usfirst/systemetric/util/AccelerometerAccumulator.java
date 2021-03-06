package org.usfirst.systemetric.util;

import org.usfirst.systemetric.geometry.Vector;
import org.usfirst.systemetric.sensors.ADXL345_I2C;

/**
 * @author Louis
 * 
 */
public class AccelerometerAccumulator {

	ADXL345_I2C accelerometer;
	long        lastTime;
	Vector      accelerationOffset;

	Vector      acceleration;
	Vector      velocity;
	Vector      position;

	public AccelerometerAccumulator(ADXL345_I2C accelerometer) {
		this.accelerometer = accelerometer;
		reset();
		setPosition(Vector.ZERO);
	}

	public void update() {
		long currentTime = System.currentTimeMillis();

		// Get the time since the last update, in seconds
		double dt = (currentTime - lastTime) / 1000.0;
		lastTime = currentTime;

		acceleration = accelerometer.getHorizontalAcceleration().minus(accelerationOffset);

		velocity = velocity.plus(acceleration.times(dt));
		position = position.plus(velocity.times(dt));
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public Vector getPosition() {
		return position;
	}

	public void reset() {
		velocity = Vector.ZERO;
		accelerationOffset = accelerometer.getHorizontalAcceleration();
		lastTime = System.currentTimeMillis();
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
}
