import java.util.*;

public class Test {
    static char crossArray[][] = {};

    public static void main(String[] args) {
        String textData = "one-two-three-four-five-six-seven-eight-nine-ten".toUpperCase();
        String[] textArray = textData.split("-");

        int startSize = 10;

        crossBuilder(startSize, textArray);

        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                if (crossArray[i][j] != '#') {
                    System.out.print(" " + ConsoleColors.BLUE_BOLD + crossArray[i][j] + ConsoleColors.RESET + " ");
                } else {
                    System.out.print(" " + crossArray[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    static String[] crossBuilder(int size, String[] textArray) {
        crossArray = new char[size][size];

        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                crossArray[i][j] = '#';
            }
        }

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < textArray.length; i++) {
            String tempString = textArray[i];
            int indexToSwap = random.nextInt(textArray.length);

            textArray[i] = textArray[indexToSwap];
            textArray[indexToSwap] = tempString;
        }

        List<String> wordList = new ArrayList<>(Arrays.asList(textArray));
        int placedWords = 0;

        while (placedWords < 5) {
            if (tryPlace(wordList.get(0), placedWords)) {
                wordList.remove(0);
                placedWords++;
            } else {
                String word = wordList.get(0);
                wordList.remove(0);
                wordList.add(word);
            }
        }

        for (int i = 0; i < textArray.length; i++) {
            System.out.println(textArray[i]);
        }

        return null;
    }

    static Boolean tryPlace(String word, int placedWords) {

        if (placedWords == 0) {
            if (word.length() <= crossArray.length) {
                int position = (crossArray.length - word.length()) / 2;
                for (int i = 0; i < word.length(); i++) {
                    // Change it to be random direction later
                    crossArray[i + position][position + 1] = word.charAt(i);
                }
                System.out.println(word);
                return true;
            } else {
                return false; // error too maybe
            }
        } else {
            if(tryHorizontal(word)){
                return true;
            }
            else{
                return tryVertical(word);
            }
        }
    }


    static Boolean tryVertical(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        for (int k = 0; k < word.length(); k++) {
                            try {
                                if (crossArray[i + k - c][j] != '#' && crossArray[i + k - c][j] != word.charAt(k)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                return false;
                            }
                        }
                        for (int k = 0; k < word.length(); k++) {
                            crossArray[i + k - c][j] = word.charAt(k);
                        }
                        System.out.println(word);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static Boolean tryHorizontal(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        for (int k = 0; k < word.length(); k++) {
                            try {
                                if (crossArray[i][j + k - c] != '#' && crossArray[i][j + k - c] != word.charAt(k)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                return false;
                            }
                        }
                        for (int k = 0; k < word.length(); k++) {
                            crossArray[i][j + k - c] = word.charAt(k);
                        }
                        System.out.println(word);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


