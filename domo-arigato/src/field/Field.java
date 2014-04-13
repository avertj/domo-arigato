package field;

import robot.Robot;
import lejos.geom.Point;

public class Field {
	private Point[] positions;

	private static Field INSTANCE;

	private Field() {
		this.positions = new Point[9];

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				this.positions[y * 3 + x] = new Point(x*50-50, y*60-60);
				// System.out.println(this.positions[y*3+x].toString());
			}
		}
	}

	

	public static Field getInstance() {
		return INSTANCE;
	}
}
