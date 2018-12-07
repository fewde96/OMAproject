import java.util.HashMap;
import java.util.LinkedList;

public class Configuration implements Comparable<Configuration>{

	private int id;
	private LinkedList<Index> indexList;
	private HashMap<Query, Double> queryGainMap; // cambiato in HashMap e messo come tipo Double
	private boolean active;
	private LinkedList<Query> servingQueries;
	
	public Configuration(int configurationId) {
		super();
		this.id = configurationId;
		this.indexList = new LinkedList<Index>();
		this.queryGainMap = new HashMap<Query, Double>();
		this.active = false; // inizialmente sono tutte spente (è così già di default)
		this.servingQueries = new LinkedList<Query>();
	}

	public int getId() {
		return id;
	}

	public LinkedList<Index> getIndexList() {
		return indexList;
	}
	
	public void addIndex(Index i) {
		this.indexList.add(i);
	}
	
	public HashMap<Query, Double> getQueryGainMap() {
		return queryGainMap;
	}

	public void addQueryGain(Query q, double gain) {
		this.queryGainMap.put(q,gain);
	}
	
	public String toString() {
		return id + "";
	}
	
	@Override
	public int compareTo(Configuration config) {
		return (this.getId()-config.getId());
	}
	
	/*
	 * Metodo per verificare se la configurazione è attiva. Ritorna il proprio attributo boolean "isActive".
	 */
	public boolean isActive() {
		return active;
	}
	
	/*
	 * Metodo con cui una query chiede alla configurazione di attivarsi. Restituisce il NetGain della sua (eventuale) accensione
	 * 1. aggiunge sempre la query in questione alla lista delle query che questa configurazione sta servendo
	 * 2. Se la configurazione era già attiva, non succede nulla
	 * 2. Altrimenti, 
	 * 		2.1 Attiva tutti i suoi indici
	 * 		2.2 Calcola un NetGain indicante la memoria e il costo ADDIZIONALI 
	 * che sono stati necessari.
	 */
	public NetGain turnOn(Query q) {
		NetGain netGain = new NetGain(0,0);
		servingQueries.add(q);
		
		if (this.isActive()==true) return netGain; // questo se era attiva anche prima: ritorna 0,0
		else {
			for (Index i : indexList) {
				netGain.add(i.turnOnBy(this));
			}
			return netGain;
		}
	}
	
	/*
	 * Metodo con cui una query chiede alla configurazione quale sarebbe il net gain se si attivasse
	 * Simile al precedente: non aggiunge la query alla lista delle query che questa configurazione sta servendo, e usa il metodo 
	 * tryTurnOn di Index
	 */
	public NetGain testTurnOn(Query q) {
		NetGain netGain = new NetGain(0,0);
		
		if (this.isActive()==true) return netGain; // questo se era attiva anche prima: ritorna 0,0
		else {
			for (Index i : indexList) {
				netGain.add(i.testTurnOnBy(this));
			}
			return netGain;
		}
	}
	
	/*
	 * Metodo che con cui una query prova a spegnere una configurazione.
	 * 1. Rimuove la query in questione dalla lista delle query servite
	 * 2. Se la configurazione non serve più alcuna query, si spegne totalmente:
	 * 		2.1 Va a rimuovere la configurazione dalle liste degli indici
	 * 		2.2 ritorna un NetGain indicante variazione di memoria e di costo derivanti dall'eventuale spegnimento degli indici.
	 * 3. Altrimenti, non cambia nulla
	 */
	public NetGain turnOff(Query q) {
		NetGain netGain = new NetGain(0,0);
		servingQueries.remove(q);
		
		if (servingQueries.isEmpty()) {
			for (Index i : indexList) {
				netGain.add(i.turnOffBy(this));
			}
		}
		return netGain;
	}

}
