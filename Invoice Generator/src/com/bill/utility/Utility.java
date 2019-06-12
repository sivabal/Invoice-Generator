package com.bill.utility;

public class Utility {

	public static void main(String[] args) {
		System.out.println(rupeeInWords(1912500));

	}
	
	public static String rupeeInWords(int rupee) {
		
		String[][] words = new String[7][];
		words[0] = new String[]{"", " One"," Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine"," Ten", " Eleven",
				" Twelve", " Thirteen", " Fourteen", " Fifteen" ," Sixteen" ," Seventeen", " Eighteen", " Ninteen"};
		
		words[1] = new String[]{"", "", " Twenty", " Thirty", " Fourty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninty"};
		
		words[2] = new String[]{"", " One Hundered", " Two Hundered", " Three Hundered", " Four Hundered"," Five Hundered", " Six Hundered",
				" Seven Hundered", " Eight Hundered", " Nine Hundered"};
		
		words[3] = new String[]{"", " One Thousand", " Two Thousand", " Three Thousand", " Four Thousand", " Five Thousand", 
				" Six Thousand", " Seven Thousand", " Eight Thousand", " Nine Thousand", " Ten Thousand"," Eleven Thousand", " Twelve Thousand", 
				" Thirteen Thousand"," Fourteen Thousand", " Fifteen Thousand", " Sixteen Thousand", " Seventeen Thousand", " Eighteen Thousand", 
				" Ninteen Thousand"};
		
		words[4] = new String[]{"", "", " Twenty", " Thirty", " Fourty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninty"};
		
		words[5] = new String[]{"", " One Lakh", " Two Lakh", " Three Lakh", " Four Lakh", " Five Lakh", 
				" Six Lakh", " Seven Lakh", " Eight Lakh", " Nine Lakh", " Ten Lakh" ," Eleven Lakh", " Twelve Lakh" ," Thirteen Lakh",
				" Fourteen Lakh", " Fifteen Lakh", " Sixteen Lakh", " Seventeen Lakh", " Eighteen lakh", " Ninteen Lakh"};
		
		words[6] = new String[]{"", "", " Twenty", " Thirty", " Fourty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninty"};
		
		StringBuilder result = new StringBuilder("");

		for(int i = 0;rupee > 0;i++) {
			//checking if '1' is present in the 'Tens', 'Ten thousand' and 'Ten lakh' position
			//if it is present then words become ten, eleven, twelve, etc,..
			if((i==0 || i== 3 || i== 5) && (rupee/10)%10 == 1 ) {
				result.insert(0, words[i][10+(rupee%10)]);
				i++;
				rupee = rupee/10;
			}
			else result.insert(0, words[i][rupee%10] + ((i == 2 && result.length() != 0)?" and":""));
			
			rupee = rupee/10;
		}

		return result.insert(0, "Rupees").append(" Only").toString();
	}

}