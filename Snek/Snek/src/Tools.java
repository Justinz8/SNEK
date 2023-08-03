
public class Tools {
	
	//constants and tools that are usefull in every file
	public static final int height = 700, width = 700;
	
	public static boolean inbounds(long lower, long upper, long l, long r) {
		if(lower<=l&&r<=upper) {
			return true;
		}
		return false;
	}
}
