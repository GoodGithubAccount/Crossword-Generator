import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChessRandom {

    public static void main(String[] args) {
        int totalPlayerPieces = 16;
        char pieces[] = new char[16];
        pieces[0] = 'K';
        int piecesToDraw = totalPlayerPieces - 1;

        chessWhatever totalQRBKP[] = {
                new chessWhatever('Q', 100),
                new chessWhatever('R', 200),
                new chessWhatever('B', 200),
                new chessWhatever('N', 200),
                new chessWhatever('P', 800)
        };
        int totalPieces = 0;

        for (chessWhatever element: totalQRBKP) {
            totalPieces += element.amount;
        }

        int count = 0;
        char piecesToDrawFrom[] = new char[totalPieces];
        for (chessWhatever element: totalQRBKP) {
            for(int i = count; i < element.amount + count; i++){
                piecesToDrawFrom[i] = element.pieceType;
            }
            count += element.amount;
        }

        // Randomize drawing pile
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < piecesToDrawFrom.length; i++) {
            char tempChar = piecesToDrawFrom[i];
            int indexToSwap = random.nextInt(piecesToDrawFrom.length);

            piecesToDrawFrom[i] = piecesToDrawFrom[indexToSwap];
            piecesToDrawFrom[indexToSwap] = tempChar;
        }

        // Draw x pieces
        for(int i = 0; i < piecesToDraw; i++){
            // Intentionally doesn't delete drawn pieces
            pieces[i + 1] = piecesToDrawFrom[random.nextInt(piecesToDraw)];
        }

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < (totalPlayerPieces / 2); j++){
                System.out.print(" " + pieces[(i * (totalPlayerPieces / 2)) + j] + " ");
            }
            System.out.println();
        }
    }
}
