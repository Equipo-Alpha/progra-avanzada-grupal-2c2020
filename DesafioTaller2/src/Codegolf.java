
public class Codegolf {

	public static void main(String[] args) {
//		int[][] m = { { 1, 8, 2 }, { 0, 3, 4 }, { 7, 6, 5 } };
//		int[][] m = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };
		int[][] m = { { 3, 2, 0 }, { 1, 4, 5 }, { 6, 7, 8 } };

		System.out.println(validarPuzzle(m));
	}

	public static boolean validarPuzzle(int[][] m) {
		int l = m.length, k = 0, f = 0, h = l * l, i = 0, j;
		int[] r = new int[h];

		for (int v[] : m)
			for (int b : v)
				r[f++] = b;

		for (; i < h; i++)
			for (j = 0; j < i; j++) {
				if (r[i] == 0)
					f = j / l + 1;
				else if (r[j] > r[i])
					k++;
			}

		j = k % 2;

		return l % 2 == 0 ? f % 2 != 0 ? j != 0 : j == 0 : j == 0;
	}
}
