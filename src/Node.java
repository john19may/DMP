import java.util.HashMap;


public class Node {
	
	HashMap<Character, Edge> hm = new HashMap<Character, Edge>();
	
	public Edge ifEdgeExist(char c)
	{
		return hm.get(c);
	}
	
	public void addEdge(char c, Edge e)
	{
		hm.put(c, e);
	}
	
}
