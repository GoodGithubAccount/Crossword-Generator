import java.util.Random;

public class Test {

    public static void main(String[] args) {
        String textData = "one-two-three-four-five-six-seven-eight-nine-ten".toLowerCase();
        String[] textArray = textData.split("-");

        int startSize = 10;

        crossBuilder(startSize, textArray);

    }

    static String[] crossBuilder(int size, String[] textArray){
        String[][] crossArray = new String[size][size];

        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < textArray.length; i++){
            String tempString = textArray[i];
            int indexToSwap = random.nextInt(textArray.length);

            textArray[i] = textArray[indexToSwap];
            textArray[indexToSwap] = tempString;
        }

        for(int i = 0; i < textArray.length; i++){
            System.out.println(textArray[i]);
        }

        return null;
    }



}
