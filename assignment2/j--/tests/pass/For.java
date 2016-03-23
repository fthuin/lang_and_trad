package pass;

import java.util.List;

public class For {
	private int runThrough() {
		List<String> lStr = new ArrayList<String>();
		lStr.add("Hello ");
		lStr.add("World!");
		for (String s : lStr) {
			System.out.print(s);
		}
		System.out.println("\n");
		
		int cnt = 0;
		for (int i = 0; i < 5; i++) {
			cnt++;
		}
		return cnt;
	}
	public static void main(String[] args) {
		System.out.println(5 == runThrough());
	}
}
