package field;

public enum EnumPuck {
	RED_GREEN(0),
	RED_GREY(1),
	RED_BLUE(2),
	BLACK_GREEN(3),
	BLACK_GREY(4),
	BLACK_BLUE(5),
	YELLOW_GREEN(6),
	YELLOW_GREY(7),
	YELLOW_BLUE(8);
	
	private int num;
	
	EnumPuck(int num){
		this.num = num;
	}
	
	public int getNum(){
		return this.num;
	}
}
