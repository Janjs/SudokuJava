package domini;

import java.util.LinkedList;

public class Coordenada {
	int x;
	int y;
	public Coordenada(int x, int y) throws Exception{
		if(x<0||x>8) throw new Exception("ERROR: fila invàlids");
		if (y<0||y>8)throw new Exception("ERROR: columna invàlids");
		this.x = x;
		this.y = y;
	}
	
	public LinkedList<Coordenada> mateixesFiles() throws Exception{
		LinkedList<Coordenada> mateixesFil = new LinkedList<Coordenada>();
		for(int num=0;num<9;num++){
			if(num!=this.y){
				mateixesFil.add(new Coordenada(x,num));
			}
		}
		return mateixesFil;
	}
	public LinkedList<Coordenada> mateixesColumnes() throws Exception{
		LinkedList<Coordenada> mateixesCol = new LinkedList<Coordenada>();
		for(int num=0;num<9;num++){
			if(num!=this.x){
				mateixesCol.add(new Coordenada(num,y));
			}
		}
		return mateixesCol;
	}
	public LinkedList<Coordenada> mateixesArea() throws Exception{
		LinkedList<Coordenada> mateixesArea = new LinkedList<Coordenada>();
		int i=(x/3)*3;
		int j=(y/3)*3;
		
		int i3 = i+3;
		int j3 = j+3;
		while(i<i3){
			while(j<j3){
				if(!(i==x&&j==y))mateixesArea.add(new Coordenada(i,j));  
				j++;
			}
			j=(y/3)*3;
			i++;
		}
		return mateixesArea;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
}
