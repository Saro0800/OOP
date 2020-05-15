package hydraulic;

/**
  Represents the sink, i.e. the terminal element of a system
 
 */
public class Sink extends ElementExt {
	
	/*
	  Constructor
	  @param name
	 */
	public Sink(String name) {
		super(name);
	}
	
	@Override
	public void simulate(SimulationObserver obs, double flow) {
		this.flowIn = flow;
		this.flowOut = obs.NO_FLOW;
		obs.notifyFlow("Sink", this.getName(), this.flowIn, this.flowOut);
		if(this.Next != null)
			this.Next.simulate(obs, flowOut);
		return;
	}

	@Override
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		this.flowIn = flow;
		this.flowOut = obs.NO_FLOW;
		obs.notifyFlow("Sink", this.getName(), this.flowIn, this.flowOut);
		if(enableMaxFlowCheck == true && this.flowIn>this.maxFlow)
			obs.notifyFlowError("Sink", this.getName(), this.flowIn, this.maxFlow);
		if(this.Next != null)
			this.Next.simulate(obs, flowOut);
		return;
	}
	
}
