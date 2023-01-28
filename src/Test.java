import java.util.*;

public class Test {
    static char crossArray[][] = {};

    public static void main(String[] args) {
        String textData = "one-two-three-four-five-six-seven-eight-nine-ten".toUpperCase();
        String[] textArray = textData.split("-");

        int startSize = 10;

        crossBuilder(startSize, textArray);
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

        while (placedWords < 6) {
            if (tryPlace(wordList.get(0), placedWords)) {
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
            if (tryHorizontal(word)) {
                return true;
            } else {
                return tryVertical(word);
            }
        }
    }


    static Boolean tryVertical(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        int countPlus = 0;
                        int countMinus = 0;
                        for (int k = 0; k < word.length(); k++) {
                            try {
                                if (crossArray[i + k - c][j] != '#' && crossArray[i + k - c][j] != word.charAt(k)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                return false;
                            }


                            Boolean[] result = verifyPosition((i + k - c), j, word.charAt(k), false);

                            if(crossArray[i + k - c][j] == word.charAt(k)){
                                if(!result[0]){
                                    countPlus++;
                                }
                                else{
                                    countPlus = 0;
                                }

                                if(!result[1]){
                                    countMinus++;
                                }
                                else{
                                    countMinus = 0;
                                }
                            }
                            else if(crossArray[i + k - c][j] == '#' && result[0] && result[1]){
                                countPlus = 0;
                                countMinus = 0;
                            }
                            else{
                                return false;
                            }

                            if(countPlus >= 2 || countMinus >= 2){
                                System.out.println("HOW???");
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

    static Boolean[] verifyPosition(int x, int y, char letter, boolean isHorizontal) {
        int xPlus = 0;
        int yPlus = 0;
        if (isHorizontal) {
            yPlus = 1;
        } else {
            xPlus = 1;
        }

        boolean flagPlus = false;
        boolean flagMinus = false;


        if (y + yPlus > crossArray.length || x + xPlus > crossArray.length ||
                y + yPlus < crossArray.length || x + xPlus < crossArray.length) {
            flagPlus = true;
        } else if (crossArray[x + xPlus][y + yPlus] == '#') {
            flagPlus = true;
        }

        if (y - yPlus > crossArray.length || x - xPlus > crossArray.length ||
                y - yPlus < crossArray.length || x - xPlus < crossArray.length) {
            flagMinus = true;
        } else if (crossArray[x - xPlus][y - yPlus] == '#') {
            flagMinus = true;
        }

        return new Boolean[]{flagPlus, flagMinus};
    }


    static Boolean tryHorizontal(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        int countPlus = 0;
                        int countMinus = 0;

                        for (int k = 0; k < word.length(); k++) {
                            try {
                                if (crossArray[i][j + k - c] != '#' && crossArray[i][j + k - c] != word.charAt(k)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                return false;
                            }

                            Boolean[] result = verifyPosition(i, (j + k - c), word.charAt(k), true);

                            if(crossArray[i][j + k - c] == word.charAt(k)){
                                if(!result[0]){
                                    countPlus++;
                                }
                                else{
                                    countPlus = 0;
                                }

                                if(!result[1]){
                                    countMinus++;
                                }
                                else{
                                    countMinus = 0;
                                }
                            }
                            else if(crossArray[i][j + k - c] == '#' && result[0] && result[1]){
                                countPlus = 0;
                                countMinus = 0;
                            }
                            else{
                                return false;
                            }


                            if(countPlus >= 2 || countMinus >= 2){
                                System.out.println("HOW???");
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


