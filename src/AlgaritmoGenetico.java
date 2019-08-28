import java.util.Random;
public class AlgaritmoGenetico {

	public static void main(String args[]) {
	
		int populacao = 5;
		int tamanho = 20;
		
		int[] inicial = {5, 10, 15, 3, 10, 5, 2, 16, 
						9, 7, 5, 10, 15, 3, 
						10, 5, 2, 16, 9, 7};
		int[][] matrizA = new int[populacao][tamanho+1];
		int[][] matrizI = new int[populacao][tamanho+1];
	
		populaMatriz(matrizA);
		for(int geracao = 0; geracao < 4; geracao++) {
			System.out.println("Geração número: " + geracao);

			for(int i = 0; i < matrizA.length; i++) {
				calculaAptidao(matrizA[i], inicial);
			}
			
			int elite = matrizA[0][tamanho];
			int linhaElite = 0;
			for(int i = 1; i < matrizA.length; i++) {
				if(matrizA[i][tamanho] < elite) {
					elite = matrizA[i][tamanho];
					linhaElite = i;
				}
			}
			for(int j=0;j<tamanho;j++) {
				matrizI[0][j] = matrizA[linhaElite][j];
			}
			
			imprimeMatriz(matrizA);
			System.out.println("Elitismo: " + linhaElite);
			crossover(matrizI, matrizA);
			matrizA = matrizI;
		}
	}
	
	public static void populaMatriz(int[][] matriz) {
		Random r = new Random();
		
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[i].length-1; j++) {
				matriz[i][j] = r.nextInt(2);
			}
		}
	}
	
	public static void imprimeMatriz(int[][] matriz) {
		
		for(int i=0; i<matriz.length; i++) {
			int j = 0;
			for(; j<matriz[i].length-1; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("\tApt: " + matriz[i][j]);
		}
	}
	
	public static void calculaAptidao(int[] linha, int[] carga) {
		int a1 = 0;
		int a2 = 0;
		
		for(int i = 0; i < carga.length; i++) {
			if(linha[i] == 0) {
				a1 += carga[i];
			} else {
				a2 += carga[i];
			}
		}
		
		linha[carga.length] = Math.abs(a2 - a1);
	}
	
	public static int torneio(int matrizA[][]) {
		Random r = new Random();
		int larissa = r.nextInt(matrizA.length);
		int gabriel = r.nextInt(matrizA.length);
		if(matrizA[larissa][20]<matrizA[gabriel][20]) {
			return larissa;
		}
		else {
			return gabriel;
		}
	}
	

	public static void crossover(int[][] matrizI, int[][]matrizA) {
		int tamanho = matrizA.length;
		int size = matrizA[0].length -1;
		int pai;
		int pai2;
		int corte = tamanho/2;
		for(int i = 1; i < tamanho; i = i+2) {
			do {
				pai =torneio(matrizA);
				pai2 = torneio(matrizA);
			}while(pai == pai2);
			for(int j = 0; j< corte; j++) {
				matrizI[i][j] = matrizA[pai][j];
			   matrizI[i+1][j] = matrizA[pai2][j];
			}
			for(int j = corte; j< size; j++) {
				matrizI[i][j] = matrizA[pai2][j];
				matrizI[i+1][j] = matrizA[pai][j];
			}
		}
	}
}

