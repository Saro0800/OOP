package hydraulic;

/*
  Represents a source of water, i.e. the initial element for the simulation.
 
  The status of the source is defined through the method
  {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {

	public Source(String name) {
		super(name);	
	}

	/*
	  defines the flow produced by the source
	 
	  @param flow
	 */
	public void setFlow(double flow){
		this.flowIn = 0.0; 
		this.flowOut = flow;
	}
	
	@Override
	public void simulate(SimulationObserver obs, double flow) {
		obs.notifyFlow("Source",this.getName(), obs.NO_FLOW, this.flowOut);
		if(this.Next != null)
			this.Next.simulate(obs, this.flowOut);
		return;
	}
	
	@Override
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		obs.notifyFlow("Source",this.getName(), obs.NO_FLOW, this.flowOut);
		if(this.Next != null)
			this.Next.simulate(obs, this.flowOut, enableMaxFlowCheck);
		return;
	}
	
}
