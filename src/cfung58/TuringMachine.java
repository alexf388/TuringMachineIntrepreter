package cfung58;

import java.awt.List;
import java.io.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set; 

public class TuringMachine {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//declare array list of transitions, final states and inputs 
		ArrayList<String> transitions = new ArrayList<String>(); 
		ArrayList<String> new_transitions = new ArrayList<String>(); 
		Map<Integer, ArrayList<String>> transitions_hash_table = new  HashMap<Integer, ArrayList<String>>();
		Set <Integer> keys; 
		
		ArrayList<String> final_states = new ArrayList<String>(); 
		ArrayList<String>input = new ArrayList<String>(); 
		
		ArrayList<String> output = new ArrayList<String>(); 
		
		
		System.out.println("initial size of input:  " + input.size()); 
		
		// Open the file from input.txt 
		FileInputStream fstream = new FileInputStream("input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		
		//counter for counting transition to be put into map 
		int z =  -1; 

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
		  //System.out.println (strLine); 
		  
		  char[] charArray = strLine.toCharArray(); 
		  //System.out.println(charArray[0]); 
		  
		  if (charArray[0] == 't'){
			  if (z == -1){
				  System.out.println("YIPEE KAY YAY MOTHERFUCKER"); 
			  }
			  System.out.println("Transition MOTHERFUCKER"); 
			  
			  strLine = strLine.replaceAll("\\s",""); 
			  System.out.println (strLine); 
			  transitions.add(strLine); 
			  
		  }
		  else if (charArray[0] == 'f'){
			  System.out.println("final state MOTHERFUCKER"); 
			  
			  strLine = strLine.substring(2); 
			  System.out.println (strLine); 
			  final_states.add(strLine); 
		  }
		  else if (charArray[0] == 'i'){
			  System.out.println("INPUT MOTHERFUCKER");
			  
			  strLine = strLine.replaceAll("\\s",""); 
			  System.out.println (strLine); 
			  input.add(strLine); 
		  }
		  
		}

		//Close the input stream
		br.close();
		
		//getting rid of the ts 
		for (int i = 0 ; i < transitions.size(); i++){
			char[] charArray = transitions.get(i).toCharArray(); 
			//System.out.println(charArray); 
			
			//key is now charArray[1], i.e. the first state 
			//builds string for value 
			String value = new StringBuilder().append(charArray[1]).append(charArray[2]).append(charArray[3]).append(charArray[4]).append(charArray[5]).toString(); 
			//System.out.println("charArray[1]: " + charArray[1]); 
			System.out.println("value: " + value); 
			
 			new_transitions.add(value); 
		}
				
		//time to go through each input and see
		System.out.println("input.size: "+ input.size()); 
		for (int i = 0 ; i < input.size(); i++){
			
			char current_letter; 
			int current_state = 0; 
			int head = 1; //starts from 1 because 0 is always i, for input 
			Boolean found = false; 
			//Boolean transition_exists = false; 
			
			char[] temp_charArray = input.get(i).toCharArray(); 
			char[] input_charArray = new char[temp_charArray.length + 1]; //2 more for the Zs at the front and back  
			input_charArray[0] = 'Z'; 
			input_charArray[input_charArray.length - 1] = 'Z'; 
			System.arraycopy(temp_charArray, 1, input_charArray,1 , input_charArray.length - 2 );
			
			
			
			System.out.println("charArray size: "+ input_charArray.length);
			
			//while not found is true 
			//while (!found){
				
				for (int j = 0 ; j < new_transitions.size(); j++){	
					char[] new_transitions_charArray = new_transitions.get(j).toCharArray(); 
					
					//convert char array into string array because it's messed up converting from char to int, vice versa
					String[] new_transitions_stringArray = new String[new_transitions_charArray.length]; 
					new_transitions_stringArray[0] = Character.toString(new_transitions_charArray[0]); 
					new_transitions_stringArray[1] = Character.toString(new_transitions_charArray[1]); 
					new_transitions_stringArray[2] = Character.toString(new_transitions_charArray[2]); 
					new_transitions_stringArray[3] = Character.toString(new_transitions_charArray[3]); 
					new_transitions_stringArray[4] = Character.toString(new_transitions_charArray[4]); 
					
	
					//System.out.println("current_state_char: " + current_state_char);
					//if the state in the transition is equal to the current_state 
					if (Integer.parseInt(new_transitions_stringArray[0]) ==  current_state){
						//read from the latest input 
						
						if (new_transitions_stringArray[1].equals(Character.toString(input_charArray[head]))){
							//set current_state to the next state 
							current_state = Integer.parseInt(new_transitions_stringArray[2]); 
							
							input_charArray[head] = new_transitions_stringArray[3].charAt(0); 
							
							if (new_transitions_stringArray[4].equals("R")){
								head +=1; 
								//transition_exists = true; 
							}else if (new_transitions_stringArray[4].equals("L")){
								//assumption: head does not go lower than 0 
								head -=1; 
								//transition_exists = true; 
							}else if (new_transitions_stringArray[4].equals("H")){
								//found = true; 
								System.out.println("HOLD1 MOTHERFUCKER");
								break; 
							}
							
							//check if head is bigger than input_charArray size 
							if (head == input_charArray.length-1){
								//increase size
								input_charArray = Arrays.copyOf(input_charArray, input_charArray.length*2); 
							}
							//else if the head is less than 0 
							else if (head < 0){
								//TODO: implement logic if head < 0 
								//i.e. goes left 
								//TODO: also get rid of the i in the input_charArray
							}
							
							//check if current_state is already
							
						}
						//else
						else if (( input_charArray[head+1] == 'Z' || input_charArray[head-1] == 'Z' )&& Integer.parseInt(new_transitions_stringArray[0]) ==  current_state ){	
							
							if (new_transitions_stringArray[4].equals("H")){
								//found = true; 
								current_state = Integer.parseInt(new_transitions_stringArray[2]); 
								
								System.out.println("HOLD2 MOTHERFUCKER");
								break; 
							}
							
						}
						
						
					}
					//else
					else{
						//do nothing  
					}
					
				}//end of second for loop 

			
			//for loop to check the end states 
			boolean accepted = false; 
			
			for (int k = 0 ; k < final_states.size(); k++){
				String current_state_string = Integer.toString(current_state); 
				
				if (current_state_string.equals(final_states.get(k))){
					accepted = true; 
					break; 
				}
			}
			//if accepted = true, then accepted
			if (accepted)
				System.out.println("ACCEPTED!"); 		
			//else, rejected
			else
				System.out.println("REJECTED!");
			
			
			
		}//end of first for loop 
		
		//open the file for output.txt 
		
	}

}
