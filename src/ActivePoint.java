
public class ActivePoint {

	Node active_node;
	char active_edge;
	int active_length;
	
	public void change(Node node,char edge_char, int len)
	{
		active_node = node;
		active_edge = edge_char;
		active_length = len;
	}
	
	public void initialize(Node root)
	{
		active_node = root;
		active_length = 0;
	}
	
}
