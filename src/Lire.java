// class lire.java
import java.io.*;

public class Lire {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static int jint() {
	int i=0;

	try {
	    i = Integer.valueOf(br.readLine()).intValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie int : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }

    
    public static int jint(String msg) {
	System.out.print(msg);
	return Lire.jint();
    }
    
    public static short jshort() {
	short i=0;

	try {
	    i = Short.valueOf(br.readLine()).shortValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie short : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }

    public static short jshort(String msg) {
	System.out.print(msg);
	return Lire.jshort();
    }


    public static byte jbyte() {
	byte i=0;

	try {
	    i = Byte.valueOf(br.readLine()).byteValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie byte : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }
    
    public static byte jbyte(String msg) {
	System.out.print(msg);
	return Lire.jbyte();
    }


    public static long jlong() {
	long i=0;

	try {
	    i = Long.valueOf(br.readLine()).longValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie long : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }
    
    public static long jlong(String msg) {
	System.out.print(msg);
	return Lire.jlong();
    }


    public static float jfloat() {
	float i=0;

	try {
	    i = Float.valueOf(br.readLine()).floatValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie float : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }
    
    public static float jfloat(String msg) {
	System.out.print(msg);
	return Lire.jfloat();
    }



    public static double jdouble() {
	double i=0;

	try {
	    i = Double.valueOf(br.readLine()).doubleValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie double : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }
    
    public static double jdouble(String msg) {
	System.out.print(msg);
	return Lire.jdouble();
    }


    public static boolean jboolean() {
	boolean i=false;

	try {
	    i = Boolean.valueOf(br.readLine()).booleanValue();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie boolean : "+e.getMessage());
	    e.printStackTrace();
	}
	return i;
    }
    
    public static boolean jboolean(String msg) {
	System.out.print(msg);
	return Lire.jboolean();
    }


    public static char jchar() {
	char c=0;

	try {
	    c = br.readLine().charAt(0);
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie char : "+e.getMessage());
	    e.printStackTrace();
	}
	return c;
    }
    
    public static char jchar(String msg) {
	System.out.print(msg);
	return Lire.jchar();
    }


    public static String jString() {
	String s="";

	try {
	    s = br.readLine();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie String : "+e.getMessage());
	    e.printStackTrace();
	}
	return s;
    }
    
    public static String jString(String msg) {
	System.out.print(msg);
	return Lire.jString();
    }


    public static String jstring() {
	String s="";

	try {
	    s = br.readLine();
	}
	catch(Exception e) {
	    System.out.println("Erreur saisie String: "+e.getMessage());
	    e.printStackTrace();
	}
	return s;
    }
    
    public static String jstring(String msg) {
	System.out.print(msg);
	return Lire.jstring();
    }


}
