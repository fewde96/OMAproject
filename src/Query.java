import java.util.*;
import java.util.stream.Stream;
import static  java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class Query implements Comparable<Query>{

	private int id;
	private HashMap<Configuration, Double> configurationGainMap; // la lista delle configurazioni che POSSONO servire questa query
	private Configuration servingConfiguration; // la configurazione (UNICA, al più è null) che serve questa query
	
	public Query(int queryId) {
		super();
		this.id = queryId;
		this.configurationGainMap = new HashMap<Configuration, Double>();
		this.servingConfiguration = null; //se non c'è alcuna configurazione che serve questa query, mettiamo nulla
	}

	public HashMap<Configuration, Double> getConfigurationGainMap() {
		return configurationGainMap;
	}

	public void addConfigurationGain(Configuration c, double gain) {
		this.configurationGainMap.put(c,gain);
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return id + "";
	}
	
	@Override
	public int compareTo(Query q) {
		return (this.id-q.id);
	}
	
	public boolean isServed() {
		if (servingConfiguration==null) return false;
		else return true; 
	}
	
	/*
	 * metodo che associa la query a una configurazione. 
	 * 1. chiede alla configurazione di attivarsi, e restituisce il NetGain risultante
	 * 2. aggiunge al NetGain il guadagno dovuto all'accensione del link
	 * 3. imposta che questa query è servita a quella configurazione
	 */
	public NetGain associateTo(Configuration config) {
		NetGain netGain = config.turnOn(this);
		netGain.add(+ configurationGainMap.get(config)); // segno +: è un aumento della objective function
		this.servingConfiguration = config;
		
		return netGain;
	}
	
	/*
	 * metodo che disassocia la query a una configurazione
	 * 1. chiede alla configurazione di spegnersi, e restituisce il NetGain risultante (se c'è)
	 * 2. aggiunge al NetGain il guadagno dovuto allo spegnimento del link
	 * 3. imposta che questa query non è servita da alcuna configurazione
	 */
	public NetGain dissociateFrom(Configuration config) {
		NetGain netGain = config.turnOff(this);
		netGain.add(- configurationGainMap.get(config)); // segno -: è una diminuizione della objective function
		this.servingConfiguration = null;
		
		return netGain;
	}
	
	/*
	 * Metodo che trova la migliore configurazione disponibile in base al best gain.
	 */
	public Configuration findBestConfiguration() {
		List<Configuration> list = configurationGainMap.keySet().stream().collect(toList());
		Double M = (double)0;
		Configuration result = null;
		
		for (Configuration c : list) {
			if (configurationGainMap.get(c) > M) {
				M = configurationGainMap.get(c);
				result = c;
			}
		}
		return result;
	}
}
