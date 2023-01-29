import java.util.*;

public class Test {
    static char crossArray[][] = {};
    static char savedCrossArray[][] = {};

    // Times to try with no progress before giving up
    // TODO: Implement
    static int tryCrosswordLimit = 5000;

    // Other method is sorted by length. Longest first.
    // TODO: Make an enum with more options
    static boolean useRandomMethod = false;

    // Size to start at.
    // TODO: Shrink on success and try again.
    static int startSize = 30;

    // Whether to always print the generated crossword
    // Or if program should only print when a better one has been created
    // TODO: Implement
    static boolean alwaysPrint = false;

    public static void main(String[] args) {

        String textData = "one-two-three-four-five-six-seven-eight-nine-ten".toUpperCase(); // Old small test data.
        String[] textArray = testData.split("\n");

        int placedWords = 0;
        int largestSoFar = 0;
        while (placedWords < textArray.length) {
            placedWords = crossBuilder(startSize, textArray, useRandomMethod);
            if (placedWords > largestSoFar) {
                largestSoFar = placedWords;
                savedCrossArray = crossArray;

                for (int i = 0; i < savedCrossArray.length; i++) {
                    for (int j = 0; j < savedCrossArray.length; j++) {
                        if (savedCrossArray[i][j] != '#') {
                            System.out.print(" " + ConsoleColors.RED_BOLD + savedCrossArray[i][j] + ConsoleColors.RESET + " ");
                        } else {
                            System.out.print(" " + savedCrossArray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("Largest so far: " + largestSoFar + "/" + textArray.length);
            }
        }
    }

    static int crossBuilder(int size, String[] textArray, boolean useRandomMethod) {
        crossArray = new char[size][size];

        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                crossArray[i][j] = '#';
            }
        }

        if (useRandomMethod) textArray = randomArray(textArray);
        else textArray = sortedByLengthArray(textArray);

        List<String> wordList = new ArrayList<>(Arrays.asList(textArray));

        int placedWords = 0;
        int attempts = 0;

        // TODO: Should prob update this to also be - placedWords.
        //  Insignificant change rn tho
        int maxAttempts = textArray.length;

        Random random = new Random(System.currentTimeMillis());
        while (placedWords < textArray.length && attempts < maxAttempts) {
            boolean randomDirection = random.nextBoolean();
            if (tryPlace(wordList.get(0), placedWords, randomDirection)) {
                placedWords++;
                attempts = 0;
                wordList.remove(0);
            } else {
                attempts++;
                String word = wordList.get(0);
                wordList.remove(0);
                wordList.add(word);
            }
        }

        // TODO: Hook up to always print or not,
        //  program probably needs a delay if alwaysprint is on.
        /*
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
        System.out.println("Total Words: " + placedWords + "/" + textArray.length);
        */

        return placedWords;
    }

    static String[] randomArray(String[] textArray) {

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < textArray.length; i++) {
            String tempString = textArray[i];
            int indexToSwap = random.nextInt(textArray.length);

            textArray[i] = textArray[indexToSwap];
            textArray[indexToSwap] = tempString;
        }

        return textArray;
    }

    static String[] sortedByLengthArray(String[] textArray) {
        Arrays.sort(textArray, Comparator.comparingInt(String::length));

        for (int i = 0; i < textArray.length / 2; i++) {
            String temp = textArray[i];
            textArray[i] = textArray[textArray.length - i - 1];
            textArray[textArray.length - i - 1] = temp;
        }


        // TODO: Make this optional with a setting.
        Random random = new Random(System.currentTimeMillis());
        // TODO: Implement better so its actually group size and not just used for division.
        //  Right now its not very transparent because it works inversely. E.g higher number smaller groups.
        //  Also doesnt work correctly at all numbers cause bad division. Implement modulus
        int groupSize = 10;
        for(int j = 0; j < textArray.length/groupSize; j++){
            for (int i = 0; i < textArray.length / groupSize; i++) {
                String tempString = textArray[(j * groupSize) + i];
                int indexToSwap = random.nextInt(textArray.length / groupSize) + (j * groupSize);
                if(indexToSwap > textArray.length){
                    indexToSwap -= (indexToSwap - textArray.length);
                }

                textArray[(j * groupSize) + i] = textArray[indexToSwap];
                textArray[indexToSwap] = tempString;
            }
        }



        return textArray;
    }

    static Boolean tryPlace(String word, int placedWords, boolean randomDirection) {

        if (placedWords == 0) {
            if (word.length() <= crossArray.length) {
                int position = (crossArray.length - word.length()) / 2;
                for (int i = 0; i < word.length(); i++) {
                    // Change it to be random direction later
                    crossArray[i + position][position + 1] = word.charAt(i);
                }
                return true;
            } else {
                return false; // error too maybe
            }
        } else {
            if (randomDirection) {
                if (tryHorizontal(word)) {
                    return true;
                } else {
                    return tryVertical(word);
                }
            } else {
                if (tryVertical(word)) {
                    return true;
                } else {
                    return tryHorizontal(word);
                }
            }
        }
    }


    static Boolean tryVertical2(String word, int i, int c, int j) {
        try {
            if (crossArray[i - c - 1][j] != '#') return false;
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        try {
            if (crossArray[i - c + word.length()][j] != '#') return false;
        } catch (ArrayIndexOutOfBoundsException e) {

        }


        int countPlus = 0;
        int countMinus = 0;
        int letterCount = 0;
        for (int k = 0; k < word.length(); k++) {
            try {
                if (crossArray[i + k - c][j] != '#' && crossArray[i + k - c][j] != word.charAt(k)) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }


            Boolean[] result = verifyPosition((i + k - c), j, word.charAt(k), false);

            if (crossArray[i + k - c][j] == word.charAt(k)) {
                letterCount++;
                if (!result[0]) {
                    countPlus++;
                } else {
                    countPlus = 0;
                }

                if (!result[1]) {
                    countMinus++;
                } else {
                    countMinus = 0;
                }
            } else if (crossArray[i + k - c][j] == '#') {
                if (!result[0]) {
                    return false;
                } else {
                    countPlus = 0;
                }

                if (!result[1]) {
                    return false;
                } else {
                    countMinus = 0;
                }
            } else {
                return false;
            }

            if (countPlus >= 2 || countMinus >= 2 || letterCount == word.length()) {
                if (letterCount == word.length()) {
                    System.out.println("TEST\n\n\nTEST\n\n\nTEST");
                }
                return false;
            }
        }
        for (int k = 0; k < word.length(); k++) {
            crossArray[i + k - c][j] = word.charAt(k);
        }
        return true;
    }

    static Boolean tryVertical(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        if (tryVertical2(word, i, c, j)) return true;
                    }
                }
            }
        }
        return false;
    }

    static Boolean[] verifyPosition(int y, int x, char letter, boolean isHorizontal) {
        int xPlus = 0;
        int yPlus = 0;
        if (isHorizontal) {
            yPlus = 1;
        } else {
            xPlus = 1;
        }

        boolean flagPlus = false;
        boolean flagMinus = false;

        if (y + yPlus >= crossArray.length || x + xPlus >= crossArray.length ||
                y + yPlus < 0 || x + xPlus < 0) {
            flagPlus = true;
        } else if (crossArray[y + yPlus][x + xPlus] == '#') {
            flagPlus = true;
        }

        if (y - yPlus >= crossArray.length || x - xPlus >= crossArray.length ||
                y - yPlus < 0 || x - xPlus < 0) {
            flagMinus = true;
        } else if (crossArray[y - yPlus][x - xPlus] == '#') {
            flagMinus = true;
        }

        return new Boolean[]{flagPlus, flagMinus};
    }

    static Boolean tryHorizontal2(String word, int i, int c, int j) {
        try {
            if (crossArray[i][j - c - 1] != '#') return false;
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        try {
            if (crossArray[i][j - c + word.length()] != '#') return false;
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        int countPlus = 0;
        int countMinus = 0;
        int letterCount = 0;

        for (int k = 0; k < word.length(); k++) {
            try {
                if (crossArray[i][j + k - c] != '#' && crossArray[i][j + k - c] != word.charAt(k)) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }

            Boolean[] result = verifyPosition(i, (j + k - c), word.charAt(k), true);

            if (crossArray[i][j + k - c] == word.charAt(k)) {
                letterCount++;
                if (!result[0]) {
                    countPlus++;
                } else {
                    countPlus = 0;
                }

                if (!result[1]) {
                    countMinus++;
                } else {
                    countMinus = 0;
                }
            } else if (crossArray[i][j + k - c] == '#') {
                if (!result[0]) {
                    return false;
                } else {
                    countPlus = 0;
                }

                if (!result[1]) {
                    return false;
                } else {
                    countMinus = 0;
                }
            } else {
                return false;
            }

            if (countPlus >= 2 || countMinus >= 2 || letterCount == word.length()) {
                return false;
            }
        }
        for (int k = 0; k < word.length(); k++) {
            crossArray[i][j + k - c] = word.charAt(k);
        }
        return true;
    }

    static Boolean tryHorizontal(String word) {
        for (int i = 0; i < crossArray.length; i++) {
            for (int j = 0; j < crossArray.length; j++) {
                for (int c = 0; c < word.length(); c++) {
                    if (crossArray[i][j] == word.charAt(c)) {
                        if (tryHorizontal2(word, i, c, j)) return true;
                    }
                }
            }
        }
        return false;
    }

    // Quick dirty test data to measure against
    // TODO: Obv load from txt or json later
    static String testData = "ANTHEMS\n" +
            "PORSCHE\n" +
            "SIM\n" +
            "PROJECT\n" +
            "THESIS\n" +
            "HINTSA\n" +
            "DTS\n" +
            "FRISIA\n" +
            "NURBURGRING\n" +
            "ABU\n" +
            "JEANDRAPEAU\n" +
            "THANKS\n" +
            "ENZO\n" +
            "LID\n" +
            "ERES\n" +
            "GERMAN\n" +
            "TWEETS\n" +
            "DERRIERE\n" +
            "OI\n" +
            "HAIR\n" +
            "ASSETTO\n" +
            "UNI\n" +
            "EU\n" +
            "AH\n" +
            "TENDONS\n" +
            "CRB\n" +
            "ACE\n" +
            "GAMER\n" +
            "NEON\n" +
            "OR\n" +
            "RED\n" +
            "TAX\n" +
            "EAR\n" +
            "NINTH\n" +
            "TOE\n" +
            "SIX\n" +
            "MEGA\n" +
            "ITSTHECAR\n" +
            "ROUEN\n" +
            "GR\n" +
            "SECA\n" +
            "AUDI\n" +
            "NSFW\n" +
            "AS\n" +
            "KOBE\n" +
            "OCON\n" +
            "GATE\n" +
            "MONZA\n" +
            "MPG\n" +
            "SAGITTARIUS\n" +
            "HOOP\n" +
            "AR\n" +
            "WASH\n" +
            "DEGRADED\n" +
            "GONDOLA\n" +
            "OIL\n" +
            "RAND\n" +
            "LAURA\n" +
            "QATAR\n" +
            "SPA\n" +
            "SKIRTS\n" +
            "MIXED\n" +
            "APIS\n" +
            "STROLL\n" +
            "BOT\n" +
            "ROSSI\n" +
            "NECK\n" +
            "AT\n" +
            "MRI\n" +
            "PIETRO\n" +
            "HUNGRIA\n" +
            "SIA\n" +
            "MRSATURDAY\n" +
            "THIRD\n" +
            "STAMPS\n" +
            "SINGAPORE\n" +
            "HARDTIRES\n" +
            "SON\n" +
            "DRIVEITOUT\n" +
            "FLARES\n" +
            "GASLY\n" +
            "JONES\n" +
            "RASCASSE\n" +
            "NINETYONE\n" +
            "GEARBOX\n" +
            "ERS\n" +
            "WINE\n" +
            "DRAG\n" +
            "EVREUX\n" +
            "TACO\n" +
            "INTERS\n" +
            "ENGINES\n" +
            "USA\n" +
            "HERTA\n" +
            "CATERING\n" +
            "MONACO\n" +
            "NFT\n" +
            "AM\n" +
            "REG\n" +
            "MILK\n" +
            "ICE\n" +
            "TRACKWALK\n" +
            "SENATE\n" +
            "INSTAGRAM\n" +
            "GRAZ\n" +
            "NAN\n" +
            "FERRARI\n" +
            "BOSE\n" +
            "SARGEANT\n" +
            "BLOG\n" +
            "GTD\n" +
            "MUDDIER\n" +
            "PADEL\n" +
            "GRO\n" +
            "SHIRTS\n" +
            "IAN\n" +
            "HONDA\n" +
            "POLES\n" +
            "SOUR\n" +
            "DRS\n" +
            "LAST\n" +
            "AIR\n" +
            "ALL";
}


