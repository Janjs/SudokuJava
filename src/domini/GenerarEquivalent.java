package domini;

import java.util.LinkedList;
import java.util.Random;

public class GenerarEquivalent {
	private Random rnd = new Random();
	public GenerarEquivalent(Casella[][] graella){
		try {
			for(int x=0;x<9;x++){
				for(int y=0;y<9;y++){
					if(graella[x][y].getModificable()){
						graella[x][y].buidar();
					}
				}
			}	
			int numCanvis = rnd.nextInt(6)+1;
			System.out.println("Número de canvis: "+numCanvis);
			LinkedList<Integer> canvisNoDisponibles = new LinkedList<Integer>();
			for(int i=0;i<numCanvis;i++){
				int quinCanvi;
				do{
					quinCanvi = rnd.nextInt(6)+1;
				}while(canvisNoDisponibles.contains(quinCanvi));
				switch(quinCanvi){
					case 1: 
						intercanviFilesMateixaRegio(graella);
						canvisNoDisponibles.add(1); break;
					case 2: 
						intercanviColumnesMateixaRegio(graella);
						canvisNoDisponibles.add(2); break;
					case 3: 
						transposarGraella(graella);
						canvisNoDisponibles.add(3); break;
					case 4: 
						girarGraella(graella);
						canvisNoDisponibles.add(4); break;
					case 5: 
						intercanviRegionsHoritzontalment(graella);
						canvisNoDisponibles.add(5); break;
					case 6: 
						intercanviRegionsVerticalment(graella);
						canvisNoDisponibles.add(6); break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void intercanviFilesMateixaRegio(Casella[][] graella) throws Exception{
		int [][] files = intercanvisPossibles();
		int xRandom = rnd.nextInt(8); //parella de files que s'intercanviaran
		int x1 = files[xRandom][0];
		int x2 = files[xRandom][1];
		intercanviDosFiles(graella, x1, x2);
		System.out.println("Canvi fet: intercanvi de dues files de la mateixa regió (fila "+(x1+1)+" i "+(x2+1)+")");
	}
	public void intercanviColumnesMateixaRegio(Casella[][] graella) throws Exception{
		int [][] columnes = intercanvisPossibles();
		int xRandom = rnd.nextInt(8); //parella de files que s'intercanviaran
		int y1 = columnes[xRandom][0];
		int y2 = columnes[xRandom][1];
		intercanviDosColumnes(graella, y1, y2);
		System.out.println("Canvi fet: intercanvi de dues columnes de la mateixa regió (fila "+(y1+1)+" i "+(y2+1)+")");
	}
	public void transposarGraella(Casella[][] graella) throws Exception{
		Casella [][] aux = new Casella[9][9];
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				aux[x][y] = graella[y][x];
			}
		}
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				graella[x][y] = aux[x][y];
			}
		}
		System.out.println("Canvi fet: Graella transposada");
	}
	public void girarGraella(Casella[][] graella) throws Exception{
		int numRandom = rnd.nextInt(3)+1;
		System.out.println("Canvi fet: Graella girada "+numRandom*90+"º");
		while(numRandom>0){
			girarGraella90graus(graella);
			numRandom--;
		}
	}
	public void girarGraella90graus(Casella[][] graella) throws Exception{
		Casella [][] aux = new Casella[9][9];
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				aux[x][y] = graella[y][x];
			}
		}
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				graella[x][y] = aux[x][8-y];
			}
		}
	}
	public void intercanviRegionsHoritzontalment(Casella[][] graella) throws Exception{
		int numRandom = rnd.nextInt(3)+1;
		if(numRandom==1){
			//r1, r2, r3 to r4,r5,r6
			intercanviDosFiles(graella, 0,3);
			intercanviDosFiles(graella, 1,4);
			intercanviDosFiles(graella, 2,5);
			System.out.println("Canvi fet: intercanvi regions horitzontalment (Entre r1,r2,r3 i r4,r5,r6");
		}
		else if(numRandom==2){
			//r1,r2,r3 to r7,r8,r9
			intercanviDosFiles(graella, 0,6);
			intercanviDosFiles(graella, 1,7);
			intercanviDosFiles(graella, 2,8);
			System.out.println("Canvi fet: intercanvi regions horitzontalment (Entre r1,r2,r3 i r7,r8,r9");
		}
		else if(numRandom==3){
			//r1,r2,r3 to r4,r5,r6
			intercanviDosFiles(graella, 3,6);
			intercanviDosFiles(graella, 4,7);
			intercanviDosFiles(graella, 5,8);
			System.out.println("Canvi fet: intercanvi regions horitzontalment (Entre r3,r4,r5 i r4,r5,r6");
		}
	}
	public void intercanviRegionsVerticalment(Casella[][] graella) throws Exception{
		int numRandom = rnd.nextInt(3)+1;
		if(numRandom==1){
			//r1, r4, r7 to r2,r5,r8
			intercanviDosColumnes(graella, 0,3);
			intercanviDosColumnes(graella, 1,4);
			intercanviDosColumnes(graella, 2,5);
			System.out.println("Canvi fet: intercanvi regions verticalment (Entre r1,r4,r7 i r2,r5,r8");
		}
		else if(numRandom==2){
			//r1,r4,r7 to r3,r6,r9
			intercanviDosColumnes(graella, 0,6);
			intercanviDosColumnes(graella, 1,7);
			intercanviDosColumnes(graella, 2,8);
			System.out.println("Canvi fet: intercanvi regions verticalment (Entre r1,r4,r7 i r3,r6,r9");
		}
		else if(numRandom==3){
			//r2,r5,r8 to r3,r6,r9
			intercanviDosColumnes(graella, 3,6);
			intercanviDosColumnes(graella, 4,7);
			intercanviDosColumnes(graella, 5,8);
			System.out.println("Canvi fet: intercanvi regions verticalment (Entre r2,r5,r8 i r3,r6,r9");
		}
	}
	
	private void intercanviDosFiles(Casella[][] graella, int x1, int x2) throws Exception{
		Casella aux;
		for(int y=0;y<9;y++){
			aux = graella[x1][y];
			graella[x1][y] = graella[x2][y];
			graella[x2][y] = aux;
		}
	}
	private void intercanviDosColumnes(Casella[][] graella, int y1, int y2) throws Exception{
		Casella aux;
		for(int x=0;x<9;x++){
			aux = graella[x][y1];
			graella[x][y1] = graella[x][y2];
			graella[x][y2] = aux;
		}
	}
	private int[][] intercanvisPossibles(){
		int [][] possibles = new int[9][2];
		possibles[0][0] = 0;
		possibles[0][1] = 1;
		possibles[1][0] = 0;
		possibles[1][1] = 2;
		possibles[2][0] = 1;
		possibles[2][1] = 2;
		possibles[3][0] = 3;
		possibles[3][1] = 4;
		possibles[4][0] = 3;
		possibles[4][1] = 5;
		possibles[5][0] = 4;
		possibles[5][1] = 5;
		possibles[6][0] = 6;
		possibles[6][1] = 7;
		possibles[7][0] = 6;
		possibles[7][1] = 8;
		possibles[8][0] = 7;
		possibles[8][1] = 8;
		return possibles;
	}
}
