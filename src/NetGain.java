
public class NetGain {
	private Double memory;
	private Double gain;
	
	public NetGain(double memory, double cost) {
		this.memory = memory;
		this.gain = cost;
	}

	public Double getMemory() {
		return this.memory;
	}
	
	public Double getGain() {
		return this.gain;
	}
	
	/*
	 * metodo che esegue la somma tra coppie. è inteso per essere usato quando si sta aggiungendo la (memoria, costo) dovuta all'attivazione di indici/configurazioni, quindi c'è un meno sul gain
	 */
	public void add(NetGain nc) {
		this.memory += nc.getMemory();
		this.gain += nc.getGain();
	}
	
	/*
	 * metodo che aggiunge il gain, inteso per essere usato quando aggiungiamo il gain di un link
	 */
	public void add(double gain) {
		this.gain += gain;
	}
	
	
}
