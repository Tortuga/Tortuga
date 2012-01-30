import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Plateau extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int l;
	private Byte[] testTortue;
	private Configuration conf;
	byte clTor1, clTor2, saute;
	Image image,imgplateau,hexa,trouge,tvert,tretourne,trougetr,tverttr,cercle;
	Polygon[] tPoly;
	Point[] tPoint;
	ArrayList<Byte> tcercle ;
	ArrayList<byte[]> transTortue;
	/**
	 * @param 
	 */
	public Plateau(){
			super();
			setBackground(Color.BLACK);
			tPoly = new Polygon[37];
			tPoint = new Point[37];
			tcercle = new ArrayList<Byte>();
			transTortue = new ArrayList<byte[]>();
			clTor1=-1;
			clTor2=-1;
			saute=-1;
			try {
				image = ImageIO.read(new File("images/Fondtortuga.gif"));
				imgplateau = ImageIO.read(new File("images/Plateau.gif"));
				hexa = ImageIO.read(new File("images/Hexagone.png"));
				trouge = ImageIO.read(new File("images/tortue.rouge.png"));
				tvert = ImageIO.read(new File("images/tortue.vert.png"));
				trougetr = ImageIO.read(new File("images/tortue.rouge.tr.png"));
				tverttr = ImageIO.read(new File("images/tortue.vert.tr.png"));
				cercle = ImageIO.read(new File("images/cercle.png"));
				tretourne = ImageIO.read(new File("images/tortue.retourner.png"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			addMouseListener(new MouseEV());
			conf=new Configuration();
			testTortue=conf.convertTabPlateauEn37();
			affPossibleMove();
			
	}
	
	private void affPossibleMove(){
		tcercle.clear();
		for (byte[] coup : conf.verifCoupObligatoire()) {
			tcercle.add((byte) Configuration.en37(coup[0]));
		}
		if(tcercle.isEmpty()){
			for (byte[] coup : conf.allPossibleMove()) {
				//System.out.println("coup:"+coup[0]);
				tcercle.add(Configuration.en37(coup[0]));
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		//long startTime = System.currentTimeMillis();
		l= (int) ((this.getHeight()-25)/(7.*Math.sqrt(3)));
		g.drawImage(image, 0, 0,this.getWidth(),this.getHeight(), null);
		creatTPoly();
		drawPlateau(g);
		for (Byte b : tcercle) {
			drawCercle(g, b);
			
		}
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("exec Time :"+ (endTime-startTime) +"ms");
    }
	
	private void drawPlateau(Graphics g) {
		double xO = (this.getWidth()/2. - (10.5*l)/2.);
		double yO = (this.getHeight()/2. - (7*l*Math.sqrt(3))/2.);
		testTortue=conf.convertTabPlateauEn37();
		int L = 2 * l + 2;
		int H = (int) (l * Math.sqrt(3) + 2);
        int k = 4,alignD =1;
        //int nb=0;
		for(int i = 0; i<7;i++){
			for(int j = 0; j<k;j++){
				if(i>3){alignD=3;}
				else{alignD=i;}
				int xA = (int)(3./2 * l * 3 + xO - j * ( 3./2. * l) + alignD * ( 3./2. * l));
				int yA = (int) (j * ( l * Math.sqrt(3)) /2. + i * ( l * Math.sqrt(3)) /2. + alignD/3 * (i - 3) * ( l * Math.sqrt(3)) /2. + yO);
				g.drawImage(hexa,xA ,yA ,L,H , null);
				//nb++;
			}
			if(i<3){k++;}
			else {k--;}
		}
		
		k=4;
		int num=0;
		for(int i=0;i<7;i++){
			if(i>3){alignD=i -3;}
			else{alignD=0;}
			for(int j=0;j<k;j++){
				int xA=(int)((j * 3./2. * l )+ l + alignD * 3./2. * l + xO);
				int yA=(int)(3 * (l * Math.sqrt(3)) / 2. + (l * Math.sqrt(3)) / 2. - j * (l * Math.sqrt(3)) / 2. + i * ( l * Math.sqrt(3)) - alignD  * ( l * Math.sqrt(3)) /2. + yO  );
				if(testTortue[num]==1){
					g.drawImage(trouge, xA - (L * 2/ 3)/2, (int) (yA -((L * 2/ 3)*0.905)/2), L * 2/ 3, (int) ((L * 2/ 3)*0.905)  , null);
				}
				else if(testTortue[num]==2){
					g.drawImage(tvert, xA - (L * 2/ 3)/2, (int) (yA -((L * 2/ 3)*0.905)/2), L * 2/ 3, (int) ((L * 2/ 3)*0.905)  , null);
				}
				else if(testTortue[num]==3){
					g.drawImage(tretourne, xA - (L * 2/ 3)/2, (int) (yA -((L * 2/ 3)*0.905)/2), L * 2/ 3, (int) ((L * 2/ 3)*0.905)  , null);
				}
				num++;
			}
			if(i<3){k++;}
			else {k--;}
		}
		for (byte[] trt : transTortue) {
			drawTortue(g, trt[0], testTortue[trt[1]]);
		}
	}
	
	private void creatTPoly(){
		double xO = (this.getWidth()/2. - (10.5*l)/2.);
		double yO = (this.getHeight()/2. - (7*l*Math.sqrt(3))/2.);
		int k = 4,alignD =1;
		int num=0;
		for(int i=0;i<7;i++){
			if(i>3){alignD=i -3;}
			else{alignD=0;}
			for(int j=0;j<k;j++){
				int xA=(int)((j * 3./2. * l )+ l + alignD * 3./2. * l + xO);
				int yA=(int)(3 * (l * Math.sqrt(3)) / 2. + (l * Math.sqrt(3)) / 2. - j * (l * Math.sqrt(3)) / 2. + i * ( l * Math.sqrt(3)) - alignD  * ( l * Math.sqrt(3)) /2. + yO  );
				if(testTortue[num]==1){
					tPoint[num]=new Point(xA, yA);
				}
				else if(testTortue[num]==2){
					tPoint[num]=new Point(xA, yA);
				}
				else if(testTortue[num]==3){
					tPoint[num]=new Point(xA, yA);
				}
				else{
					tPoint[num]=new Point(xA, yA);
				}
				num++;
			}
			if(i<3){k++;}
			else {k--;}
		}
		for(int i=0;i<37;i++){
			
			if(tPoint[i]!=null){
				int x=tPoint[i].x;
				int y=tPoint[i].y;
				Polygon poly =new Polygon(new int[]{-l+x,-l/2+x,l/2+x,l+x,l/2+x,x-l/2}, new int[]{y+0,y+(int) (l* Math.sqrt(3)/2),y+(int) (l* Math.sqrt(3)/2),y+0,y-(int) (l* Math.sqrt(3)/2),y-(int) (l* Math.sqrt(3)/2)}, 6);
				tPoly[i]=poly;
			}
			else{
				tPoly[i]=null;
			}
		}
	}
	
	private void drawTortue(Graphics g,int noTortue,int type){
		int L = 2 * l + 2;
		if(type==1)
			g.drawImage(trougetr, tPoint[noTortue].x- (L * 2/ 3)/2, (int) (tPoint[noTortue].y - ((L * 2/ 3)*0.905)/2), L * 2/ 3, (int) ((L * 2/ 3)*0.905), null);
		else if(type==2)
			g.drawImage(tverttr, tPoint[noTortue].x- (L * 2/ 3)/2, (int) (tPoint[noTortue].y - ((L * 2/ 3)*0.905)/2), L * 2/ 3, (int) ((L * 2/ 3)*0.905), null);
	}
	private void drawCercle(Graphics g,byte b){
		int L = 2 * l + 2;
		g.drawImage(cercle, tPoint[b].x- (L * 7/ 8)/2, (int) (tPoint[b].y - ((L * 7/ 8))/2), L * 7/ 8, (int) ((L * 7/ 8)), null);
	}
	
	private byte tortueClique(int x,int y){
		for(byte i = 0;i<tPoly.length;i++){
			if(tPoly[i]!=null&&tPoly[i].contains(x,y)){
				return i;
			}
		}
		return -1;
	}
	private boolean cliquePossible(byte numTor){
		if(tcercle.contains(numTor)){
			return true;
		}
		for (byte[] tortue : transTortue) {
			if(tortue[0]==numTor){
				return true;
			}
		}
		return false;
	}
	private void reset() {
		conf=new Configuration();
		repaint();
		affPossibleMove();
	}
	
	
	private class MouseEV extends MouseAdapter
    {
        public void mouseClicked(MouseEvent evt)
       {
        	//System.out.println(choixTortue());
        	//long startTime = System.currentTimeMillis();
        	byte numTor=tortueClique(evt.getX(), evt.getY());
        	if((numTor!=-1)&&cliquePossible(numTor)){
        		if(clTor1==-1 && testTortue[numTor]==conf.getPlayer()){
        			clTor1=numTor;
        			tcercle.clear();
        			tcercle.add(numTor);
        			for (byte[] poss : conf.verifCoupObligatoire()) {
						if(Configuration.en37(poss[0])==numTor){
							transTortue.add(new byte[]{Configuration.en37(poss[2]),Configuration.en37(poss[0]),poss[1]});
						}
					}
        			if(transTortue.isEmpty()){
	        			for (byte[] poss : conf.possibleMove(Configuration.en81(numTor), (byte)0)) {
	        				transTortue.add(new byte[]{Configuration.en37(poss[2]),Configuration.en37(poss[0]),poss[1]});
	        			}
        			}
        			repaint();
        		}
        		else if(clTor1!=-1){
        			if(clTor2==-1){
        				clTor2=numTor;
        				testTortue[clTor2]=testTortue[clTor1];
        				testTortue[clTor1]=0;
        				boolean a=false;
        				for (byte[] coup : transTortue) {
        					if(clTor1==coup[1]&&clTor2==coup[0]){
        						//System.out.println("ok " + coup[2]);	
        						if(coup[2]==3){
        							conf.coupJoue(Configuration.en81(clTor1), choixTortue(), Configuration.en81(clTor2));
        							tcercle.clear();
									tcercle.add((byte) clTor2);
					        		if(saute==-1)saute=3;
					        		clTor1=clTor2;
					        		clTor2=-1;
					        		a=true;
        						}
        						else if(coup[2]!=0){
									conf.coupJoue(Configuration.en81(clTor1), coup[2], Configuration.en81(clTor2));
									tcercle.clear();
									tcercle.add((byte) clTor2);
					        		saute=coup[2];
					        		clTor1=clTor2;
					        		clTor2=-1;
					        		a=true;
        						}
        						else{
        							conf.coupJoue(Configuration.en81(clTor1), (byte) 0, Configuration.en81(clTor2));
        							saute=0;
        							a=true;
        						}
        					}
        				}
        				//System.out.println(saute);
        				transTortue.clear();
						if(saute>0){
							for (byte[] poss : conf.possibleMove(Configuration.en81(clTor1), saute)) {
								if(poss[1]==3||poss[1]==saute||saute==3)
								transTortue.add(new byte[]{Configuration.en37(poss[2]),Configuration.en37(poss[0]),poss[1]});
							}
							if(transTortue.isEmpty()){
								saute=-1;
								clTor1=-1;
								clTor2=-1;
								conf.changePlayer();
								affPossibleMove();
								//System.out.println(saute);
							}
						}
						else if (!a) {
							clTor1=-1;
							clTor2=-1;
							affPossibleMove();
						}
						else{
							clTor1=-1;
							clTor2=-1;
							conf.changePlayer();
							affPossibleMove();
							saute=-1;
							//System.out.println(saute);
						}
					  //for (byte[] coup : conf.verifCoupObligatoire()) {
					//	tcercle.add((byte) conf.en37(coup[0]));
					//}
						repaint();

						if(conf.getPlayer()==2){
							conf=IA.meilleurConf(conf, (byte) 8);
							affPossibleMove();
						}
					//repaint();
						if(conf.whoWin()==1){
							JOptionPane.showMessageDialog(null, "Les tortues Rouges gagnent","Game Over", JOptionPane.INFORMATION_MESSAGE);
							reset();
						}
						else if(conf.whoWin()==2){
							JOptionPane.showMessageDialog(null, "Les tortues Vertes gagnent","Game Over", JOptionPane.INFORMATION_MESSAGE);
							reset();
						}
        			}
        		}
        	}
        	//else{System.out.println(numTor);}
        	//long endTime = System.currentTimeMillis();
        	//System.out.println("click Exec Time :"+ (endTime-startTime) +"ms");
           // pour afficher le graphique des coordonn�es
       }
        private byte choixTortue(){
        	String[] couleur = {"Rouge", "Verte"};
    		int rang = JOptionPane.showOptionDialog(null, 
    										"De quelle couleur voulez vous mettre la tortuue que vous retournez ?",
    										"Choix de Couleur",
    										JOptionPane.YES_NO_OPTION,
    										JOptionPane.QUESTION_MESSAGE,
    										null,
    										couleur,
    										couleur[0]);
    		if(rang==-1)rang=choixTortue()-1;
    		//JOptionPane.showMessageDialog(null, "Vous avez choisis " + couleur[rang], "Choix", JOptionPane.INFORMATION_MESSAGE);
    		return (byte) (rang+1);
        }
        
    }
}