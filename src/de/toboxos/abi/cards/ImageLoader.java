package de.toboxos.abi.cards;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.toboxos.abi.Abi;

public class ImageLoader {

	public static final int 
		ARWEN = 1, 
		AXEL = 2, 
		COOPER = 3, 
		GENERAL = 4,
		GEORG = 5, 
		FLAMUR = 6, 
		DANIEL = 7, 
		SCHLACHTER = 8, 
		EMILIA = 9, 
		FELIX = 10, 
		DUBOWY = 11, 
		MANU = 12, 
		FELIX_G = 13, 
		STEFFEN = 14,
		ANWALT = 15,
		FRANZI = 16,
		MARIKE = 17,
		NILS = 18,
		SANDRO = 19,
		ZELENKA = 20,
		ALLGEIER = 21,
		TARASCHEWSKI = 22,
		FORSTNER = 23,
		SCHELLMANN = 24,
		WEISSWURST = 25,
		WIECH = 26,
		TORNAU = 27,
		KREUZER = 28,
		MISCHO = 29;
	
	
	private static Image 
		arwen, axel, cooper, general, georg, flamur, daniel, schlachter, emilia, felix, dubowy, manu, felix_g,
		steffen, anwalt, franzi, marike, nils, sandro, zelenka, allgeier, taraschewski, forstner, schellmann,
		weisswurst, wiech, tornau, kreuzer, mischo;
	
	public static void load() {
		try {
			arwen = ImageIO.read( Abi.classPath.getResource("/de/toboxos/abi/res/arwen.jpg") );
			axel = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/axel.jpg") );
			cooper = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/cooper.jpg") );
			general = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/general.jpg") );
			georg = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/georg.jpg") );
			flamur = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/flamur.jpg") );
			daniel = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/daniel.jpg") );
			schlachter = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/schlachter.jpg") );
			emilia = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/emilia.jpg") );
			felix = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/felix.jpg") );
			dubowy = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/dubowy.jpg") );
			manu = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/manu.jpg") );
			felix_g = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/felix_g.jpg") );
			steffen = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/steffen.jpg") );
			anwalt = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/anwalt.jpg") );
			franzi = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/franzi.jpg") );
			marike = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/marike.jpg") );
			nils = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/nils.jpg") );
			sandro = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/sandro.jpg") );
			zelenka = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/zelenka.jpg") );
			allgeier = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/allgeier.jpg") );
			taraschewski = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/taraschewski.jpg") );
			forstner = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/forstner.jpg") );
			schellmann = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/schellmann.jpg") );
			weisswurst = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/weisswurst.jpg") );
			wiech = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/wiech.jpg") );
			tornau = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/tornau.jpg") );
			kreuzer = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/kreuzer.jpg") );
			mischo = ImageIO.read( Abi.classPath.getClass().getResource("/de/toboxos/abi/res/mischo.jpg") );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Image getImage(int type) {
		switch( type )  {
			case ARWEN:
				return arwen;
			case AXEL:
				return axel;
			case COOPER:
				return cooper;
			case GENERAL:
				return general;
			case GEORG:
				return georg;
			case FLAMUR:
				return flamur;
			case DANIEL:
				return daniel;
			case SCHLACHTER:
				return schlachter;
			case EMILIA:
				return emilia;
			case FELIX:
				return felix;
			case DUBOWY:
				return dubowy;
			case MANU:
				return manu;
			case FELIX_G:
				return felix_g;
			case STEFFEN:
				return steffen;
			case ANWALT:
				return anwalt;
			case FRANZI:
				return franzi;
			case MARIKE:
				return marike;
			case NILS:
				return nils;
			case SANDRO:
				return sandro;
			case ZELENKA:
				return zelenka;
			case ALLGEIER:
				return allgeier;
			case TARASCHEWSKI:
				return taraschewski;
			case FORSTNER:
				return forstner;
			case SCHELLMANN:
				return schellmann;
			case WEISSWURST:
				return weisswurst;
			case WIECH:
				return wiech;
			case TORNAU:
				return tornau;
			case KREUZER:
				return kreuzer;
			case MISCHO:
				return mischo;
			default:
				return null;
		}
	}
	
}
