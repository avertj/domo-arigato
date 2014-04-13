package field;

public enum EnumPuck {
	NW(0),
	N(1),
	NE(2),
	W(3),
	M(4),
	E(5),
	SW(6),
	S(7),
	SE(8);
	
	private int num;
	
	EnumPuck(int num){
		this.num = num;
	}
	
	public int getNum(){
		return this.num;
	}
}
