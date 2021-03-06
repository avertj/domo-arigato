package field;

import java.awt.Point;



public class Field {
	public static final int PUCK_RADIUS = 15;
	private Point[] positions;
	private boolean[] present;
	private EnumPuck[] pucks = {EnumPuck.NW, EnumPuck.N, EnumPuck.NE, EnumPuck.W, EnumPuck.M, EnumPuck.E, EnumPuck.SW, EnumPuck.S, EnumPuck.SE};

	private static Field INSTANCE = new Field();

	private Field() {
		this.positions = new Point[9];

		for (int y = -1; y < 2; y++) {
			for (int x = -1; x < 2; x++) {
				this.positions[(y+1) * 3 + (x+1)] = new Point(50 * x, 60 * (-y));
			}
		}
		this.present=new boolean[9];
		for(int i=0;i<9;i++){
			this.present[i]=true;
		}
	}

	public Point getPosition(EnumPuck p) {
		return this.positions[p.getNum()];
	}
	
	public EnumPuck getPuck(int i) {
		return pucks[i];
	}

	public static Field getInstance() {
		return INSTANCE;
	}
	
	public void setPresent(EnumPuck pos, boolean p){
		this.present[pos.getNum()]=p;
	}
	
	public boolean isPresent(EnumPuck p){
		return this.present[p.getNum()];
	}
}
