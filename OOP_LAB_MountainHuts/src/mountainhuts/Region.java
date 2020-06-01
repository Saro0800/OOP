package mountainhuts;

//import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private String name;	//region name
	private LinkedList<Range> altitudeRangeList = new LinkedList<>();
	private HashMap<String, Municipality> municMap = new HashMap<>();
	private HashMap<String, MountainHut> mHutMap = new HashMap<>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = new String(name);
	}
	

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String s : ranges)
			this.altitudeRangeList.add(new Range(s));
		
//		LinkedList<Range> list = Stream.of(ranges)
//				.collect(LinkedList::new, ()->list.add(new Range(s)),LinkedList::addAll);
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		List<Range> valid = this.altitudeRangeList
				.stream()
				.filter(x -> x.getMinValue() <= altitude)
				.filter(x -> x.getMaxValue() >= altitude)
				.collect(Collectors.toList());
		
		if(valid.size() != 0)
			return (valid.get(0).toString());
		else return ("0-INF");
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		Municipality mun = this.municMap.get(name);
		if(mun!=null)
			return mun;
		else {
			this.municMap.put(name, new Municipality(name,province,altitude));
			return this.municMap.get(name);
		}
		
		/* Alternativa con gli Stream molto più inefficiente di quanto fatto sopra*/
		
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.municMap.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		
		MountainHut hut = this.mHutMap.get(name);
		if(hut!=null)
			return hut;
		else {
			this.mHutMap.put(name, new MountainHut(name, null, category, bedsNumber, municipality));
			return this.mHutMap.get(name);
		}
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		
		MountainHut hut = this.mHutMap.get(name);
		if(hut == null) {
			this.mHutMap.put(name, new MountainHut(name, altitude, category, bedsNumber, municipality));
			hut = this.mHutMap.get(name);
		}
		
		return hut;				
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.mHutMap.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region r = new Region(name);
		String[] vet;
		List<String> list = readData(file);
		Iterator itr = list.iterator();
		itr.next();		//serve a saltare la riga di intestazione
		Municipality m;
		
		while(itr.hasNext()) {
			vet = ((String) itr.next()).split(";");
			 m = r.createOrGetMunicipality(vet[1], vet[0], Integer.parseInt(vet[2]));
			
			if(vet[4].length()==0)
				r.createOrGetMountainHut(vet[3], vet[5], Integer.parseInt(vet[6]), m);
			else
				r.createOrGetMountainHut(vet[3], Integer.parseInt(vet[4]), vet[5], Integer.parseInt(vet[6]), m);
		}
		
		return r;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> map = this.municMap.values()
				.stream()
				.parallel()
				.collect(Collectors.groupingBy(Municipality::getProvince, HashMap::new, Collectors.counting()));
		return map;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		
		Map<String, Map<String, Long>> map = this.mHutMap.values()
				.stream()
				.collect(Collectors.groupingBy(
							x->x.getMunicipality().getProvince(), 
							HashMap::new, 
							Collectors.groupingBy(x->x.getMunicipality().getName(), HashMap::new, Collectors.counting())));
		
		return map;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		
		
		return this.mHutMap.values()
				.stream()
				.collect(Collectors.groupingBy( x->{
					if(x.getAltitude().isPresent())
						return getAltitudeRange(x.getAltitude().get());
					else 
						return getAltitudeRange(x.getMunicipality().getAltitude());
				}, Collectors.counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.mHutMap.values()
				.stream()
				.collect(Collectors.groupingBy(	
						x->x.getMunicipality().getProvince(),
						HashMap::new,
						Collectors.summingInt(MountainHut::getBedsNumber)));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return this.mHutMap.values()
				.stream()
				.collect(Collectors.groupingBy(	
						x->{
							if(x.getAltitude().isPresent())
								return getAltitudeRange(x.getAltitude().get());
							else 
								return getAltitudeRange(x.getMunicipality().getAltitude());
						},
						HashMap::new,
						Collectors.mapping(MountainHut::getBedsNumber, Collectors.maxBy(Comparator.naturalOrder()))));
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		
		Map<String, Long> m = this.mHutMap.values()
				.stream()
				.collect(Collectors.groupingBy( 
						x->x.getMunicipality().getName(),
						TreeMap::new,
						Collectors.counting()));
				
		return m.entrySet()
				.stream()
				.collect(Collectors.groupingBy( 
						x->x.getValue(),
						HashMap::new,
						Collectors.mapping(x->x.getKey(), Collectors.toList())));
	}

}
