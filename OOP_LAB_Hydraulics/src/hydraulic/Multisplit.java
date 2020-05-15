package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	
	private int maxOutput = 0;	//max number of outuput
	private int currentConnected = 0;	//number of element actually connected
	Element[] elementsList= null;	//list of connected elements
	double[] proportions = null;	//flow's proportion
	double[] elementsFlow = null;

	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		this.maxOutput = numOutput;
		this.elementsList = new Element[maxOutput];
		this.proportions = new double[maxOutput];
		this.elementsFlow = new double[maxOutput];
		for(double i : proportions)
			i = new Double(-1.0);
		for(double i : elementsFlow)
			i = new Double(-1.0);
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	//array initialization
    	Element[] array = new Element[this.maxOutput];
    	//if there aren't connected elements, return empty array
    	if(this.currentConnected == 0)
    		return array;
    	int i=0;
    	for(Element e : this.elementsList) {
    		array[i] = e;
    		i++;
    	}
    	
    	//return ( (Element[]) this.elementsList.toArray() );
    	return array;
    	
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		//checks if noutput is eligible
		if(noutput<0 || noutput>=this.maxOutput) {
			System.out.println("Invalid entry number: " + noutput);
			return;
		}
		
		elementsList[noutput] = elem;
		this.currentConnected++;
		
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		for(int i=0; i<maxOutput; i++)
			this.proportions[i] = proportions[i];
	}
	
	@Override
	public void layout(StringBuffer s,int dim, int...space) {
		StringBuffer new_s = new StringBuffer();
		int count=0;
		
		new_s.append(" ["+this.getName()+"]MultiSplit ");
		s.append(new_s+"+");
		space[dim]+=new_s.length();
			
		for(Element e : elementsList) {
			if(e == null)
				s.append(" *");
			else if(e instanceof Split || e instanceof Multisplit){
				if(dim>0) {
					//space[dim]=space[dim-1];
					space[dim]+=new_s.length();
				}
				s.append("->");
				e.layout(s, dim+1, space);
			}
			else {
				s.append("->");
				e.layout(s, dim, space);
			}
			
			count++;
			
			if(count<maxOutput) {
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
			}
		}

	}
	
	@Override
	public void simulate(SimulationObserver obs, double flow) {
		this.flowIn = flow;
			
		for(int i=0; i<maxOutput; i++)
			if(this.proportions[i] != -1.0 )
				this.elementsFlow[i] = proportions[i]*this.flowIn;
		
		obs.notifyFlow("Multisplit", this.getName(), this.flowIn, this.elementsFlow);
		
		for(int i=0; i<maxOutput; i++)
			if( this.elementsList[i] != null )
				this.elementsList[i].simulate(obs, elementsFlow[i]);
		
		
	}
	
	@Override
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		this.flowIn = flow;
			
		for(int i=0; i<maxOutput; i++)
			if(this.proportions[i] != -1.0 )
				this.elementsFlow[i] = proportions[i]*this.flowIn;
		
		obs.notifyFlow("Multisplit", this.getName(), this.flowIn, this.elementsFlow);
		if(enableMaxFlowCheck == true && this.flowIn>this.maxFlow)
			obs.notifyFlowError("Split", this.getName(), this.flowIn, this.maxFlow);
		
		for(int i=0; i<maxOutput; i++)
			if( this.elementsList[i] != null )
				this.elementsList[i].simulate(obs, elementsFlow[i], enableMaxFlowCheck);
		
		
	}
	

	
}
