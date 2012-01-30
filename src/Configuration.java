import java.util.ArrayList;

public class Configuration{

	byte[] tPlateau;
	/* remplissage du tableau :
	 *-1 = case en dehors du plateau
	 * 0 = case vide du plateau
	 * 1 = tortue du joueur en bas (rouge)
	 * 2 = tortue du joueur en haut (vert)
	 * 3 = tortue retourne
	 */
	final private static byte[] t81convert37={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,-1,-1,-1,-1,4,5,6,7,8,-1,-1,-1,9,10,11,12,13,14,-1,-1,15,16,17,18,19,20,21,-1,-1,22,23,24,25,26,27,-1,-1,-1,28,29,30,31,32,-1,-1,-1,-1,33,34,35,36,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	final private static byte[] t37convert81={13,14,15,16,21,22,23,24,25,29,30,31,32,33,34,37,38,39,40,41,42,43,46,47,48,49,50,51,55,56,57,58,59,64,65,66,67};
	byte player;
	byte opponent;
	private int eval;
	byte winner; // 0 si partie en cour ; 1 si rouge(bas) gagne ; 2 si vert(haut) gagne
	
	/**
	 * Constructeur de classe
	 */
	
	public Configuration() {
		tPlateau = new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,-1,-1,-1,-1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,-1,-1,0,0,0,0,0,0,0,-1,-1,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,-1,-1,-1,-1,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		/* -1,-1,-1,-1,-1,-1,-1,-1,-1,
		 * -1,-1,-1,-1, 0, 0, 0, 0,-1,			 ______
		 * -1,-1,-1, 0, 0, 0, 0, 0,-1,			/	   |
		 * -1,-1, 0, 0, 0, 0, 0, 0,-1,		   /	V  |
		 * -1, 0, 0, 0, 0, 0, 0, 0,-1,		  |		   |
		 * -1, 0, 0, 0, 0, 0, 0,-1,-1,		  |	      /
		 * -1, 0, 0, 0, 0, 0,-1,-1,-1,		  |	R    /
		 * -1, 0, 0, 0, 0,-1,-1,-1,-1,		  |_____/
		 * -1,-1,-1,-1,-1,-1,-1,-1,-1.
		 */
		eval=0;
		player = 2;
		opponent = 1;
		winner=0; 
		placementDefautTortue();
	}
	
	public Configuration(byte[] tab,byte player, byte opponent,int eval){
		tPlateau=new byte[81];
		System.arraycopy(tab, 0, tPlateau, 0, tab.length);
		this.player = player;
		this.opponent = opponent;
		this.eval=eval;
	}
	
	public Configuration(byte[] tab37){
		this();
		 for (int i = 0; i < tab37.length; i++) {
			tPlateau[Configuration.en81(i)]=tab37[i];
		}
	}
	
	public Configuration clone() {
		return new Configuration(tPlateau, player, opponent, eval);
	}
	
	public int getEval() {
		return eval;
	}

	public void setEval(int i) {
		this.eval = i;
	}

	/**
	 * remplit le tableau pour le debut de partie
	 */
	
	private void placementDefautTortue(){
		// d�finition du plateau avec les tortues
		for (int i=0 ; i<4 ; i++){
			tPlateau[i+13]=2;
			tPlateau[16+9*i]=2;
		}tPlateau[24]=2;
		for (int i=0 ; i<4 ; i++){
			tPlateau[i+64]=1;
			tPlateau[37+9*i]=1;
		}tPlateau[56]=1;
	}
	
	/**
	 * Retourne un tableau de 37x37 byte repr�sentant le plateau de jeux<br>
	 * les tortues sont repr�sent� de la fa�on suivante:<br>
	 *-1 = case en dehors du plateau<br>
	 * 0 = case vide du plateau<br>
	 * 1 = tortue du joueur en bas<br>
	 * 2 = tortue du joueur en haut<br>
	 * 3 = tortue retourne<br>

	 * @return le plateau sus forme de tableau
	 */
	
	public Byte[] convertTabPlateauEn37(){ // m�thode à utiliser avant d'envoyer tPlateau37 à la partie graphique
		int ii = 0;
		Byte[] tPlateau37 = new Byte[37];
		for (int i=0; i<tPlateau.length ; i++){
			if (tPlateau[i] != -1){
				tPlateau37[ii]=tPlateau[i];
				ii ++;
			}
		}
		return tPlateau37;
	}
	
	/**
	 * convertit un indice du tableau de 81 cases en indice du tableau de 37 cases correspondant 
	 * @param numCase indice de dans le tableau de 81 cases
	 * @return 
	 */
	
	public static byte en37(byte numCase){ // ex : en37(21) retourne 4, en37(64) retourne 33
		if (numCase >= 0 && numCase < t81convert37.length)
			return t81convert37[numCase];
		else
			System.out.println("config.en37() : bug, parametre en dehors du tableau");
		return -1;
	}
	public  static byte en81(byte numCase){ // (réciproque de en37()) ex : en37(4) retourne 21, en37(33) retourne 64
		if (numCase >= 0 && numCase < t37convert81.length)
			return t37convert81[numCase];
		else
			System.out.println("config.en81() : bug, parametre en dehors du tableau");
		return -1;
	}
	
	public static byte en37(int numCase){ // ex : en37(21) retourne 4, en37(64) retourne 33
		if (numCase >= 0 && numCase < t81convert37.length)
			return t81convert37[numCase];
		else
			System.out.println("config.en37() : bug, parametre en dehors du tableau");
		return -1;
	}
	public  static byte en81(int numCase){ // (réciproque de en37()) ex : en37(4) retourne 21, en37(33) retourne 64
		if (numCase >= 0 && numCase < t37convert81.length)
			return t37convert81[numCase];
		else
			System.out.println("config.en81() : bug, parametre en dehors du tableau");
		return -1;
	}

	public void changePlayer(){
		if (player == 1 && opponent == 2){			player = 2;	opponent = 1; }
		else if (player == 2 && opponent == 1){	player = 1;	opponent = 2; }
		else System.out.println("erreur, 2 players ou 2 opponent");
	}
	
	public byte whoWin(){
		if 		(tPlateau[16] == 1) return 1;
		else if (tPlateau[64] == 2) return 2;
		else if ( isBlocked() )		return opponent;
		else return 0;
	}
	
	/**
	 * basée sur la méthode possibleMove()
	 * Teste si des déplacements ou sauts sont possible, si c'est le cas, la fonction
	 * s'arrête prématurément en renvoyant false. Sinon, si toutes les tortues sont testées
	 * et qu'aucun déplacement n'est possible, on renvoie true.
	 * Dans le cas où le joueur n'a plus de tortue, la méthode renvoie true
	 * 
	 * @return true si configuration blocké (plus de coups possible), false sinon
	 */
	
	public boolean isBlocked(){
		byte signe=0, tCaseAutour[]={-8,-9, 1};
		if (player == 1) signe = 1;
		else if (player == 2) signe = -1;
		for (byte numCase=0 ; numCase<tPlateau.length ; numCase++) {
			if (tPlateau[numCase] == player){ //pour chaque tortue du joueur
				byte typeCaseSuiv;
				for (byte dir : tCaseAutour){
					typeCaseSuiv = tPlateau[numCase+signe*dir];
					if (typeCaseSuiv == 0){
						return false; // DEPLACEMENT possible => non bloqué
					}else if ((typeCaseSuiv > 0 ) && (tPlateau[numCase+signe*2*dir] == 0)){ // SAUT possible
						return false; // SAUT possible => non bloqué
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * retourne un liste de tout les coups possibles 
	 * où chaque coup est représenté par un tableau de byte.<br>
	 * le premier élément du tableau est la case o� se trouve la tortue qui peux bouger<br>
	 * le deuxième element et le type de saut : <br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 0 - pas de saut<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 1|2 - saut par dessus une tortue de type 1|2<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 3 - saut par dessus une tortue retournée<br>
	 * le dernier élément du tableau est la case de destination
	 
	 * @return La liste des coups possibles 
	 */
	
	public ArrayList<byte[]> allPossibleMove(){
		
		ArrayList<byte[]> lMovePossible=new ArrayList<byte[]>();
		for (byte i=0 ; i<tPlateau.length ; i++) {
			if (tPlateau[i] == player){
				lMovePossible.addAll(possibleMove(i, (byte)0));
			}
		}
		return lMovePossible;
	}
	
	/**
	 * retourne un liste de tout les coups possibles pour une tortue donn�
	 * où chaque coup est représenté par un tableau de byte.
	 * le premier élément du tableau est la case de départ de la tortue qui peux bouger<br>
	 * le deuxième element et le type de saut : <br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 0 - pas de saut<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 1|2 - saut par dessus une tortue de type 1|2<br>
	 * 	&nbsp;&nbsp;&nbsp;&nbsp; 3 - saut par dessus une tortue retournée<br>
	 * le dernier élément du tableau est la case de destination

	 @param  numCase  Numéro de la case où se trouve la tortue dont on veux les mouvement possible 
	 
	 @param tortueSautee  Nombre de saut que la tortue a déjà effectué.
	 
	 @return La liste des coups possibles 
	 */
	
	public ArrayList<byte[]> possibleMove(byte numCase, byte tortueSautee){
		
		byte signe=0, tCaseAutour[]={-8,-9, 1};
		ArrayList<byte[]> lMovePossible = new ArrayList<byte[]>();
		if (player == 1) signe = 1;
		else if (player == 2) signe = -1;
		else System.err.println("Erreur, Configuration, methode possibleMove, pas de tortue sur tPlateau["+numCase+"]");
		byte typeCaseSuiv;
		for (byte dir : tCaseAutour){
			typeCaseSuiv = tPlateau[numCase+signe*dir];
			if (tortueSautee == 0){			// PREMIER DEPLACEMENT OU SAUT
				if (typeCaseSuiv == 0){					// DEPLACEMENT possible
					byte[] tab3={numCase, 0, (byte)(numCase+signe*dir)};
					lMovePossible.add(tab3);
				}else if ((typeCaseSuiv > 0 ) && (tPlateau[numCase+signe*2*dir] == 0)){ // SAUT possible
					byte[] tab3={numCase, typeCaseSuiv, (byte)(numCase+signe*2*dir)};
					lMovePossible.add(tab3);
				}
			}else if (tortueSautee > 0){	// APRES UN SAUT
				if ((typeCaseSuiv > 0 ) &&(tPlateau[numCase+signe*2*dir] == 0)) { // SAUT possible en théorie
					if ((typeCaseSuiv==3) || (typeCaseSuiv==tortueSautee) || tortueSautee==3) {		// SAUT possible selon règles du jeu
						byte[] tab3={numCase, typeCaseSuiv, (byte)(numCase+signe*2*dir)};
						lMovePossible.add(tab3);
					}
				}
			}
		}
		return lMovePossible;
	}
	
	public ArrayList<byte[]> verifCoupObligatoire(){
		ArrayList<byte []> lMoveObligatoire = new ArrayList<byte []>();
		ArrayList<byte[]> lMovePossible = new ArrayList<byte[]>();
		lMovePossible=allPossibleMove();
		for (byte i=0; i<lMovePossible.size() ; i++){
			if (lMovePossible.get(i)[1] == opponent) {
				lMoveObligatoire.add(lMovePossible.get(i));
			}
		}if (lMoveObligatoire.isEmpty()){
			if (lMovePossible.isEmpty()) // si le joueur ne peut pas jouer, sno adversaire gagne la partie
				winner = opponent;
			return lMovePossible;
		}else{ 
			return lMoveObligatoire;
		}
	}
	
	/**
	 * Déplace une tortue en changeant des valeurs du tableau tPlateau[]

	 * @param caseDebut numéro de la case d'ou la tortue1 saute
	 * @param typeSaute type de la tortue2 APRES que la tortue1 aie sautée par dessus
	 * @param caseFin 	numéro de la case ou la tortue1 attérit 
	 */
	public void coupJoue(byte caseDebut,byte typeSaute,byte caseFin){ // la tortue se déplace
		tPlateau[caseFin]=tPlateau[caseDebut];
		tPlateau[caseDebut]=0;
		if(typeSaute>0){
			byte caseSaute=(byte)((caseDebut+caseFin) / 2);
			if(tPlateau[caseSaute]==3){
				tPlateau[caseSaute]=(byte) typeSaute;
			}
			else if(typeSaute==opponent){
				tPlateau[caseSaute]=3;
			}
		}
		winner = whoWin();
	}
	
	public byte getPlayer(){
		return player;
	}
	

	public void print(){
		for (int i=0; i<tPlateau.length ; i++){
			if (i%9==0)System.out.println();
			System.out.print(tPlateau[i] + "\t");
		}System.out.println();
	}
	public void printRV(){
		String kr = "";
		for (int i=0; i<tPlateau.length ; i++){
			switch (tPlateau[i]) {
				case -1 :	kr = "";  break;
				case 0 :	kr = "O"; break;
				case 1 :	kr = "R"; break;
				case 2 :	kr = "V"; break;
				case 3 :	kr = "N"; break;
			}
			if (i%9==0) System.out.println();
			System.out.print(kr+"\t");
		}
	}
	public void printHexRV(){
		String[] rv =new String[]{".","R","V","N"};
		System.out.println("\t\t\t"+ rv[tPlateau[en81(3)]] + "\t\t\t");
		System.out.println("\t\t"+ rv[tPlateau[en81(2)]] + "\t\t" + rv[tPlateau[en81(8)]] + "\t\t");
		System.out.println("\t"+ rv[tPlateau[en81(1)]] + "\t\t" + rv[tPlateau[en81(7)]] + "\t\t" + rv[tPlateau[en81(14)]] + "\t\t");
		System.out.println(rv[tPlateau[en81(0)]] + "\t\t" + rv[tPlateau[en81(6)]] + "\t\t" + rv[tPlateau[en81(13)]] + "\t\t"+ rv[tPlateau[en81(21)]]);
		System.out.println("\t"+ rv[tPlateau[en81(5)]] + "\t\t" + rv[tPlateau[en81(12)]] + "\t\t" + rv[tPlateau[en81(20)]] + "\t");
		System.out.println(rv[tPlateau[en81(4)]] + "\t\t" + rv[tPlateau[en81(11)]] + "\t\t" + rv[tPlateau[en81(19)]] + "\t\t" + rv[tPlateau[en81(27)]]);
		System.out.println("\t"+ rv[tPlateau[en81(10)]] + "\t\t" + rv[tPlateau[en81(18)]] + "\t\t" + rv[tPlateau[en81(26)]] + "\t");
		System.out.println(rv[tPlateau[en81(9)]] + "\t\t" + rv[tPlateau[en81(17)]] + "\t\t" + rv[tPlateau[en81(25)]] + "\t\t" + rv[tPlateau[en81(32)]]);
		System.out.println("\t"+ rv[tPlateau[en81(16)]] + "\t\t" + rv[tPlateau[en81(24)]] + "\t\t" + rv[tPlateau[en81(31)]] + "\t");
		System.out.println(rv[tPlateau[en81(15)]] + "\t\t" + rv[tPlateau[en81(23)]] + "\t\t" + rv[tPlateau[en81(30)]] + "\t\t" + rv[tPlateau[en81(36)]]);
		System.out.println("\t"+ rv[tPlateau[en81(22)]] + "\t\t" + rv[tPlateau[en81(29)]] + "\t\t" + rv[tPlateau[en81(35)]] + "\t");
		System.out.println("\t\t"+ rv[tPlateau[en81(28)]] + "\t\t" + rv[tPlateau[en81(34)]] + "\t\t");
		System.out.println("\t\t\t"+ rv[tPlateau[en81(33)]] + " \t\t\t ");
	}
	public void print_t81convert37(){
		for (int i=0; i<t81convert37.length ; i++){
			if (i%9==0)System.out.println();
			System.out.print(t81convert37[i]+"\t");
		}System.out.println();
	}	
	public void print_t37convert81(){
		for (int i=0; i<t37convert81.length ; i++){
			if (i==4||i==9||i==15||i==22||i==28||i==33||i==37) System.out.println();
			System.out.print(t37convert81[i]+"\t");
		}System.out.println();
	}	
	public void printAllCoupPossible(){
		ArrayList<byte[]> lMovePossible=allPossibleMove();
		for (int i=0; i<lMovePossible.size() ; i++){  // <=> print mvt possible
			System.out.print("case init : " + lMovePossible.get(i)[0]);
			System.out.print("; type sautée : " + lMovePossible.get(i)[1]);
			System.out.println("; case finale : " + lMovePossible.get(i)[2]);
		}		
	}
	
	
	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.changePlayer();
		config.allPossibleMove();// calcul tous les coups possible du joueur 1 
		config.verifCoupObligatoire(); // regarde si un saut au dessus de l'adversaire est possible
		config.convertTabPlateauEn37();
		config.printAllCoupPossible();
		

		config.print();
		config.printRV();
		
		config.print_t81convert37();
		System.out.println();
		config.print_t37convert81();
		System.out.println();
		
		config.printHexRV();
		System.out.println("joueur "+config.player+" blocké : "+config.isBlocked());
		config.changePlayer();
		System.out.println("joueur "+config.player+" blocké : "+config.isBlocked());
		//mettre placementBlock() a la place de placementDefautTortue() dans le constructeur pour tester la fonction isBlocked()
	}
}


