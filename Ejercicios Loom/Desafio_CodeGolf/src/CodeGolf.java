public class CodeGolf {

	public static void main(String[] args) {
//		int[][] m = { { 1, 8, 2 }, { 0, 3, 4 }, { 7, 6, 5 } };
//		int[][] m = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };
//		int[][] m = { { 3, 2, 0 }, { 1, 4, 5 }, { 6, 7, 8 } };
		int[][] m = { { 1, 2, 3 }, { 6, 5, 4 }, { 8, 0, 7 } };
		System.out.println(validarPuzzle(m));
	}

	public static boolean validarPuzzle(int[][] m) {
		int l = m.length, k = 0, f = 0, i = 0, j, u;

		for (; i < l * l; i++)
			for (j = 0; j <= i; j++) {
				u = m[i / l][i % l];
				k += u == 0 ? 0 * (f = j / l + 1) : m[j / l][j % l] > u ? 1 : 0;
			}

		j = k % 2;

		return l % 2 == 0 ? f % 2 != 0 ? j != 0 : j == 0 : j == 0;
	}
}
