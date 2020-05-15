package hydraulic;

/*
  Represents a tap that can interrupt the flow.
  
  The status of the tap is defined by the method
  {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends ElementExt {
	
	private boolean Open;
	private double flow;

	public Tap(String name) {
		super(name);
		this.Open = false;
	}
	
	/*
	  Defines whether the tap is open or closed.
	 
	  @param open  opening level
	 */
	public void setOpen(boolean open){
		this.Open = open;
	}

	@Override
	public void simulate(SimulationObserver obs, double flow) {
		this.flow = flow;
		this.flowIn = flow;
		this.flowOut = flow;
		if(Open == true)
			obs.notifyFlow("Tap",this.getName(), this.flow, this.flow);
		if(Open == false)
			obs.notifyFlow("Tap",this.getName(), this.flow, 0.0);
		if(this.Next != null && Open == true)
			this.Next.simulate(obs, this.flow);
		if(this.Next != null && Open == false)
			this.Next.simulate(obs, 0.0);
		return;
	}
	
	@Override
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		this.flow = flow;
		this.flowIn = flow;
		this.flowOut = flow;
		if(Open == true)
			obs.notifyFlow("Tap",this.getName(), this.flow, this.flow);
		if(Open == false)
			obs.notifyFlow("Tap",this.getName(), this.flow, 0.0);
		if(enableMaxFlowCheck == true && this.flowIn>this.maxFlow)
			obs.notifyFlowError("Tap", this.getName(), this.flowIn, this.maxFlow);
		if(this.Next != null && Open == true)
			this.Next.simulate(obs, this.flowOut, enableMaxFlowCheck);
		if(this.Next != null && Open == false)
			this.Next.simulate(obs, 0.0, enableMaxFlowCheck);
		return;
	}

}
