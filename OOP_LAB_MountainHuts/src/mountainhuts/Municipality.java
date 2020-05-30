package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {
	
	private String name;
	private String prov;
	private int altitude;
	
	public Municipality(String name, String prov, int alt) {
		this.name = name;
		this.prov = prov;
		this.altitude = alt;
	}

	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return this.prov;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return this.altitude;
	}

}
