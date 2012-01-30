import java.util.ArrayList;

public class IA{

    protected byte depthMax = 6;
    protected long nbAppelListeFils = 0;//a suppr
    protected long nbConfigFils = 0;//a suppr
    public IA(){
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
   	
    public static int position(Byte[] jeu, byte player){
       
       	int val=0;
       	byte opponent=0;
       	Byte empty = 0;
       	if (player == 1) opponent = 2;
       	else if (player == 2) opponent = 1;
       	else System.out.println("erreur IA");

   	/* POSITION IA */   
       	
		int [] tabIA = {50, 15, 50, 1000,
						40, 25, 25, 50, 50,
						40, 25, 25, 50, 25, 15,
						60, 30, 30, 50, 25, 25, 50,
						30, 50, 50, 30, 25, 40,
						75, 75, 50, 30, 40,
						9000, 75, 30, 60 
		};
		
		/*
		0, 1, 2, 3
		4, 5, 6, 7, 8,
		9, 10, 11, 12, 13, 14
		15, 16, 17, 18, 19, 20, 21
		22, 23, 24, 25, 26, 27
		28, 29, 30, 31, 32,
		33, 34, 35, 36
		*/
				
		
		for (int i=0; i<37; i++){
			if (jeu[i] == player){
				val += tabIA[i];
			}
		}  
	 
    	if ((jeu[22] == player) && (jeu[28] != opponent))	val += 120;
    	if ((jeu[35] == player) && (jeu[34] != opponent)) 	val += 120;
   		
    	if (jeu[29] == player){
    		if ((jeu[28] != opponent) || (jeu[34] != opponent))
    			val += 50;
    	}
    	
    	if ((jeu[23] == player) && (jeu[22] != opponent))		val += 20;
    	if ((jeu[30] == player) && (jeu[35] != opponent))		val += 20;

    	    	   	
    	if (((jeu[24] == player) && (jeu[18] == player)) || ((jeu[18] == player) && (jeu[12] == player)))
    		val += 30;
    		
    		   
       	if ((jeu[28] == player || jeu[29] == player || jeu[34] == player) && jeu[33] == 0) //sur le point de gagner
    		val += 1000;
    	
      
	/////////////////////////////////////////////////////////    
    ////// POSITION JOUEUR /////////
	
       	int [] tabJoueur = {60,30,75,9000,
       					40,30,50,75,75,
       					40,25,30,50,50,30,
       					50,25,25,50,30,30,60,
       					15,25,50,25,25,40,
       					50,50,25,25,40,
       					1000,50,15,50}; 
       	
		for (int i=0; i<37; i++){
			if (jeu[i] == opponent){
				val -= tabJoueur[i];
			}
		}  
    	
		if ((jeu[2] == opponent || jeu[7] == opponent || jeu[8] == opponent) && jeu[3] == empty) //sur le point de perdre
    		val -= 1000;

    	if (jeu[12] == opponent) {
			if (jeu[7] == 0)	val -= 75;
    			else 			val -= 50;
				
	}
	if (jeu[6] == opponent) {
		if (jeu[1] == player || jeu[2] == player || jeu[7] == player)
			val -=50;		
    		else	val -= 75;
				
		}
		if (jeu[13] == opponent) {
			if (jeu[7] == player || jeu[8] == player || jeu[14] == player)
				val -= 50;	
    		else
				val -= 75;
		}
		if (jeu[4] == opponent){
			if (jeu[0] == player)
				val -= 50;
		else
				val -= 75;
		}
		if (jeu[27] == opponent){
    		if (jeu[21] == player)
				val -= 50;
			else
				val -= 75;
			}
		if (jeu[1] == opponent && (jeu[0] == opponent || jeu[0] == player)) {
    		if (jeu[2] == player && jeu[3] == 0)
				val -= 1000;
			else
				val -= 25;
		}
		if (jeu[14] == opponent && (jeu[21] == opponent || jeu[21] == player)) {
    		if (jeu[8] == player && jeu[3] == 0)
				val -= 1000;
			else
				val -= 25;
		}
		if (jeu[0] == opponent || jeu[21] == opponent )	val -= 100;  	
			  	
   		
   	 ////////////////////////////////////////////////////////
    	if(player==2){
    		return -val;
    	}
    	else
    	return val;
    
    }
    
    
 
    //pas besoin de rajouter un paramèter joueur, c'est déjà un attribut de l'objet configurtion : conf.player
    
   	public static int evaluation(Configuration conf){
   		Byte[] jeu = conf.convertTabPlateauEn37(); //retourne un tableau de 37 cases
   		//jeu <=> Byte[37] : plateau du jeu avec 37 cases
   		int val=position(jeu, conf.player);
   		conf.printHexRV();
   		System.out.println(val);
   		return val;
   	}

   	
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
		if (typeSauteFirst == 3)
			typeSauteFirst = typeSaute;
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
		for (Configuration fconf : listeFils(conf)) {
			val=-alphaBeta(fconf, Integer.MIN_VALUE, Integer.MAX_VALUE, (byte)1, (byte) (max));
			if(val>valMeilleur);{
				valMeilleur=val;
				meilleurConf=fconf;
			}
		}
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
		
		Configuration config = new Configuration();
		config.printRV();
		System.out.println(evaluation(config));
		System.out.println("\n********************************************");
		/*
		long timeI = System.currentTimeMillis();
		ia.testRec(config,(byte)0);
		long timeF = System.currentTimeMillis();
		System.out.println("Avec niveau "+ia.depthMax+"\n"+ia.nbAppelListeFils +" fois listeFils,\n genère "+ia.nbConfigFils+" configuration,\n pendant "+((timeF-timeI)*0.001)+"sec");
		*/
		
		verifFils(config, (byte) 0);
		
		//Configuration bou = meilleurConf(config, (byte) 6);
		config.printHexRV();
		System.out.println(evaluation(config));
		config.changePlayer();
		System.out.println(evaluation(config));
	}
}
