package diet;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    
	private Food ref;
	private String name;
	private LinkedHashMap<NutritionalElement, Double> Ingredients = new LinkedHashMap<>();
	
	
	public Recipe(String name, Food ref) {
		this.name = name;
		this.ref = ref;
	}
	
	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		this.Ingredients.put(ref.getRawMaterial(material), quantity);
		return this;
	}
	
	private double getTotalMass() {
		double mass = 0.0;
		
		for(Map.Entry<NutritionalElement, Double> e : this.Ingredients.entrySet())
			mass += e.getValue();
		
		return mass;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		double sum=0.0;
		double mass = this.getTotalMass();
		
		for(Map.Entry<NutritionalElement, Double> e : this.Ingredients.entrySet())
			sum += e.getKey().getCalories() * (e.getValue()/100.00);
		
		return (sum/mass)*100.00;
	}

	@Override
	public double getProteins() {
		double sum=0.0;
		double mass = this.getTotalMass();
		
		for(Map.Entry<NutritionalElement, Double> e : this.Ingredients.entrySet())
			sum += e.getKey().getProteins() * (e.getValue()/100.00);
		
		return (sum/mass)*100.00;
	}

	@Override
	public double getCarbs() {
		double sum=0.0;
		double mass = this.getTotalMass();
		
		for(Map.Entry<NutritionalElement, Double> e : this.Ingredients.entrySet())
			sum += e.getKey().getCarbs() * (e.getValue()/100.00);
		
		return (sum/mass)*100.00;
	}

	@Override
	public double getFat() {
		double sum=0.0;
		double mass = this.getTotalMass();
		for(Map.Entry<NutritionalElement, Double> e : this.Ingredients.entrySet())
			sum += e.getKey().getFat() * (e.getValue()/100.00);
		
		return (sum/mass)*100.00;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		
		for(Map.Entry<NutritionalElement, Double> m : this.Ingredients.entrySet()) {
			String name = m.getKey().getName();
			Double qta = m.getValue();
			s.append(name).append(": ").append(String.format("%d.1", qta)).append("\n");
		}
		
		return s.toString();
	}
}





















