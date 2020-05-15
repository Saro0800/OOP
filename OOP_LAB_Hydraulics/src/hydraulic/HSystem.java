package hydraulic;

/*
  Main class that act as a container of the elements for
  the simulation of an hydraulics system 
 
  */
public class HSystem {
	
	final static int MaxElements = 100;		//max number of elements 
	protected int ElementsNumber = 0;					//number of elements stored
	protected Element[] ElementsArray = new Element[MaxElements];			//collection of elements stored
 	
	/*
	  Adds a new element to the system
	  @param elem
	 */
	public void addElement(Element elem){
		this.ElementsArray[ElementsNumber] = (Element) elem;
		this.ElementsNumber++;
	}
	
	/*
	  returns the element added so far to the system.
	  If no element is present in the system an empty array (length==0) is returned.
	  
	  @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){	
		Element[] vett = new Element[ElementsNumber];
		for(int i=0; i<ElementsNumber; i++)
			vett[i] = ElementsArray[i];
		
		return vett;
	}
	
	/*
	  Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/*
	  starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		//search first source stored to start simulation
		for(Element elem : ElementsArray) {
			if(elem instanceof Source) {
				elem.simulate(observer, observer.NO_FLOW);
				return;
			}
				
		}
	}

}
