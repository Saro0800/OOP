package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
			
		StringBuffer config = new StringBuffer("\n");
		
		if(ElementsNumber == 0)
			config.append("No elements present\n");
		else
			for(Element e : ElementsArray)
				if(e instanceof Source)	e.layout(config,0,new int[100]);
		
		return config.toString();
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		int toDelete=0;
		
		for(Element e: ElementsArray) {
			if(e.getName().contentEquals(name)) 
				break;
			toDelete++;
		}
		
		if(!(ElementsArray[toDelete] instanceof Split || ElementsArray[toDelete] instanceof Multisplit)) {
			if(toDelete>=0)
				ElementsArray[toDelete-1].Next = (ElementsArray[toDelete].Next);
			for(int i=toDelete+1; i<ElementsNumber; i++)
				ElementsArray[i-1] = ElementsArray[i];
			ElementsNumber--;
			ElementsArray[ElementsNumber] = null;
		}
				
		
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		//search first source stored to start simulation
		for(Element elem : ElementsArray) {
			if(elem instanceof Source) {
				elem.simulate(observer, observer.NO_FLOW,enableMaxFlowCheck);
				return;
			}
						
		}
	}
	
}
