import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class AlgaritmoGenetico {


	public static void main(String args[]) {
		int iniciox = 0;
		int inicioy = 0;
		String[][] labirinto = lerArquivo("labirinto1_10.txt");
		int populacao = labirinto.length * 50;
		int tamanho = labirinto.length*8;
		for(int i = 0;i<labirinto.length;i++) {
			for (int j = 0; j < labirinto.length; j++) {
				if (labirinto[i][j].equals("E")) {
					iniciox = j;
					inicioy = i;
				}
			}
		}

		int[][] matrizA = new int[populacao][tamanho+1];
		int[][] matrizI = new int[populacao][tamanho+1];

		populaMatriz(matrizA);
		int[] sucess = new int[tamanho];
		/*int[] cam = new int[12];
		cam[0]=7;
		cam[1]=7;
		cam[2]=7;
		cam[3]=7;
		cam[4]=6;
		cam[5]=6;
		cam[6]=6;
		cam[7]=7;
		cam[8]=7;
		cam[9]=4;
		cam[10]=4;
		cam[11]=4;*/

		//calculaAptidao(cam, iniciox, inicioy, labirinto, sucess);
		for(int geracao = 0; geracao < 10000; geracao++) {
			System.out.println("Geracao numero: " + geracao);

			for(int i = 0; i < matrizA.length; i++) {
				calculaAptidao(matrizA[i], iniciox, inicioy, labirinto, sucess);
			}
			System.out.println(matrizA[0]);
			int elite = matrizA[0][tamanho];
			int linhaElite = 0;
			for(int i = 1; i < matrizA.length; i++) {
				if(matrizA[i][tamanho] > elite) {
					elite = matrizA[i][tamanho];
					linhaElite = i;
				}
			}
			for(int j=0;j<tamanho;j++) {
				matrizI[0][j] = matrizA[linhaElite][j];
			}
			
			imprimeMatriz(matrizA);
			System.out.println("Elitismo: " + linhaElite);
			System.out.println(" ");
			crossover(matrizI, matrizA);
			matrizA = matrizI;
		}
		for (int i =0;i<tamanho;i++){
			System.out.print(" "+sucess[i]+" ");
		}
	}

	public static String[][] lerArquivo(String arq){
		try {
			FileReader file = new FileReader(arq);
			BufferedReader lerArq = new BufferedReader(file);

			int tamanho = Integer.parseInt(lerArq.readLine());
			String[][] matriz = new String[tamanho][tamanho];
			for(int i = 0;i<tamanho;i++) {
				String[] linha = lerArq.readLine().split(" ");
				for(int j = 0; j<tamanho;j++) {
					matriz[i][j] = linha[j];
				}
			}

			file.close();
			return matriz;
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
		return null;
	}
	
	public static void populaMatriz(int[][] matriz) {
		Random r = new Random();
		
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[i].length-1; j++) {
				matriz[i][j] = r.nextInt(8);
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
	
	public static void calculaAptidao(int[] linha, int iniciox, int inicioy, String[][] labirinto, int[] sucess) {
		int posx = iniciox;
		int posy = inicioy;
		int[][] caminho = new int[labirinto.length][labirinto.length];
		int soma = 0;
		for (int i = 0;i<linha.length;i++){
			if (!labirinto[posx][posy].equals("S")) {
				switch (linha[i]) {
					case 0:
						if (posx - 1 < 0 || inicioy - 1 < 0) soma = soma - 10;
						else if (posx - 1 >= 0 && posy - 1 >= 0 && posx - 1 < labirinto.length && posy - 1 < labirinto.length) {
							if (labirinto[posx - 1][posy - 1].equals("0")) {
								if (caminho[posx - 1][posy - 1] > 0) soma = soma - caminho[posx][posy]*2;
								posx = posx - 1;
								posy = posy - 1;
								soma= soma +5;
								caminho[posx][posy]++;
							} else if (labirinto[posx - 1][posy - 1].equals("S")) {
								soma = soma + 10000;
								posx = posx - 1;
								posy = posy - 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
								sucess = linha;
							} else if (labirinto[posx - 1][posy - 1].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx - 1][posy - 1].equals("E")){
								soma--;
								posx = posx - 1;
								posy = posy - 1;
							}
						}
						break;
					case 1:
						if (posx - 1 < 0) soma = soma - 10;
						else if (posy >= 0 && posx - 1 >= 0 && posx < labirinto.length && posy - 1 < labirinto.length) {
							if (labirinto[posx-1][posy].equals("0")) {
								if (caminho[posx - 1][posy] > 0) soma = soma - caminho[posx-1][posy]*2;
								posx = posx - 1;
								posy = posy;
								soma= soma +5;
								caminho[posx][posy]++;
							} else if (labirinto[posx - 1][posy].equals("S")) {
								soma = soma + 10000;
								posx = posx - 1;
								posy = posy;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx - 1][posy].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx - 1][posy].equals("E")){
								soma--;
								posx = posx - 1;
								posy = posy;
							}
						}
						break;
					case 2:
						if (posx - 1 < 0 || posy + 1 < 0 || posx +1>labirinto.length) soma = soma - 10;
						else if (posx - 1 >= 0 && posy + 1 >= 0 && posx - 1 < labirinto.length && posy + 1 < labirinto.length) {
							if (labirinto[posx - 1][posy + 1].equals("0")) {
								if (caminho[posx - 1][posy + 1] > 0) soma = soma - caminho[posy-1][posx+1]*2;
								posx = posx - 1;
								posy = posy + 1;
								soma= soma +5;
								caminho[posy][posx]++;
							} else if (labirinto[posx - 1][posx + 1].equals("S")) {
								soma = soma + 10000;
								posx = posx - 1;
								posy = posy + 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx - 1][posy + 1].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx -1][posy + 1].equals("E")){
								soma--;
								posx = posx - 1;
								posy = posy + 1;
							}
						}
						break;
					case 3:
						if (posy - 1 < 0) soma = soma - 10;
						else if (posy - 1 >= 0 && posx>= 0 && posy - 1 < labirinto.length && posx< labirinto.length) {
							if (labirinto[posx][posy - 1].equals("0")) {
								if (caminho[posx][posy - 1] > 0) soma = soma - caminho[posx][posy - 1]*2;
								posx = posx;
								posy = posy - 1;
								soma= soma +5;
								caminho[posx][posy]++;
							} else if (labirinto[posx][posy - 1].equals("S")) {
								soma = soma + 10000;
								posx = posx;
								posy = posy - 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx - 1][posy - 1].equals("1")){
								soma=soma-3;;
							} else if (labirinto[posx][posy - 1].equals("E")){
								soma--;
								posx = posx;
								posy = posy - 1;
							}
						}
						break;
					case 4:
						if (posy + 1 <= 0 || posy +1>labirinto.length) soma = soma - 10;
						else if (posx>= 0 && posy +1>= 0 && posx  < labirinto.length && posy+ 1< labirinto.length) {
							if (labirinto[posx][posy+ 1].equals("0")) {
								if (caminho[posx][posy+1] > 0) soma = soma - caminho[posx][posy+1]*2;
								posx = posx;
								posy = posy + 1;
								soma= soma +5;
								caminho[posy][posx]++;
							} else if (labirinto[posx][posy + 1].equals("S")) {
								soma = soma + 10000;
								posx = posx;
								posy = posy + 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx][posy + 1].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx][posy + 1].equals("E")){
								soma--;
								posx = posx;
								posy = posy + 1;
							}
						}
						break;
					case 5:
						if (posx + 1 < 0 || posy - 1 > labirinto.length-1) soma = soma - 10;
						else if (posx + 1 >= 0 && posy - 1 >= 0 && posx + 1 < labirinto.length && posy - 1 < labirinto.length) {
							if (labirinto[posx + 1][posy - 1].equals("0")) {
								if (caminho[posx + 1][posy - 1] > 0) soma = soma - caminho[posx + 1][posy - 1]*2;
								posx = posx + 1;
								posy = posy - 1;
								soma= soma +5;
								caminho[posy][posx]++;
							} else if (labirinto[posx + 1][posy - 1].equals("S")) {
								soma = soma + 10000;
								posx = posx + 1;
								posy = posy - 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx + 1][posy - 1].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx + 1][posy - 1].equals("E")){
								soma--;
								posx = posx + 1;
								posy = posy - 1;
							}
						}
						break;
					case 6:
						if (posx + 1 < 0) soma = soma - 10;
						else if (posx+1>= 0 && posy >= 0 && posx +1 < labirinto.length && posy < labirinto.length) {
							if (labirinto[posx + 1][posy].equals("0")) {
								if (caminho[posx+1][posy] > 0) soma = soma - caminho[posx+1][posy]*2;
								posx = posx + 1;
								posy = posy;
								soma= soma +5;
								caminho[posx][posx]++;
							} else if (labirinto[posx + 1][posy].equals("S")) {
								soma = soma + 10000;
								posx = posx + 1;
								posy = posy;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx + 1][posy].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx + 1][posy].equals("E")){
								soma--;
								posx = posx;
								posy = posy + 1;
							}
						}
						break;
					case 7:
						if (posx + 1 < 0 || posy + 1 < 0) soma = soma - 10;
						else if (posx + 1 >= 0 && posy + 1 >= 0 && posx + 1 < labirinto.length && posy + 1 < labirinto.length) {
							if (labirinto[posx + 1][posy + 1].equals("0")) {
								if (caminho[posx + 1][posy + 1] > 0) soma = soma - caminho[posx+1][posy+1]*2;
								posx = posx + 1;
								posy = posy + 1;
								soma= soma +5;
								caminho[posx][posy]++;
							} else if (labirinto[posx + 1][posy + 1].equals("S")) {
								soma = soma + 10000;
								posx = posx + 1;
								posy = posy + 1;
								imprimeMatriz(caminho);
								linha[linha.length - 1] = soma;
							} else if (labirinto[posx + 1][posy + 1].equals("1")){
								soma=soma-3;
							} else if (labirinto[posx + 1][posy - 1].equals("E")){
								soma--;
								posx = posx + 1;
								posy = posy + 1;
							}
						}
						break;
				}
				linha[linha.length - 1] = soma;
			} else {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!CHEGOU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
		}
	}
	
	public static int torneio(int matrizA[][]) {
		Random r = new Random();
		int first = r.nextInt(matrizA.length);
		int secound = r.nextInt(matrizA.length);
		if(matrizA[first][matrizA[0].length-1]>matrizA[secound][matrizA[0].length-1]) {
			return first;
		}
		else {
			return secound;
		}
	}
	

	public static void crossover(int[][] matrizI, int[][]matrizA) {
		int tamanho = matrizA.length;
		int size = matrizA[0].length -1;
		int pai;
		int pai2;
		Random r = new Random();
		int corte =r.nextInt(matrizA[0].length -5)+2;
		for(int i = 1; i < tamanho-1; i = i+2) {
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
			matrizI[i][r.nextInt(matrizA[0].length -5)+2] = r.nextInt(8);
		}
	}
}

