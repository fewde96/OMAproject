import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Workspace extends Thread {

	
	LinkedList<Index> indexes;
	LinkedList<Configuration> configurations;
	LinkedList<Query> queries;
	Double memory;
	Integer nFigli = 8;
	
	public Workspace() {
		this.indexes = new LinkedList<Index>();
		this.configurations = new LinkedList<Configuration>();
		this.queries = new LinkedList<Query>();
	}

	public LinkedList<Index> getIndexes() {
		return indexes;
	}

	public LinkedList<Configuration> getConfigurations() {
		return configurations;
	}

	public LinkedList<Query> getQueries() {
		return queries;
	}
	
	public int loadInstance(String filename) throws IOException {
		
		try {
			FileReader r = new FileReader(filename);
			BufferedReader br = new BufferedReader(r);
			
			String line=br.readLine(); 
			
			line = line.replace("N_QUERIES: ", "");
			int queries_num = Integer.parseInt(line);
			
			for(int i=1; i<=queries_num; i++)
				this.queries.add(new Query(i));
			
			System.out.println("Queries number: " + queries_num);
			System.out.println("Queries list");
			System.out.println(this.getQueries()+"\n");
			
			
			line = br.readLine().replace("N_INDEXES: ", "");
			int indexes_num = Integer.parseInt(line);
			
			for(int i=1; i<=indexes_num; i++)
				this.indexes.add(new Index(i));
			
			System.out.println("Indexes number: " + indexes_num);
			System.out.println("Indexes list");
			System.out.println(this.getIndexes()+"\n");
			
			
			line = br.readLine().replace("N_CONFIGURATIONS: ", "");
			int configurations_num = Integer.parseInt(line);
			
			for(int i=1; i<=configurations_num; i++)
				this.configurations.add(new Configuration(i));
			
			System.out.println("Configurations number: " + configurations_num);
			System.out.println("Configurations list");
			System.out.println(this.getConfigurations()+"\n");
			
			line = br.readLine().replace("MEMORY: ", "");
			memory = Double.parseDouble(line);
			br.readLine();
			
			System.out.println("Memory: " + memory);
			
			String[] str;
			for(int k=0; k<configurations_num; k++) {
				line = br.readLine();
				str = line.split(" ");
				
				for (int j=0; j<indexes_num; j++) {
					if (Integer.parseInt(str[j])==1) {
						indexes.get(j).addConfiguration(configurations.get(k));
						configurations.get(k).addIndex(indexes.get(j));
					}
				}				
			}
			
			line = br.readLine();
			for(int i=0; i<indexes_num; i++)
				indexes.get(i).setFixedCost(Double.parseDouble(br.readLine()));
			
			line = br.readLine();
			for(int i=0; i<indexes_num; i++)
				indexes.get(i).setMemoryOccupation(Double.parseDouble(br.readLine()));
			
			System.out.println("Index 1 cost: " + indexes.get(0).getFixedCost());
			System.out.println("Index 1 memory occupation: " + indexes.get(0).getMemoryOccupation());
			
			line = br.readLine();
			for(int k=0; k<configurations_num; k++) {
				line = br.readLine();
				str = line.split(" ");
				
				for (int j=0; j<queries_num; j++) {
					if (Integer.parseInt(str[j])!=0) {
						queries.get(j).addConfigurationGain(configurations.get(k), Double.parseDouble(str[j]));
						configurations.get(k).addQueryGain(queries.get(j), Double.parseDouble(str[j]));
					}
				}				
			}
			
			System.out.println("Query 1 configurations and gains " + queries.get(0).getConfigurationGainMap());
			System.out.println("Configuration 1 queries and gains " + configurations.get(0).getQueryGainMap());
			
			int end = br.readLine().compareTo("EOF");
			br.close();
			return end;	
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                    
        }
		return -1;
	}
	
	/*
	 * metodo che inizializza, creando una soluzione casuale con i criteri che abbiamo deciso:
	 * 1. sceglie una random query
	 * 2. la collega al suo best gain
	 * 3. avanti così
	 * 4. si ferma quando eccede di memoria
	 */
	public Chromosome getInitialSolution() {
		Random rand = new Random();
		Chromosome chr = new Chromosome(indexes, configurations, queries);

		for (int k=0; k < queries.size(); k++) {
			Query q = queries.get(rand.nextInt(queries.size())); // estrae una random query
			Configuration bc = q.findBestConfiguration(); // trova la miglior configurazione per quella query
			if (bc.testTurnOn(q).getMemory() + chr.getUsedMemory() > memory) return chr; // se abbiamo ecceduto di memoria
			else {
				chr.associate(q, bc);
			}
		}
		
		return chr;	
	}
	
	
	public void CreateThread() {
		// Creazione della popolazione iniziale
		List<Chromosome> parents = new LinkedList<Chromosome>();
		int i;
		for(i=0; i<nFigli; i++)
			parents.add(getInitialSolution());
		// Creazione dei thread
		Thread threadHandler[] = new Thread[nFigli];
		for(i=0; i<nFigli; i++)
			threadHandler[i].start();
		// Ritorno delle soluzioni trovate e restart della ricerca con nuova popolazione
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Necessario copiare la struttura dati per evitare sovrascritture da parte dei thread
		// Esecuzione della localSearch
		// modifica prova commit
		
	}
}


