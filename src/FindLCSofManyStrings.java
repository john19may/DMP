import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class FindLCSofManyStrings {

	
	static String whole_string = "";
	
	public static int current;
	
	private static ActivePoint act_pt;
	
	private static Node root;
	
	private static int remainder = 1;
	
	private static ArrayList<String> listOfTheWordsToBeAdded;
	
	//The end characters needed for Suffix Tree Algorithm
	//right now we have 1000 characters as delimiters to end the string
	//so we can process at max 1000 string in 0ne time
	//I have chosen these characters because they are from old "YI syllables" probably not used today.
	private static int DELI1 = 0xA000, DELI2 = 0xA40F;
	
	public static void main(String args[])
	{
		//scanning all the strings
		int n;
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		
		int deli = DELI1;
		
		for(int iii = 0;iii<n;iii++)
		{
			String str = sc.next();
			str+=((char)deli);
			whole_string+=str;
		}
		//input complete
		
		//initializing variables
		current = 0;
		
		root = new Node();
		
		act_pt.initialize(root);
		
		listOfTheWordsToBeAdded = new ArrayList<>();
		////initializing variables complete
		
		//MAJOR LOOP BEGINS
		for(n = 0; n<whole_string.length(); n++)
		{
			char cur_char = whole_string.charAt(n);
			
			//if we are at root and new char has to be inserted
			if(act_pt.active_node==root&&act_pt.active_length==0&&root.ifEdgeExist(cur_char)==null)
			{
				Node new_node = new Node(); 
				Edge e = new Edge(n, n, new_node, true);
				root.addEdge(cur_char, e);
			}
			//we are at root and we have to insert a new character which is already present as starting point of one of the edges
			else if(act_pt.active_node==root&&act_pt.active_length==0&&root.ifEdgeExist(cur_char)!=null)
			{
				act_pt.change(root, cur_char, 1);
				remainder++;
				listOfTheWordsToBeAdded.add(0, cur_char+"");
			}
			else if(act_pt.active_node==root&&act_pt.active_length!=0&&act_pt.active_length<root.ifEdgeExist(act_pt.active_edge).getLength())
			{
				Edge e = root.ifEdgeExist(act_pt.active_edge);
				if(cur_char==whole_string.charAt(e.label.getUpdated().s+act_pt.active_length))
				{
					act_pt.change(root, act_pt.active_edge, act_pt.active_length+1);
					remainder++;
					for(int i=0;i<listOfTheWordsToBeAdded.size();i++)
					{
						listOfTheWordsToBeAdded.set(i, listOfTheWordsToBeAdded.get(i)+cur_char);
					}
					listOfTheWordsToBeAdded.add(cur_char+"");
				}
				else
				{
					for(int i=0;i<listOfTheWordsToBeAdded.size();i++)
					{
						listOfTheWordsToBeAdded.set(i, listOfTheWordsToBeAdded.get(i)+cur_char);
					}
					listOfTheWordsToBeAdded.add(cur_char+"");
					
					for(int i=0;i<listOfTheWordsToBeAdded.size();i++)
					{
						if(i==0)
						{
							Node temp = e.pointer;
							Label l  = e.label.getUpdated();
							Node new_node = new Node();
							e.pointer = new_node;
							e.label = new Label(l.s, l.s+act_pt.active_length-1, false);
							Edge new_edge = new Edge(l.s+act_pt.active_length, l.e, temp, l.isCurrentSysmbol);
							new_node.addEdge(whole_string.charAt(l.s+act_pt.active_length), new_edge);
							Node new_node2 = new Node();
							Edge new_edge2 = new Edge(n, n, new_node2, true);
							new_node.addEdge(cur_char, new_edge2);
						}
					}
				}
			}
		}
		
	}
	
}