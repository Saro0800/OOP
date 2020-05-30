package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut.
 * 
 * It is linked to a {@link Municipality}
 *
 */
public class MountainHut {
	
	private String name;
	private Optional<Integer> altitude;
	private String category;
	private int n_beds;
	private Municipality mun;
	
	public MountainHut(String name, Integer altitude, String category, Integer nBeds, Municipality mun) {
		this.name = name;
		this.altitude = Optional.ofNullable(altitude);
		this.category = category;
		this.n_beds = nBeds;
		this.mun = mun;
	}

	/**
	 * Unique name of the mountain hut
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Altitude of the mountain hut.
	 * May be absent, in this case an empty {@link java.util.Optional} is returned.
	 * 
	 * @return optional containing the altitude
	 */
	public Optional<Integer> getAltitude() {
		return this.altitude;
	}

	/**
	 * Category of the hut
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Number of beds places available in the mountain hut
	 * @return number of beds
	 */
	public Integer getBedsNumber() {
		return this.n_beds;
	}

	/**
	 * Municipality where the hut is located
	 *  
	 * @return municipality
	 */
	public Municipality getMunicipality() {
		return this.mun;
	}

}
