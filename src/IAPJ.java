import java.util.ArrayList;

public class IAPJ{

    protected byte depthMax = 6;
    protected long nbAppelListeFils = 0;//a suppr
    protected long nbConfigFils = 0;//a suppr
    public IAPJ(){
    	super();
    }
    
    /* On part du principe que l'IA jouera avec les tortues vertes (en haut donc) */
    public void print(Byte[] jeu){
    	for (int i=0; i < 37; i++){
			if (i==4 || i==9 || i==15 || i==22 || i==28 || i==33)
				System.out.println();
			System.out.print(jeu[i]);
		}
   		System.out.println();
   	}
   	
    
    
    
 
    //pas besoin de rajouter un paramèter joueur, c'est déjà un attribut de l'objet configurtion : conf.player
    
   	/**
   	 * Calcul la liste des configurations possibles à partir d'une configuration
   	 * Pour cela on calcule la liste de coups possibles (triplet) depuis la configuration
   	 * puis pour chaque triplet, on crée une configuration.
   	 * Si le coup joué est un déplacement direct : clone puis coupJoue puis listeConf.add...
   	 * Si le coup joué est un saut : appel de sautRecusrif qui va calculer retourner toutes
   	 * les autres configurations suivant les saut.
   	 * 
   	 * @param conf configuration depuis laquelle on calcule toutes les nouvelles configurations
   	 * 
   	 * @return la liste des nouvelles configurations
   	 */
   	
	public static ArrayList<Configuration> listeFils(Configuration conf){
		byte caseDebut;
		byte typeSauteFirst;
		byte caseFin;
		ArrayList<Configuration> listeConf = new ArrayList<Configuration>();
		for (byte[] triplet : conf.verifCoupObligatoire()) { // pour chaque coup de la liste des coups possibles
			caseDebut = triplet[0];
			typeSauteFirst = triplet[1];
			caseFin   = triplet[2];
			if (typeSauteFirst == 0) { // si coup = déplacement direct
				Configuration c = conf.clone();
				c.coupJoue(caseDebut, (byte)0, caseFin);
				listeConf.add(c); // AJOUT de la nouvelle config
			}else if (typeSauteFirst > 0) { // si coup = saut 
				listeConf.addAll(sautRecursif(conf, triplet, typeSauteFirst, false));
			}
		}		
		for (Configuration c : listeConf){ // changement de joueur pour toutes les config fils crées
			c.changePlayer();
		}		return listeConf;
	}

	/**
	 * Fonction récursive qui va calculer les configurations possible à partir d'un saut par dessus une tortue : 
	 * quand une tortue saute par dessus une autre, on regarde si elle peut à nouveau sauter sur d'autre tortue
	 * d'où l'appel récursif. Ensuite, le contenu de la fonction ressemble (un peu) à la fonction listeFils.
	 * La fonction a un autre appel récursif lorsqu'une tortue retournée est sauté, il faut créer 2 config, une
	 * en retournant la tortue en la couleur du player (appel récursif), une en la retournant de la couleur de
	 * l'opponent (suite normale de la fonction).
	 * 
	 * @param conf 		Configuration pour laquelle on calcule les nouvelles config
	 * 
	 * @param triplet 	{caseDebut, typeSaute, caseFin} coup effectué après lequel on calcule les nouvelles config
	 * 
	 * @param typeSauteFirst = 1 si la premiere tortue sauté non retourné était une rouge (1) dans ce tour
	 * 						 = 2 si la premiere tortue sauté non retourné était une verte (2) dans ce tour 
	 * 						 = 3 si seulement des tortues retournées (3) ont été sautées depuis le début du tour
	 * 
	 * @param noirEnPlayer booléan qui déterminera en couleur couleur se change une tortue retournée 2fois
	 * 						true  => la tortue retournée se change en player   (=> nouvel appel récursif)
	 *						false => la tortue retournée se change en opponent 
	 *
	 * @return la liste de toutes les nouvelles configurations
	 * 
	 */
	
	private static ArrayList<Configuration> sautRecursif(Configuration conf, byte[] triplet, byte typeSauteFirst, boolean noirEnPlayer){
		
		ArrayList<Configuration> listeConf = new ArrayList<Configuration>();
		
		byte caseDebut = triplet[0];
		byte typeSaute = triplet[1];
		byte caseFin   = triplet[2];
		byte newTurttle=0;
		
		if (typeSaute == conf.player)
			newTurttle = conf.player;// player saute opponent2 : pas de changement
		else if (typeSaute == conf.opponent)
			newTurttle = typeSaute; 		//  player saute opponent2 : rouge2 se retourne en noir
		else if (typeSaute == 3) {	//  player1 saute noir2 : 2 cas, _noir2 devient player, on fait un appel récursif pour traiter ce cas.
									//								 _noir2 devient opponent, on continue la fonction normalement
	 		if (noirEnPlayer) {
	 			newTurttle = conf.player; 
			}else { 
				listeConf.addAll(sautRecursif(conf, triplet, typeSauteFirst, true)); //appel récursif 
				newTurttle = conf.opponent;
			}
		}else System.out.println("ERREUR : dans la fct recursive de IA, typeSaute != 1,2 ou 3");
		
		Configuration c = conf.clone();
		c.coupJoue(caseDebut, newTurttle, caseFin);
		
		// typeSauteFirst doit contenir le type de la premiere tortue de couleur rouge ou verte sauté dans un tour de jeu
		if (typeSauteFirst == 3){
			typeSauteFirst = typeSaute;
		}
		ArrayList<byte[]> lMovePossible = c.possibleMove(caseFin, typeSauteFirst);
		if (lMovePossible.isEmpty()){ // si pas de saut possible après le premier coup
			listeConf.add(c); // AJOUT de la nouvelle config
		}else {
			for (byte[] tripletFils : lMovePossible){	
				listeConf.addAll(sautRecursif(c, tripletFils, typeSauteFirst, false)); //appel récursif
			}
		}
		return listeConf;
	}

	public void testRec(Configuration conf,byte depth){
		depth++;
		nbAppelListeFils++;
		
		if (depth == 2)
			System.out.println("nbappelListeFils : "+nbAppelListeFils);
		
		ArrayList<Configuration> listeConfig = listeFils(conf);
		if (depth<depthMax){
			for (Configuration c : listeConfig) {
				testRec(c, depth);
			}
		}
		else if (depth==depthMax){
			for (int i=0; i<listeConfig.size() ; i++) {
				nbConfigFils++;
			}
		}
	}

	
	
	//pas besoin de rajouter un paramèter joueur, c'est déjà un attribut de l'objet configurtion : conf.player
	
	public static int position(Byte[] jeu, byte player){
	
	   	int val=0;
	   	byte opponent=0;
	   	Byte empty = 0;
	   	int transpose=0;
	   	if (player == 1){
	   		opponent = 2;
	   		transpose=36;
	   	}
	   	else if (player == 2){
	   		opponent = 1;
	   	}
	   	else System.err.println("erreur IA");
	   	
	   	
	/* POSITION IA */   
	   	
		int [] tabIA = {50, 15, 50, 1000,
						40, 25, 25, 50, 50,
						40, 25, 25, 50, 25, 15,
						60, 30, 30, 50, 25, 25, 50,
						30, 50, 50, 30, 25, 40,
						75, 75, 50, 30, 40,
						1000000000, 75, 30, 60 
		};
				
		
		for (int i=0; i<37; i++){
			if (jeu[(37+transpose-i)%37] == player){
				val += tabIA[(37+transpose-i)%37];
			}
		}  
	
		if ((jeu[(37+transpose-22)%37] == player) && (jeu[(37+transpose-28)%37] != opponent))	val += 120;
		if ((jeu[(37+transpose-35)%37] == player) && (jeu[(37+transpose-34)%37] != opponent)) 	val += 120;
		
		if (jeu[(37+transpose-29)%37] == player){
			if ((jeu[(37+transpose-28)%37] != opponent) || (jeu[(37+transpose-34)%37] != opponent))
				val += 50;
		}
		
		if ((jeu[(37+transpose-23)%37] == player) && (jeu[(37+transpose-22)%37] != opponent))		val += 20;
		if ((jeu[(37+transpose-30)%37] == player) && (jeu[(37+transpose-35)%37] != opponent))		val += 20;
	
		    	   	
		if (((jeu[(37+transpose-24)%37] == player) && (jeu[(37+transpose-18)%37] == player)) || ((jeu[(37+transpose-18)%37] == player) && (jeu[(37+transpose-12)%37] == player)))
			val += 30;
			
			   
	   	if ((jeu[(37+transpose-28)%37] == player || jeu[(37+transpose-29)%37] == player || jeu[(37+transpose-34)%37] == player) && jeu[(37+transpose-33)%37] == 0) //sur le point de gagner
			val += 1000;
		
	   	if(jeu[(37+transpose-3)%37] == player && jeu[(37+transpose-2)%37] == player && jeu[(37+transpose-7)%37] == player && jeu[(37+transpose-8)%37] == player){
	   		val +=1000;		
	   	}
	   	
	   	
	   	
	   	
	   	if (jeu[(37+transpose-6)%37] == opponent) {
			if (jeu[(37+transpose-1)%37] == player || jeu[(37+transpose-2)%37] == player || jeu[(37+transpose-7)%37] == player)
				val -=50;		
				else	val -= 75;
					
		}
		if (jeu[(37+transpose-13)%37] == opponent) {
			if (jeu[(37+transpose-7)%37] == player || jeu[(37+transpose-8)%37] == player || jeu[(37+transpose-14)%37] == player)
				val -= 50;	
			else
				val -= 75;
		}
		if (jeu[(37+transpose-4)%37] == opponent){
			if (jeu[(37+transpose-0)%37] == player)
				val -= 50;
		else
				val -= 75;
		}
		if (jeu[(37+transpose-27)%37] == opponent){
			if (jeu[(37+transpose-21)%37] == player)
				val -= 50;
			else
				val -= 75;
			}
		if (jeu[(37+transpose-1)%37] == opponent && (jeu[(37+transpose-0)%37] == opponent || jeu[(37+transpose-0)%37] == player)) {
			if (jeu[(37+transpose-2)%37] == player && jeu[(37+transpose-3)%37] == 0)
				val -= 1000;
			else
				val -= 25;
		}
		if (jeu[(37+transpose-14)%37] == opponent && (jeu[(37+transpose-21)%37] == opponent || jeu[(37+transpose-21)%37] == player)) {
			if (jeu[(37+transpose-8)%37] == player && jeu[(37+transpose-3)%37] == 0)
				val -= 1000;
			else
				val -= 25;
		}
		if (jeu[(37+transpose-0)%37] == opponent || jeu[(37+transpose-21)%37] == opponent )	val -= 100;
	   	
		
		
	   	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	   	
	   	
	   	
	   	
	
		
		return val;
	
	}
	
	private static int paquets(Configuration conf,byte player) {
		int val=0;
		
		byte[] jeu=conf.tPlateau,tCaseAutour={-8,-9, 1, 8, 9, -1};;
		for (int i=0; i<jeu.length; i++){
			if (jeu[i] == player){
				for(int j=0; j<tCaseAutour.length;j++)
					if(jeu[i+tCaseAutour[j]] == player){
						val+=20;
						if(jeu[i+tCaseAutour[(j+1+6)%6]] == player||jeu[i+tCaseAutour[(j-1+6)%6]] == player){
							val+=20;
						}
					}
					
			}
		}
		
		
		
		return val;
	}

	public static int evaluation(Configuration conf){
		Byte[] jeu = conf.convertTabPlateauEn37(); //retourne un tableau de 37 cases
		//jeu <=> Byte[37] : plateau du jeu avec 37 cases
		int val=position(jeu, conf.player);
		//val+=paquets(conf,conf.player);
		
		val -= position(jeu, conf.opponent);
		//val -= paquets(conf,conf.opponent);
		
		if(conf.whoWin()==conf.player){
			val+=100000000;
		}
		else if(conf.whoWin()==conf.opponent) {
			val-=100000000;
		}
		/*conf.printHexRV();
		System.out.println(val);*/
		return val;
	}

	//ALPHA BETA
	private static int alphaBeta(Configuration conf, int alpha, int beta,byte depth,byte max){
		if ((conf.whoWin() != 0) || (depth >= max)){
			return evaluation(conf);
		}
		else{
			int meilleur = Integer.MIN_VALUE;
			for (Configuration fconf : listeFils(conf)) {
				int suiv=-alphaBeta(fconf, -beta, -alpha, (byte) (depth+1),max);
				if (suiv > meilleur) {
					meilleur=suiv;
					if(meilleur > alpha){
						alpha=meilleur;
						if (alpha >= beta) {
							return meilleur;
						}
					}
				}
			}
			return meilleur;
		}
	}
	
	public static Configuration meilleurConf(Configuration conf,byte max){
		Configuration meilleurConf=null;
		int valMeilleur=Integer.MIN_VALUE;
		int val;
		boolean prems=true;
		for (Configuration fconf : listeFils(conf)) {
			val=-alphaBeta(fconf, Integer.MIN_VALUE, Integer.MAX_VALUE, (byte)1, (byte) (max));
			/*
			System.out.println("---------------------------------------------------------------------");
			fconf.printHexRV();
			System.out.println("valeur = "+val+ "meilleur = " + valMeilleur);
			*/
			if(val>valMeilleur||prems){
				valMeilleur=val;
				meilleurConf=fconf;
				prems=false;
			}
		}
		System.out.println("choisi = " + valMeilleur);
		return meilleurConf;
	}

	public int Min(int a, int b){
		if (a<=b) return a;
		else return b;
	}
	public int Max(int a, int b){
		if (a<=b) return b;
		else return a;
	}
	
	@SuppressWarnings("unused")
	private static void verifFils(Configuration conf,byte depth){
		if(depth<5){
			for (Configuration fconf : listeFils(conf)) {
				if(fconf==null){
					conf.printRV();
					throw new NullPointerException("Null"); 
				}
				verifFils(fconf,(byte) (depth+1));
			}
		}
	}
	
	public static void main(String[] args) {
		
		Configuration conf = new Configuration();
		byte[] t =new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,3,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0};
		 for (int i = 0; i < t.length; i++) {
			conf.tPlateau[Configuration.en81(i)]=t[i];
		}
		System.out.println(evaluation(conf));
		conf.changePlayer();
		conf.printHexRV();
		meilleurConf(conf, (byte) 1).printHexRV();
	}
}
