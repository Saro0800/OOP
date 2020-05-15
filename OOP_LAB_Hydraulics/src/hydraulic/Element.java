package hydraulic;

/*
  Represents the generic abstract element of an hydraulics system.
  It is the base class for all elements.
 
  Any element can be connect to a downstream element
  using the method {@link #connect(Element) connect()}.
 */
public class Element {
	
	private String Name;	//element name
	protected Element Next;	//element connected to this
	protected double flowIn;//
	protected double flowOut;
		
	/*
	  Constructor
	  @param name the name of the element
	 */
	public Element(String name){
		this.Name = new String(name);
		this.Next = null;
		this.flowIn = 0.0; 
		this.flowOut = 0.0; 
	}

	/*
	  getter method
	  @return the name of the element
	 */
	public String getName(){
		return this.Name;
	}
	
	/*
	  Connects this element to a given element.
	  The given element will be connected downstream of this element
	  @param elem the element that will be placed downstream
	 */
	public void connect(Element elem){
		if(this instanceof Sink)	//if invoked on a Sink object, it has no effect
			return;
		
		this.Next = elem;
		return;
	}
	
	/*
	  Retrieves the element connected downstream of this
	  @return downstream element
	 */
	public Element getOutput(){
		return this.Next;
	}
	
	public void simulate(SimulationObserver obs, double flow) {
		obs.notifyFlow("Class type ",this.getName(), 0.0, 0.0);
	}
	
	public void simulate(SimulationObserverExt obs, double flow, boolean enableMaxFlowCheck) {
		obs.notifyFlow("Class type ",this.getName(), 0.0, 0.0);
	}

	public void layout(StringBuffer s, int dim, int... space) {
		StringBuffer new_s = new StringBuffer();
		
		if(this instanceof Source)
			new_s.append("["+this.getName()+"]Source ->");
		else if(this instanceof Tap)
			new_s.append(" ["+this.getName()+"]Tap ->");
		else if(this instanceof Sink)
			new_s.append(" ["+this.getName()+"]Sink ");
		
		s.append(new_s);
		
		if(this.Next == null)
			s.append("*");
		else if(this.Next instanceof Split || this.Next instanceof Multisplit) {
			space[dim]+=new_s.length();
			this.Next.layout(s, dim, space);
		}
		else {
			space[dim]+=new_s.length();
			this.Next.layout(s, dim, space);
		}
		
		
	}
	
}
