public class RomanNumbers {

	public static int convert(String roman){
		
		int[] array = new int[15];
		int result = 0;

		for(int i = 0; i < roman.length(); i++)
			array[i] = convertChar(roman.charAt(i));

		for(int i = 0; i < roman.length(); i++)
			if(array[i] < array[i+1]){
				result += (array[i+1] - array[i]);
				i++;
			}else
				result += array[i];
		
		return result;
	}

	public static int convertChar(char letter){
		switch(letter){
			case 'I': return 1;
			case 'V': return 5;
			case 'X': return 10;
			case 'L': return 50;
			case 'C': return 100;
			case 'D': return 500;
			case 'M': return 1000;
			default: throw new NumberFormatException("Not a roman number.");
		}
	}
}