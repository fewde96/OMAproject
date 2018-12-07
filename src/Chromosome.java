import java.util.LinkedList;

public class Chromosome {
	private LinkedList<Index> indexes;
	private LinkedList<Configuration> configurations;
	private LinkedList<Query> queries;
	private Double usedMemory;
	private Double objFunctionValue;
	
	/*
	 * costruttore di Chromosome. Gli viene passata l'intera struttura (che poi sarà vuota). La memoria utilizzata è 0.
	 * IMPORTANTE: bisogna cambiare l'implementazione in modo che abbia una copia di tutto, non un riferimento.
	 */
	public Chromosome(LinkedList<Index> indexes, LinkedList<Configuration> configurations,
			LinkedList<Query> queries) {
		this.indexes = indexes;
		this.configurations = configurations;
		this.queries = queries;
		usedMemory = (double)0;
		objFunctionValue = (double)0;
	}
	
	public void associate(Query q, Configuration c) {
		NetGain nc = q.associateTo(c);
		usedMemory += nc.getMemory();
		objFunctionValue += nc.getGain();
	}

	public Double getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(Double usedMemory) {
		this.usedMemory = usedMemory;
	}
	
	
}
