package cfung58;

import java.util.List;
import java.io.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set; 

public class TuringMachineIntrepeter {

	//interpreter code 
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//declare array list of transitions, final states and inputs 
		List<String> transitions = new ArrayList<String>(); 
		Map<Integer, List<String>> transitions_hashmap = new  HashMap<Integer, List<String>>();
		
		ArrayList<String> final_states = new ArrayList<String>(); 
		ArrayList<String>input = new ArrayList<String>(); 
		
		
		
		System.out.println("initial size of input:  " + input.size()); 
		
		// Open the file from input.txt 
		FileInputStream fstream = new FileInputStream("input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		
		//counter for counting transition to be put into map 
		//ASSUMPTION: the input always start with state 0 
		int z =  0;
		int iterator = 0 ;

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		  
			// Print the content on the console
			//System.out.println (strLine); 
		  
			//char[] charArray = null; 
			
			//TODO: make sure double digits become one element in array 
			String[] str_array = strLine.split("\\s");
			
			
		  char[] charArray = strLine.toCharArray();
		  //System.out.println(charArray[0]); 
		  
		  if (charArray[0] == 't'){
			  System.out.println("transition MOTHERFUCKER"); 
			  //get the initial state 
			  //ASSUMPTION: the input always start with state 0 
			  //iterator = Integer.parseInt(Character.toString(charArray[2])); 
			  iterator = Integer.parseInt(str_array[1]); 
			  
			  //at the beginning 
			  if (z == iterator){
				  System.out.println("z == iterator"); 
				  
				  //TODO: also remember to add the strLine to transitions when you're done 
				  
				  //get rid of the t at the beginning 
				  strLine = strLine.substring(2);
				  strLine = strLine.replaceAll("\\s","-"); 
				  //String[] str_array = strLine.split("\\s");
				  //System.out.println (strLine); 
				  
				  transitions.add(strLine); 
			
				  Integer key = new Integer(z);
				  transitions_hashmap.put(key, transitions);  
			  }
			  else {
				  System.out.println("z != iterator"); 
				  //add transitions to the transitions_hashmap
				  Integer key = new Integer(z);
				  transitions_hashmap.put(key, transitions); 
				  
				  
				  //redefine transitions with new ArrayList 
				  transitions = new ArrayList<String>(); 
				  
				  z = iterator;  
				  
				  //get rid of the t at the beginning 
				  strLine = strLine.substring(2);
				  strLine = strLine.replaceAll("\\s","-");
				  //String[] str_array = strLine.split("\\s");
				  //System.out.println (strLine); 
				  
				  transitions.add(strLine); 
				  
				  Integer key2 = new Integer(z);
				  transitions_hashmap.put(key2, transitions);  
			  } 
			  
		  }
		  else if (charArray[0] == 'f'){
			  System.out.println("final state MOTHERFUCKER"); 
			  
			  //since all the final/accepting states will be in one line, we need to separate them 
			  //i starts from 2, assuming that 'f' takes up 0, \0 takes up 1, and the first state takes up index 2
			  strLine = strLine.substring(2); 
			  String[] str_array2 = strLine.split("\\s"); 
			  
			  for (int i = 0 ; i < str_array2.length ; i++){
				  final_states.add(str_array2[i]); 
			  }
			  
			  
		  }
		  else if (charArray[0] == 'i'){
			  System.out.println("INPUT MOTHERFUCKER");
			  
			  strLine = strLine.replaceAll("\\s",""); 
			  strLine = strLine.substring(1); 
			  System.out.println (strLine); 
			  input.add(strLine); 
		  }
		  
		}

		//Close the input stream
		br.close();
		
		
		//testing
		/*
		System.out.println(transitions_hashmap.size()); 
		for (int i = 0 ; i < transitions_hashmap.size() ; i++){
			List<String> tests = transitions_hashmap.get(i); 
			for (int j = 0 ; j < tests.size(); j++){
				System.out.println("tests: "+ tests.get(j)); 
			}
		}
		
		
		System.out.println(final_states.size()); 
		System.out.println(final_states); 
		*/
		BufferedWriter out = new BufferedWriter (new FileWriter("output.txt")); 
		
		
		for (int i = 0 ; i < input.size(); i++ ){
			String new_input = new StringBuilder("Z").append(input.get(i)).append("Z").toString(); 
			//System.out.println(new_input); 
			
			String[] newer_input = new_input.split(""); 
			//System.out.println(newer_input); // [,Z,a,#,a, Z]
			String[] newest_input = new String[newer_input.length]; 
			System.arraycopy(newer_input, 0, newest_input, 0, newest_input.length);
			//System.out.println(newest_input); 
			
											
			//transition, finish, input 
			String[] return_result = TuringMachine(transitions_hashmap, final_states, newest_input); 
			
			//TODO: print it to output.txt 
			out.write(return_result[0] + "\n");
			out.write(return_result[1] + "\n"); 
			
			
		}
		out.close(); 
		
	

	}//end public main 

	
	//turing machine code 
	private static String[] TuringMachine(
			Map<Integer, List<String>> transitions_hashmap,
			ArrayList<String> final_states, String[] input_StringArray) {
		// TODO Auto-generated method stub
		
		String[] return_result = new String[2]; 
		
		int current_state = 0; //ASSUMPTION: the starting state is always 0 
		int head = 1; //it is at 1 because there's a Z at 0 
		Boolean found = false; 
		
		//input_StringArray = (input_StringArray.toString()).split("-"); 
		
		
		while(!found){
			//ASSUMPTION: current_state will always be in tests 
			//find the transition(s) with the current_state 
			List<String> tests = transitions_hashmap.get(current_state);
			
			
			//iterate through the transition(s) that have the current state 
			for (int j = 0 ; j < tests.size(); j++){
				//TODO: check for head here 
				//add Zs to both ends 
				
				//ERROR HERE 
				//TODO: figure a way not to use char, but string[] 
				
				String[] tests_string = tests.get(j).split("-"); 
				//String[] tests_string_old = tests.get(j).split("-"); 
				//String[]  tests_string= new String[tests_string_old.length-1]; 
				//System.arraycopy(tests_string_old, 1, tests_string, 0, tests_string.length);
				
				
				//if the input symbol matches the symbol at the head 
				if (tests_string[1].equals(input_StringArray[head])){
					
					//set current_state to next state from the transition 
					current_state = Integer.parseInt(tests_string[2]);  
					
					//write symbol 
					input_StringArray[head] = tests_string[3];
					
					//head movement direction 
					if (tests_string[4].equals("R")){
						head++; 
						found = false; 
						break; 
					}
					else if (tests_string[4].equals("L")){
						head--; 
						found = false; 
						break; 
					}
					else if (tests_string[4].equals("H")){
						found = true; 
						break; 
					}
				}
				else 
					found = true; 
					
				
			}//END FOR LOOP
			
			
			
		}//end while loop 
		
		String message = "REJECTED"; 
		//see if the current_state is in the final_states
		for (int i = 0 ; i < final_states.size(); i++){
			if (current_state == Integer.parseInt(final_states.get(i))){
				message = "ACCEPTED"; 
				break; 
			}
		}
		
		//remove the Zs from the input 
		//ASSUMPTION: 1 Z at both ends 
		int begin = 0; 
		int end = 0; 
		int i = 0; 
		//System.out.println(input_StringArray);
		
		int j = input_StringArray.length -1; 
		if (input_StringArray[i+1] != "Z"){
			begin = i+1; 
		}
		else{
			begin = i; 
		}
		
			
		
		if (!input_StringArray[j].equals("Z")){
			end = j; 
		}
		else{
			end = j-1; 
		}
		
		//special case if there's two just two Zs 
		if (input_StringArray[0].equals("Z") && input_StringArray[1].equals("Z")){
			return_result[0] = ""; 
		}
		else{
			//return_result[0] = (input_StringArray.toString()).substring(begin, end); 
			StringBuilder newString = new StringBuilder(); 
			
			for (int k = begin ; k <= end ; k++){
				newString.append(input_StringArray[k]); 
			}
			return_result[0] = newString.toString();  
		}
		
		
		return_result[1] = message; 
		
		System.out.println("return_result: " + return_result[0]); 
		System.out.println("message: " + message); 
		
		//return the return_result
		return return_result; 
	}
	
	
}//end class 