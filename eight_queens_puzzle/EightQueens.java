import java.util.Scanner;

/**
 * Placerar ut n drottningar på ett bräde som är n x n stort.
 * 
 * @author Teodor Östlund
 * @author Mikael Rosquist
 * 
 */

public class EightQueens {

	private static String board[][];
	private static int boardSize;
	private static String queen = "Q";
	private int queenCounter;
	private static Scanner scan = new Scanner(System.in);

	public EightQueens() {
		board = new String[boardSize][boardSize];
		newBoard();
		placeQueen(0, 0);
		printBoard();
	}

	/**
	 * Placerar ut drottningar på brädet så länge queenCounter och row är mindre
	 * än boardSize. Vi försöker alltid placera ut drottningarna på rad 0 i en
	 * ny kolumn (från vänster till höger) och om rutan inte är attack av en
	 * annan dam så placeras den nya drottningen ut på rutan och queenCounter
	 * ökar sedan med 1. Därefter anropas placeQueen igen rekusivt och vi
	 * försöker placera ut en ny drottning i nästa kolumn på rad 0. Om det dock
	 * inte skulle gå att placera ut en drottning på någon rad i kolumnen måste
	 * vi gå tillbaka en kolumn och ta bort drottningen i den kolumnen för att
	 * sedan flytta ner den en rad och försöka sedan igen.
	 * 
	 * @param row
	 *            raden den nya damen ska placeras i.
	 * @param col
	 *            kolumnen den nya damen ska placeras i.
	 */
	public void placeQueen(int row, int col) {

		for (; queenCounter < boardSize && row < boardSize; row++) {
			if (!checkAttack(row, col)) {
				board[row][col] = queen;
				queenCounter++;
				// printBoard();
				placeQueen(0, col + 1);
			} else if (row == boardSize - 1)
				removeQueen(row, col - 1);
		}
	}

	/**
	 * När en drottning har gått igenom en hel kolumn utan att kunna placeras
	 * någonstans anropas denna metod som tar bort drottningen i föregående
	 * kolumn. Vi börjar nerifrån och går uppåt, när vi hittar drottningen tar
	 * vi bort den. Skulle drottningen stå på en rad som är lika med boardSize-1
	 * anropas removeQueen rekursivt och går då igenom föregående kolumn. Står
	 * drottningen på en rad som är mindre än boardSize-1 anropas placeQueen med
	 * samma kolumn och en rad ner från där drottningen stod.
	 * 
	 * @param row
	 *            raden är alltid samma som antalet drottningar och därmed också
	 *            boardSize.
	 * @param col
	 *            den kolumn där en drottning ska tas bort/flyttas.
	 */
	public void removeQueen(int row, int col) {

		for (row = boardSize - 1; row >= 0; row--) {
			if (board[row][col] == queen) {
				board[row][col] = " ";
				queenCounter--;
				// printBoard();
				break;
			}
		}
		if (row == boardSize - 1)
			removeQueen(row, col - 1);
		else
			placeQueen(row + 1, col);
	}

	/**
	 * Metoden består av tre for-loopar som går i varsin riktning och kollar
	 * efter andra drottningar. Eftersom drottningarna placeras ut kolumnvis
	 * från vänster till höger behöver vi bara kolla efter andra drottningar
	 * till vänster. En loop kollar diagonalt neråt, en kollar samma rad och en
	 * kollar diagoanlt uppåt.
	 * 
	 * @param row
	 *            aktuell rad
	 * @param col
	 *            aktuell kolumn
	 * @return true om det finns en drottning som kan attackera
	 */
	public boolean checkAttack(int row, int col) {
		for (int i = 0; i < col; i++)
			if (board[row][i] == queen)
				return true;

		for (int j = row - 1, i = col - 1; j >= 0 && i >= 0; j--, i--)
			if (board[j][i] == queen)
				return true;

		for (int j = row + 1, i = col - 1; j < boardSize && i >= 0; j++, i--)
			if (board[j][i] == queen)
				return true;

		return false;
	}

	/**
	 * Här skapas ett helt nytt och tomt bräde. Metoden går igenom brädet rad
	 * för rad, kolumn för kolumn och tar bort alla eventuella drottningar. Sist
	 * sätter vi variabeln queenCounter till 0 eftersom brädet inte innehåller
	 * några drottningar.
	 * 
	 */
	public void newBoard() {
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				board[i][j] = " ";
		queenCounter = 0;
	}

	/**
	 * Går igenom hela brädet, rad för rad, kolumn för kolumn och skriver ut vad
	 * alla rutor innehåller.
	 * 
	 */
	public void printBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++)
				System.out.print("[" + board[i][j] + "]");
			System.out.println("");
		}
		System.out.println("\nAlla " + boardSize + " damer har placerats ut! ");
		System.out.println();
	}

	public static void main(String[] args) {
		do {
			boardSize = 0;
			while (boardSize <= 3) {
				if(boardSize != 0)
					System.out.println("Det går inte att placera ut " + boardSize + " damer på ett " + boardSize + "x" + boardSize + " stort bräde!");
				System.out.print("Ange brädets storlek: ");
				boardSize = scan.nextInt();
				System.out.println();
			}

			new EightQueens();

			scan.nextLine();
			System.out.print("Vill du börja om? (ja/nej) ");

		} while (scan.nextLine().equalsIgnoreCase("ja"));
	}
}