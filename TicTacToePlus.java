import java.util.Scanner;

public class TicTacToePlus {
    static char[][] board = new char[3][3]; // Plansza do gry
    static char human = 'X'; // Gracz człowiek
    static char computer = 'O'; // Gracz komputer
    static int symbolsPlaced = 0; // Liczba postawionych symboli
    static int movesNumber = 0;  // Liczba przesunięć
    static int realMovesNumber = 0; // Lliczba przesunięć w prawdziwej grze

    // Inicjalizacja planszy
    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Rysuje planszę
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

    // Sprwdza czy plansza jest zapełniona
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

    // Sprawdza czy gra się skończyła
    public static boolean gameOver() {
        return (checkWin(human) || checkWin(computer) || isBoardFull());
    }

    // Sprawdza czy gracz wygrał
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

    // Ruchy gracza
    public static void humanMove() {
        if (!gameOver()) {
            Scanner scanner = new Scanner(System.in);
            if (symbolsPlaced < 6) {
                System.out.print("Podaj rząd (0-2): ");
                int row = scanner.nextInt();
                System.out.print("Podaj kolumnę (0-2): ");
                int col = scanner.nextInt();
                if (row < 0 || row > 2 || col < 0 || col > 2) {
                    System.out.println("Nieprawidłowe wartości! Wprowadź wartości od 0 do 2");
                    humanMove();
                } else if (board[row][col] != '-') {
                    System.out.println("Niedozwolony ruch! Sprubój ponownie ");
                    humanMove();
                } else {
                    board[row][col] = human;
                    symbolsPlaced++;
                }
            } else {
                System.out.print("Wprowadź rząd symbolu, który chcesz przenieść (0-2): ");
                int fromRow = scanner.nextInt();
                System.out.print("Wprowadź kolumnę symbolu, który chcesz przenieść (0-2): ");
                int fromCol = scanner.nextInt();
                if (fromRow < 0 || fromRow > 2 || fromCol < 0 || fromCol > 2) {
                    System.out.println("Nieprawidłowe wartości! Wprowadź wartości od 0 do 2");
                    humanMove();
                } else if (board[fromRow][fromCol] != human) {
                    System.out.println("Niedozwolony ruch! Możesz poruszać tylko swoje własne symbole");
                    humanMove();
                } else {
                    System.out.print("Wprowadź rząd do, którego chcesz przenieść swój symbol (0-2): ");
                    int toRow = scanner.nextInt();
                    System.out.print("Wprowadź kolumnę do, której chcesz przenieść swój symbol (0-2): ");
                    int toCol = scanner.nextInt();
                    if (toRow < 0 || toRow > 2 || toCol < 0 || toCol > 2) {
                        System.out.println("Nieprawidłowe wartości! Wprowadź wartości od 0 do 2");
                        humanMove();
                    } else if (board[toRow][toCol] != '-') {
                        System.out.println("Niedozwolony ruch! Komórka docelowa jest już zajęta");
                        humanMove();
                    } else if ((fromRow - toRow > 1) || (fromCol - toCol > 1)) {
                        System.out.println("Niedozwolony ruch! Możesz przesuwać swój symbol o jedno pole w pionie lub poziomie");
                        humanMove();
                    } else if ((fromRow != toRow) && (fromCol != toCol)) {
                        System.out.println("Niedozwolony ruch! Możesz przesuwać swój symbol tylko poziomo lub pionowo");
                        humanMove();
                    } else {
                        board[toRow][toCol] = human;
                        board[fromRow][fromCol] = '-';
                    }
                }
            }
        }
    }

    public static int scoreValue() {
        int score = 0;

        // Sprawdza rzędy i nalicza punkty
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == computer) {
                    score = score + 10;
                    break;
                } else if (board[i][0] == human) {
                    score = score - 10;
                    break;
                }
            }
        }

        // Sprwdza kolumny i nalicza punkty
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == computer) {
                    score = score + 10;
                    break;
                } else if (board[0][i] == human) {
                    score = score - 10;
                    break;
                }
            }
        }

        // Sprawdza przekontne i nalicza punkty
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == computer) {
                score = score + 10;
            } else if (board[0][0] == human) {
                score = score - 10;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == computer) {
                score = score + 10;
            } else if (board[0][2] == human) {
                score = score - 10;
            }
        }

        return score;
    }

    static void computerMove() {
        int bestScore = Integer.MIN_VALUE;
        int row = -1;
        int col = -1;
        int previousRow = -1;
        int previousCol = -1;

        // Sprawdza, czy na tablicy jest już 6 znaków
        if (symbolsPlaced == 6) {
            // Iteruje przez planszę i przenosi każdą postać na sąsiednie puste miejsce
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == computer) {
                        // Sprawdza, czy w sąsiednich komórkach nie ma wolnych miejsc i znajduje najlepszy ruch za pomocą algorytmu minmax w 2 fazie gry
                        if (i > 0 && board[i - 1][j] == '-') {
                            board[i - 1][j] = computer;
                            board[i][j] = '-';
                            int score = minmax(false, board);
                            board[i - 1][j] = '-';
                            board[i][j] = computer;
                            if (score > bestScore) {
                                bestScore = score;
                                row = i - 1;
                                col = j;
                                previousRow = i;
                                previousCol = j;
                            }
                        }
                        if (i < board.length - 1 && board[i + 1][j] == '-') {
                            board[i + 1][j] = computer;
                            board[i][j] = '-';
                            int score = minmax(false, board);
                            board[i + 1][j] = '-';
                            board[i][j] = computer;
                            if (score > bestScore) {
                                bestScore = score;
                                row = i + 1;
                                col = j;
                                previousRow = i;
                                previousCol = j;
                            }
                        }
                        if (j > 0 && board[i][j - 1] == '-') {
                            board[i][j - 1] = computer;
                            board[i][j] = '-';
                            int score = minmax(false, board);
                            board[i][j - 1] = '-';
                            board[i][j] = computer;
                            if (score > bestScore) {
                                bestScore = score;
                                row = i;
                                col = j - 1;
                                previousRow = i;
                                previousCol = j;
                            }
                        }
                        if (j < board[i].length - 1 && board[i][j + 1] == '-') {
                            board[i][j + 1] = computer;
                            board[i][j] = '-';
                            int score = minmax(false, board);
                            board[i][j + 1] = '-';
                            board[i][j] = computer;
                            if (score > bestScore) {
                                bestScore = score;
                                row = i;
                                col = j + 1;
                                previousRow = i;
                                previousCol = j;
                            }
                        }
                    }
                }
            }

            // Umieszcza symbol komputera w najlepszym miejscu w 2 fazie gry
            if (!isBoardFull() && !gameOver()) {
                board[row][col] = computer;
                board[previousRow][previousCol] = '-';
            }

        } else {
            // Znajduje najlepszy ruch za pomocą algorytmu minimax w 1 fazie gry
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    // Check if spot is empty
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

            // Umieszcza symbol komputera w najlepszym miejscu w 1 fazie gry
            if (!isBoardFull() && !gameOver()) {
                board[row][col] = computer;
                symbolsPlaced++;
            }
        }
    }

    static int minmax(boolean isMaximizing, char[][] board) {
        int result = scoreValue();
        // Zwraca wynik sytuacji na planszy
        if (result != 0) {
            return result;
        }
        // Liczba przsunięć do jakich analizowane jest drzewo (2 gracza(human) 2 komputeraz(computer))
        if (isBoardFull() || movesNumber >= 4) {
            return 0;
        }

        if (symbolsPlaced < 6) {
            // Algorytm minmax dla 1 fazy gry
            // Jeżeli komputer maksyamlizuje wynik
            if (isMaximizing) {
                int bestScore = Integer.MIN_VALUE;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        // Sprawdza czy pole jest wolne
                        if (board[i][j] == '-') {
                            board[i][j] = computer;
                            int score = minmax(!isMaximizing, board);
                            bestScore = Math.max(score, bestScore);
                            board[i][j] = '-';
                        }
                    }
                }
                return bestScore;
            } else {
                // Jeżeli jest tuta gracza
                int bestScore = Integer.MAX_VALUE;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        // Sprawdza czy pole jest wolne
                        if (board[i][j] == '-') {
                            board[i][j] = human;
                            int score = minmax(!isMaximizing, board);
                            bestScore = Math.min(score, bestScore);
                            board[i][j] = '-';
                        }
                    }
                }
                return bestScore;
            }
        } else {
            // Algorytm minmax dla 2 fazy gry
            // Jeżeli komputer maksyamlizuje wynik
            if (isMaximizing) {
                int bestScore = Integer.MIN_VALUE;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] == computer) {
                            if (i > 0 && board[i - 1][j] == '-') {
                                board[i - 1][j] = computer;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.max(score, bestScore);
                                board[i - 1][j] = '-';
                                board[i][j] = computer;
                                movesNumber--;
                            }
                            if (i < board.length - 1 && board[i + 1][j] == '-') {
                                board[i + 1][j] = computer;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.max(score, bestScore);
                                board[i + 1][j] = '-';
                                board[i][j] = computer;
                                movesNumber--;
                            }
                            if (j > 0 && board[i][j - 1] == '-') {
                                board[i][j - 1] = computer;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.max(score, bestScore);
                                board[i][j - 1] = '-';
                                board[i][j] = computer;
                                movesNumber--;
                            }
                            if (j < board[i].length - 1 && board[i][j + 1] == '-') {
                                board[i][j + 1] = computer;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.max(score, bestScore);
                                board[i][j + 1] = '-';
                                board[i][j] = computer;
                                movesNumber--;
                            }
                        }
                    }
                }
                return bestScore;
            } else {  // Jeżeli jest tura gracza
                int bestScore = Integer.MAX_VALUE;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] == human) {
                            if (i > 0 && board[i - 1][j] == '-') {
                                board[i - 1][j] = human;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.min(score, bestScore);
                                board[i - 1][j] = '-';
                                board[i][j] = human;
                                movesNumber--;
                            }
                            if (i < board.length - 1 && board[i + 1][j] == '-') {
                                board[i + 1][j] = human;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.min(score, bestScore);
                                board[i + 1][j] = '-';
                                board[i][j] = human;
                                movesNumber--;
                            }
                            if (j > 0 && board[i][j - 1] == '-') {
                                board[i][j - 1] = human;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.min(score, bestScore);
                                board[i][j - 1] = '-';
                                board[i][j] = human;
                                movesNumber--;
                            }
                            if (j < board[i].length - 1 && board[i][j + 1] == '-') {
                                board[i][j + 1] = human;
                                board[i][j] = '-';
                                movesNumber++;
                                int score = minmax(!isMaximizing, board);
                                bestScore = Math.min(score, bestScore);
                                board[i][j + 1] = '-';
                                board[i][j] = human;
                                movesNumber--;
                            }
                        }
                    }
                }
                return bestScore;
            }
        }
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("Zasady: ");
            System.out.println("Gra ma 2 fazy:");
            System.out.println("w pierwszej fazie gracze wykonują swoje ruchy tak długo aż każdy nie postawi na planszy 3 swoich symboli ");
            System.out.println("natomiast w drugiej fazie kazy z graczy ma 2 przsunięcia którymi może przesunąć swoje symbole o jedno pole w poziomei lub pionie \n");

            System.out.println("Kto ma zaczać? \n");
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
                    realMovesNumber = 0;
                    symbolsPlaced = 0;

                    while (!gameOver() && realMovesNumber < 5) {
                        humanMove();
                        printBoard();
                        computerMove();
                        printBoard();
                        if (gameOver()) {
                            if (checkWin(human)) {
                                System.out.println("Wygrał X");
                            } else if (checkWin(computer)) {
                                System.out.println("Wygrał O");
                            }
                        }
                        realMovesNumber++;
                    }
                    if (realMovesNumber >= 5) {
                        System.out.println("Remis");
                    }
                    break;
                case 2:
                    initializeBoard();
                    printBoard();
                    realMovesNumber = 0;
                    symbolsPlaced = 0;

                    while (!gameOver() && realMovesNumber < 5) {
                        computerMove();
                        printBoard();
                        humanMove();
                        printBoard();
                        if (gameOver() || realMovesNumber >= 5) {
                            if (checkWin(human)) {
                                System.out.println("Wygrał X");
                            } else if (checkWin(computer)) {
                                System.out.println("Wygrał O");
                            }
                        }
                        realMovesNumber++;
                    }
                    if (realMovesNumber >= 5) {
                        System.out.println("Remis");
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wybrano zły tryb sprobój jeszcze raz!\n");
                    break;
            }
        }
    }
}