package hydraulic;

/*
  Represents a split element, a.k.a. T element
  
  During the simulation each downstream element will
  receive a stream that is half the input stream of the split.
 */

public class Split extends ElementExt {

	protected Element NextLeft;		//element connected to this to the left side
	protected Element NextRight;	//element connected to this to the right side
	protected double flowOutLeft;
	protected double flowOutRight;

	
	/*
	  Constructor
	  @param name
	 */
	public Split(String name) {
		super(name);
		this.NextLeft = null;
		this.NextRight = null;
		this.flowOutLeft = 0.0;
		this.flowOutRight = 0.0;
	}
    
	/*
	  returns the downstream elements
	  @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	/* if one the downstream connection is not specified,
    	 * a message error appears
    	 */
    	if(NextLeft == null || NextRight == null) {
    		System.out.println("Split element is not used correctly");
    		return null;
    	}
        /* if both downstream connections are specified, an array is created and returned*/
    	Element[] Array = new Element[2];
    	Array[0] = NextLeft;
    	Array[1] = NextRight;
    	return Array;
    }

    /*
      connect one of the outputs of this split to a
      downstream component.
      
      @param elem  the element to be connected downstream
      @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		if(noutput == 0)
			NextLeft = elem;
		else if(noutput == 1)
			NextRight = elem;
		else {
			System.out.println("Invalid output number");
			return;
		}
		return;
	}
	
	@Override
	public void simulate(SimulationObserver obs, double flow) {
		this.flowIn = flow;
		this.flowOutLeft = this.flowOutRight = this.flowIn / 2.0;
		
		obs.notifyFlow("Split", this.getName(), this.flowIn, this.flowOutLeft, this.flowOutRight);
		
		if(this.NextLeft != null)
			this.NextLeft.simulate(obs, flowOutLeft);
		if(this.NextRight != null)
			this.NextRight.simulate(obs, flowOutRight);
		
		return;
	}
	
	@Override
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		this.flowIn = flow;
		this.flowOutLeft = this.flowOutRight = this.flowIn / 2.0;
		
		obs.notifyFlow("Split", this.getName(), this.flowIn, this.flowOutLeft, this.flowOutRight);
		
		if(enableMaxFlowCheck == true && this.flowIn>this.maxFlow)
			obs.notifyFlowError("Split", this.getName(), this.flowIn, this.maxFlow);
		
		if(this.NextLeft != null)
			this.NextLeft.simulate(obs, flowOutLeft);
		if(this.NextRight != null)
			this.NextRight.simulate(obs, flowOutRight);
		
		return;
	}
	
	@Override
	public void layout(StringBuffer s,int dim, int...space) {
		StringBuffer new_s = new StringBuffer();
		
		new_s.append(" ["+this.getName()+"]Split ");
		s.append(new_s+"+");
		space[dim]+=new_s.length();
		
		//printing left element
		if(this.NextLeft == null)
			s.append(" *");
		else if(this.NextLeft instanceof Split || this.NextLeft instanceof Multisplit){
			if(dim>0) {
				//space[dim]=space[dim-1];
				space[dim]+=new_s.length();
			}
			s.append("->");
			space[dim+1]=2;
			this.NextLeft.layout(s, dim+1, space);
		}
		else {
			s.append("->");
			//space[dim]+=2;
			this.NextLeft.layout(s, dim, space);
		}
		s.append("\n");
		
		
		for(int i=0; i<dim+1; i++) {
			for(int j=0; j<space[i]; j++)
				s.append(" ");
			if(space[i]!=0)
				s.append("|");				
		}
		s.append("\n");
		for(int i=0; i<dim; i++) {
			for(int j=0; j<space[i]; j++)
				s.append(" ");
			if(space[i]!=0)
				s.append("|");				
		}
		for(int j=0; j<space[dim]; j++)
			s.append(" ");
		s.append("+");	
		//s.append("\n");
		
		
		//printing right element
		if(this.NextRight == null)
			s.append(" *");
		else if(this.NextRight instanceof Multisplit){
			if(dim>0) {
				//space[dim]=space[dim-1];
				space[dim]+=new_s.length();
			}
			s.append("->");
			space[dim+1]=space[dim]+3;
			space[dim]=0;
			this.NextRight.layout(s, dim+1, space);
		}
		else if (this.NextRight instanceof Split){
			if(dim>0) {
				//space[dim]=space[dim-1];
				space[dim]+=new_s.length();
			}
			s.append("->");
			space[dim+1]=space[dim]+3;
			space[dim]=0;
			this.NextRight.layout(s, dim+1, space);
		}
		else {
			s.append("->");
			space[dim]+=2;
			this.NextRight.layout(s, dim, space);
		}
		
				
	}
}





















