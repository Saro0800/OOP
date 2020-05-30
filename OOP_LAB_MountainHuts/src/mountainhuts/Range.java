package mountainhuts;

public class Range {
	
	private int start;
	private int end;
	
	public Range(String range) {
		String[] s = range.split("-");
		this.start = Integer.parseInt(s[0]);
		this.end = Integer.parseInt(s[1]);
	}
	
	public String checkRange(int value) {
		if(value>=this.start && value<=this.end)
			return this.toString();
		else return null;
	}
	
	
	@Override
	public String toString() {
		return (this.start+"-"+this.end);
	}
	
	public int getMinValue() {
		return this.start;
	}

	public int getMaxValue() {
		return this.end;
	}
}
