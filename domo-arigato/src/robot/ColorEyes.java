package robot;

import actions.RunnableRobot;
import lejos.nxt.I2CSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

public class ColorEyes {
	public ColorHTSensor cs;
	RunnableRobot thread;
	I2CSensor sensor;
	SensorPort eyes;
	
	LightSensor ls;
	
	ColorEyes() {
		thread = null;
	}
	
	void initEyes(SensorPort eyes) {
		this.eyes = eyes;
		/*byte buf[] = new byte [1];
		sensor = new I2CSensor(eyes, 0x02, I2CPort.STANDARD_MODE, I2CSensor.TYPE_LOWSPEED);
		System.out.println(sensor.getData(0x42, buf, 1));
		System.out.println(buf[0]);
		Delay.msDelay(100);
		System.out.println(sensor.getData(0x42, buf, 1));
		System.out.println(buf[0]);*//*
		cs = new ColorHTSensor(eyes);*/
		eyes.activate();
		ls = new LightSensor(eyes);
		ls.setFloodlight(true);

		thread = new EyesRun(this, eyes);
		Thread t = new Thread(thread);
		t.start();
		/*System.out.println(ls.getLightValue());
		System.out.println(eyes.readRawValue());
		Delay.msDelay(1000);
		System.out.println(ls.getLightValue());
		System.out.println(eyes.readRawValue());
		Delay.msDelay(1000);
		System.out.println(ls.getLightValue());
		System.out.println(eyes.readRawValue());*/

	}
	
	public boolean onNoise() {
		int read = eyes.readRawValue();
		return ((read >= EyesRun.LOW_COLORS) && (read <= EyesRun.HIGH_COLORS));
	}
	
	synchronized public int getLightValue() {
		return eyes.readRawValue();
	}
	
	synchronized public lejos.robotics.Color getColor() {
		return cs.getColor();
	}
	
	public void closeEyes() {
		if(thread != null)
			thread.interrupt();
	}
}
