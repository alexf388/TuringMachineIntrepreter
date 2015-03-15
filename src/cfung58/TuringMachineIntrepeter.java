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
		ArrayList<String> new_transitions = new ArrayList<String>(); 
		Map<Integer, List<String>> transitions_hashmap = new  HashMap<Integer, List<String>>();
		List <Integer> keys  = new ArrayList <Integer>(); 
		
		ArrayList<String> final_states = new ArrayList<String>(); 
		ArrayList<String>input = new ArrayList<String>(); 
		
		ArrayList<String> output = new ArrayList<String>(); 
		
		
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
		  
		  char[] charArray = strLine.toCharArray(); 
		  //System.out.println(charArray[0]); 
		  
		  if (charArray[0] == 't'){
			  System.out.println("transition MOTHERFUCKER"); 
			  //get the initial state 
			  //ASSUMPTION: the input always start with state 0 
			  iterator = Integer.parseInt(Character.toString(charArray[2])); 
			  
			  //at the beginning 
			  if (z == iterator){
				  System.out.println("z == iterator"); 
				  
				  //TODO: also remember to add the strLine to transitions when you're done 
				  strLine = strLine.replaceAll("\\s",""); 
				  
				  //get rid of the t at the beginning 
				  strLine = strLine.substring(1);
				  System.out.println (strLine); 
				  
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
			  
				  strLine = strLine.replaceAll("\\s",""); 
				  
				  //get rid of the t at the beginning 
				  strLine = strLine.substring(1);
				  System.out.println (strLine); 
				  
				  transitions.add(strLine); 
			  } 
			  
		  }
		  else if (charArray[0] == 'f'){
			  System.out.println("final state MOTHERFUCKER"); 
			  
			  //since all the final/accepting states will be in one line, we need to separate them 
			  //i starts from 2, assuming that 'f' takes up 0, \0 takes up 1, and the first state takes up index 2
			  strLine = strLine.substring(2); 
			  char[] temp_array = strLine.toCharArray(); 
			  
			  
			  
			  for (int i = 0 ; i < temp_array.length ; i++){
				  //ASSUMPTION: there will be no more than 99 fucking final states 
				  if (i+1 < temp_array.length){
					  if (temp_array[i+1] != ' '){
						  String s = new StringBuilder().append(temp_array[i]).append(temp_array[i+1]).toString();
						  final_states.add(s);
					  }
					  else {
						  String s = new StringBuilder().append(temp_array[i]).toString(); 
						  final_states.add(s); 
						  i++; 
					  }
				  }
				  else{
					  String s = new StringBuilder().append(temp_array[i]).toString(); 
					  final_states.add(s);
				  }
					  
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
		
		
		for (int i = 0 ; i < input.size(); i++ ){
						
			char[] temp_charArray = input.get(i).toCharArray(); 
			char[] input_charArray = new char[temp_charArray.length + 2]; //2 more for the Zs at the front and back  
			input_charArray[0] = 'Z'; 
			input_charArray[input_charArray.length-1] = 'Z'; 
			System.arraycopy(temp_charArray, 0, input_charArray,1 , input_charArray.length - 2 );
						
					
			//transition, finish, input 
			String[] return_result = TuringMachine(transitions_hashmap, final_states, input_charArray); 
			
			//TODO: print it to output.txt 
			
		}
		
		
	

	}//end public main 

	
	//turing machine code 
	private static String[] TuringMachine(
			Map<Integer, List<String>> transitions_hashmap,
			ArrayList<String> final_states, char[] input_charArray) {
		// TODO Auto-generated method stub
		
		String[] return_result = new String[2]; 
		
		int current_state = 0; //ASSUMPTION: the starting state is always 0 
		int head = 1; //it is at 1 because there's a Z at 0 
		char input_symbol = ' '; 
		Boolean found = false; 
		
		while(!found){
			//ASSUMPTION: current_state will always be in tests 
			//find the transition(s) with the current_state 
			List<String> tests = transitions_hashmap.get(current_state);
			
			
			//iterate through the transition(s) that have the current state 
			for (int j = 0 ; j < tests.size(); j++){
				//TODO: check for head here 
				//add Zs to both ends 
				
				char[] tests_char = tests.get(j).toCharArray(); 
				
				//if the input symbol matches the symbol at the head 
				if (tests_char[1] == input_charArray[head]){
					
					//set current_state to next state from the transition 
					current_state = Integer.parseInt(Character.toString(tests_char[2]));  
					
					//write symbol 
					input_charArray[head] = tests_char[3];
					
					//head movement direction 
					if (tests_char[4] == 'R'){
						head++; 
						break; 
					}
					else if (tests_char[4] == 'L'){
						head--; 
						break; 
					}
					else if (tests_char[4] == 'H'){
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
		System.out.println(input_charArray);
		
		int j = input_charArray.length -1; 
		if (input_charArray[i+1] != 'Z'){
			begin = i+1; 
		}
		else{
			begin = i; 
		}
		
			
		
		if (input_charArray[j] == 'Z'){
			end = j; 
		}
		else{
			end = j-1; 
		}
		
		//special case if there's two just two Zs 
		if (input_charArray[0]== 'Z' && input_charArray[1] == 'Z'){
			return_result[0] = ""; 
		}
		else
			return_result[0] = (String.valueOf(input_charArray)).substring(begin, end); 
		
		
		return_result[1] = message; 
		
		System.out.println("return_result: " + return_result[0]); 
		System.out.println("message: " + message); 
		
		//return the return_result
		return return_result; 
	}
	
	
}//end class 
