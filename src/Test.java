import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
    static char crossArray[][] = {};
    public static void main(String[] args) {
        String textData = "one-two-three-four-five-six-seven-eight-nine-ten".toUpperCase();
        String[] textArray = textData.split("-");

        int startSize = 10;

        crossBuilder(startSize, textArray);
    }

    static String[] crossBuilder(int size, String[] textArray){
        crossArray = new char[size][size];

        for (int i = 0; i < crossArray.length; i++)   {
            for (int j = 0; j < crossArray.length; j++)   {
                crossArray[i][j] = '#';
            }
        }

        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < textArray.length; i++){
            String tempString = textArray[i];
            int indexToSwap = random.nextInt(textArray.length);

            textArray[i] = textArray[indexToSwap];
            textArray[indexToSwap] = tempString;
        }
        List<String> wordList = Arrays.stream(textArray).toList();
        int placedWords = 0;
        tryPlace(wordList.get(0), placedWords);

        for(int i = 0; i < textArray.length; i++){
            System.out.println(textArray[i]);
        }

        for (int i = 0; i < crossArray.length; i++)   {
            for (int j = 0; j < crossArray.length; j++)   {
                if(crossArray[i][j] != '#'){
                    System.out.print(" " + ConsoleColors.BLUE_BOLD + crossArray[i][j] + ConsoleColors.RESET + " ");
                }
                else{
                    System.out.print(" " + crossArray[i][j] + " ");
                }
            }
            System.out.println();
        }

        return null;
    }

    static Boolean tryPlace(String word, int placedWords){
        if (placedWords == 0){
            if(word.length() <= crossArray.length){
                int position = (crossArray.length - word.length()) / 2;
                for (int i = 0; i < word.length(); i++){
                    // Change it to be random direction later
                    crossArray[i+position][position] = word.charAt(i);
                }
            }
            else{
                return false; // error too maybe
            }
        }
        else{
            for (int i = 0; i < crossArray.length; i++)   {
                for (int j = 0; j < crossArray.length; j++)   {

                }
            }
        }

        return false;
    }
}
