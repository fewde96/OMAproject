import java.util.*;
import java.io.IOException;

public class main {

	
	public static void main(String[] args) throws IOException {
	
		Workspace s = new Workspace();
		
		/*for(int i=1; i<=10; i++) {
			s.getIndexes().add(new Index(i));
		}
		
		s.getIndexes().get(9).addConfiguration(new Configuration(4));
		
		System.out.println(s.getIndexes());
		System.out.println(s.getIndexes().get(9).getConfiguration_list());*/
		
		s.loadInstance("instance20.odbdp");
	}
	
}
