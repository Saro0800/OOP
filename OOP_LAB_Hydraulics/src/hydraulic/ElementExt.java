package hydraulic;

public abstract class ElementExt extends Element{
	
	protected double maxFlow;

	public ElementExt(String name) {
		super(name);
		this.maxFlow = -1.0;
	}

	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}

}
