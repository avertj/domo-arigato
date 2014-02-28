package field;

import lejos.geom.Point;

public class Field {
	private Point[] positions;

	private static Field INSTANCE;

	private Field() {
		this.positions = new Point[9];

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				this.positions[y * 3 + x] = new Point(30 + x * 60, 50 + y * 50);
				// System.out.println(this.positions[y*3+x].toString());
			}
		}
	}

	public Point getPuck(EnumPuck p) {
		return this.positions[p.getNum()];
	}

	public static Field getInstance() {
		return INSTANCE;
	}
}
