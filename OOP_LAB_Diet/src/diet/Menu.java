package diet;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	private Food ref;
	private String name;
	private LinkedHashMap<NutritionalElement, Double> Dishes = new LinkedHashMap<>();
	
	public Menu (String name, Food ref) {
		this.name = name;
		this.ref = ref;
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		this.Dishes.put(ref.getRecipe(recipe), quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		this.Dishes.put(ref.getProduct(product), 1.0);
		return null;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double calories = 0.0;
		
		for(Map.Entry<NutritionalElement, Double> m : this.Dishes.entrySet()) {
			
			NutritionalElement n = m.getKey();
			if(n instanceof Recipe) {
				double qta = m.getValue();
				calories += n.getCalories()*(qta/100.00);
			} 
			else if(m.getKey() instanceof Product) {
				calories += n.getCalories();
			}
		}
		
		return calories;
		
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double proteins = 0.0;
		
		for(Map.Entry<NutritionalElement, Double> m : this.Dishes.entrySet()) {

			if(m.getKey() instanceof Recipe) {
				double qta = m.getValue();
				proteins +=(m.getKey().getProteins()/100.00)*qta;
			} 
			else if(m.getKey() instanceof Product) {
				proteins += m.getKey().getProteins();
			}
		}
		
		return proteins;
		
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double carbs = 0.0;
		
		for(Map.Entry<NutritionalElement, Double> m : this.Dishes.entrySet()) {

			if(m.getKey() instanceof Recipe) {
				double qta = m.getValue();
				carbs +=(m.getKey().getCarbs()/100.00)*qta;
			} 
			else if(m.getKey() instanceof Product) {
				carbs += m.getKey().getCarbs();
			}
		}
		
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double fat = 0.0;
		
		for(Map.Entry<NutritionalElement, Double> m : this.Dishes.entrySet()) {

			if(m.getKey() instanceof Recipe) {
				double qta = m.getValue();
				fat +=(m.getKey().getFat()/100.00)*qta;
			} 
			else if(m.getKey() instanceof Product) {
				fat += m.getKey().getFat();
			}
		}
		
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
