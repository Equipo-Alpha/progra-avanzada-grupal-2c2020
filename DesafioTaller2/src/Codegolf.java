
public class Codegolf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[][] m = { { 1, 8, 2 }, { 0, 3, 4 }, { 7, 6, 5 } };
		int[][] m = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };

		System.out.println(validarPuzzle(m));
	}

	private static boolean validarPuzzle(int[][] m) {
		// TODO Auto-generated method stub
		// l = rango de matriz
		// r = array de elementos de matriz
		// k = inversiones
		// c = contador para avanzar en el array al añadir
		// h = largo del array r
		// i = posicion de fila
		// j = posicion de columna
		// p = posicion de la columna del elemento 0
		// e = elemento de la matriz

		int l = m.length;
		int k = 0, c = 0, f = 0, h = l * l, i, j, e;
		int[] r = new int[h];

		for (i = 0; i < l; i++) {
			for (j = 0; j < l; j++) {
				e = m[i][j];
				r[c++] = e;
				if (e == 0)
					f = e;
			}
		}

		for (i = 0; i < h; i++)
			for (j = i + 1; j < h; j++)
				k += (r[i] > r[j] && r[j] != 0) ? 1 : 0;

		return (l % 2 == 0) ? (f % 2 == 0) ? (k % 2 == 1) : (k % 2 == 0) : (k % 2 == 0);
	}
}
