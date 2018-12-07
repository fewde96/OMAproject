import java.util.LinkedList;
public class Index {

	private int id;
	private Double fixedCost; // rispetto alla versione di Federico qui è stato cambiato in Double
	private Double memoryOccupation; // rispetto alla versione di Federico qui è stato cambiato in Double
	private LinkedList<Configuration> activeConfigurations; // lista delle configurazioni ATTIVE che questo indice sta servendo
	// NOTA: se l'indice è attivo o no si ricava da activeConfigurations.size();
	
	public Index(int indexId) {
		this.id = indexId;
		this.activeConfigurations = new LinkedList<Configuration>();
		this.fixedCost = (double) -1; //Potenzialmente pericoloso come valore
	}

	public int getId() {
		return id;
	}

	public Double getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(Double fixedCost) {
		this.fixedCost = fixedCost;
	}

	public Double getMemoryOccupation() {
		return memoryOccupation;
	}

	public void setMemoryOccupation(Double memoryOccupation) {
		this.memoryOccupation = memoryOccupation;
	}

	public void addConfiguration(Configuration c) {
		this.activeConfigurations.add(c);
	}
	
	public LinkedList<Configuration> getActiveConfigurations() {
		return activeConfigurations;
	}
	
	public String toString() {
		return id + "";
	}
	
	/*
	 * Metodo che verifica se un indice è attivo o meno, sfruttando la lunghezza della sua lista di configurazioni. Ritorna un boolean.
	 * 
	 */
	public boolean isActive() {
		return activeConfigurations.size() > 0;
	}
	
	/*
	 * Metodo che restituisce il numero di configurazioni che questo indice attualmente serve. Ritorna un int. 
	 */
	public int getNumberOfConfigurations() {
		return activeConfigurations.size();
	}
	
	/*
	 * Metodo che accende l'indice (su richiesta di una configurazione):
	 * 1. aggiunge la configurazione alla lista delle sue configurazioni
	 * 2. se l'indice non era attivo, restituisce un NetGain con la memoria aggiuntiva che stiamo occupando e il costo che stiamo aggiungendo
	 * 3. se l'indice era già attivo, restituisce un NetGain 0,0
	 */
	public NetGain turnOnBy(Configuration c) {
		activeConfigurations.add(c); //NOTA così come ora, la configurazione la aggiunge sempre, quindi va assunto che non ci sono mai errori.
		
		if (isActive()==false) return new NetGain(+ this.memoryOccupation, - this.fixedCost);  // this.fixedCost ha segno negativo perché è un costo che si aggiunge
		else return new NetGain(0,0);
	}
	
	/*
	 * Metodo con cui una configurazione chiede alla query quale sarebbe il net gain se si attivasse
	 * Simile al precendete, non aggiunge la configurazione alla lista delle configurazioni che stanno usando l'indice
	 */
	public NetGain testTurnOnBy(Configuration c) {
		
		if (isActive()==false) return new NetGain(+ this.memoryOccupation, - this.fixedCost);  // this.fixedCost ha segno negativo perché è un costo che si aggiunge
		else return new NetGain(0,0);
	}
	
	/*
	 * Metodo che spegne l'indice:
	 * 1. rimuove la configurazione dall'elenco delle configurazioni
	 * 2. se l'indice era attivo e si è spento, restituisce un NetGain con la memoria che non stiamo più occupando e il costo che abbiamo rimosso
	 * 3. se l'indice era attivo ma lo è ancora, restituisce un NetGain 0,0
	 */
	public NetGain turnOffBy(Configuration c) {
		activeConfigurations.remove(c);
		
		if (isActive()==false) return new NetGain(- this.memoryOccupation, + this.fixedCost); // this.memoryOccupation ha segno negativo perché è una memoria guadagnata, this.fixedCost ha segno positivo perché è un costo in meno, quindi l'objective function aumenta di valore
		else return new NetGain(0,0);
	}
}
