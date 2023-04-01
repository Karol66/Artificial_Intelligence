import java.util.ArrayList;
import java.util.Scanner;

public class ProjektAi2 {

    static char[][] board = new char[3][3]; // plansza do gry
    static char human = 'X'; // gracz człowiek
    static char computer = 'O'; // gracz komputer

    //inicjalizacja planszy
    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // rysuje planszę
    public static void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-------------");
    }

    // sprwdza czy plansza jest zapełniona
    public static boolean isBoardFull() {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                }
            }
        }
        return isFull;
    }

    // sprawdza czy gra się skończyła
    public static boolean gameOver() {
        return (checkWin(human) || checkWin(computer) || isBoardFull());
    }

    //spr
    public static boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    // Ruch gracza człowieka
    public static void humanMove() {
        if (!isBoardFull() && !gameOver()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            int col = scanner.nextInt();
            if (board[row][col] != '-') {
                System.out.println("Invalid move! Try again.");
                humanMove();
            } else {
                board[row][col] = human;
            }
        }
    }

    public static int scoreValue() {
        int score = 0;

        // sprawdza rzędy
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == computer) {
                    score = score - 10;
                    break;
                } else if (board[i][0] == human) {
                    score = score + 10;
                    break;
                }
            }
        }

        // sprwdza kolumny
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == computer) {
                    score = score - 10;
                    break;
                } else if (board[0][i] == human) {
                    score = score + 10;
                    break;
                }
            }
        }

        // sprawdza przekontne
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == computer) {
                score = score - 10;
            } else if (board[0][0] == human) {
                score = score + 10;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == computer) {
                score = score - 10;
            } else if (board[0][2] == human) {
                score = score + 10;
            }
        }

        return score;
    }

    // ruch komputera
    static void bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int row = -1;
        int col = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                // sprawdza czy pole jest wolne
                if (board[i][j] == '-') {
                    board[i][j] = computer;
                    int score = minmax(false, board);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        if (!isBoardFull() && !gameOver()) {
            board[row][col] = computer;
        }
    }

    static int minmax(boolean isMaximizing, char[][] board) {
        int result = scoreValue();
        // zwraca wynik sytuacji na planszy
        if (result != 0) {
            return result;
        }
        if (isBoardFull()) {
            return 0;
        }

        // jeżeli komputer maksyamlizuje wynik
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    // sprawdza czy pole jest wolne
                    if (board[i][j] == '-') {
                        board[i][j] = computer;
                        int score = minmax(false, board);
                        bestScore = Math.max(score, bestScore);
                        board[i][j] = '-';
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    // sprawdza czy pole jest wolne
                    if (board[i][j] == '-') {
                        board[i][j] = human;
                        int score = minmax(true, board);
                        bestScore = Math.min(score, bestScore);
                        board[i][j] = '-';
                    }
                }
            }
            return bestScore;
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Kto ma zaczac? \n");
            System.out.println("1-gracz");
            System.out.println("2-komputer");
            System.out.println("3-zakoncz");
            System.out.print("->");

            Scanner scanner = new Scanner(System.in);

            int wybor = scanner.nextInt();

            switch (wybor) {
                case 1:
                    initializeBoard();
                    printBoard();

                    while (!gameOver()) {
                        humanMove();
                        printBoard();
                        bestMove();
                        printBoard();
                        if (gameOver()){
                            if (checkWin(human)) {
                                System.out.println("Przegrał X");
                            }
                            else if (checkWin(computer)){
                                System.out.println("Przegrał O");
                            }
                            else {
                                System.out.println("Remis");
                            }
                        }
                    }
                    break;
                case 2:
                    initializeBoard();
                    printBoard();

                    while (!gameOver()) {
                        bestMove();
                        printBoard();
                        humanMove();
                        printBoard();
                        if (gameOver()){
                            if (checkWin(human)) {
                                System.out.println("Przegrał X");
                            }
                            else if (checkWin(computer)){
                                System.out.println("Przegrał O");
                            }
                            else {
                                System.out.println("Remis");
                            }
                        }
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wybrano zly tryb sproboj jeszcze raz!\n");
                    break;
            }
        }
    }
}
