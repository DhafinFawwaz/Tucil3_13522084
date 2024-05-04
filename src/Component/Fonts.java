package Component;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Fonts {


    public static void LoadFont() {
        try {
            String prefix = "src/Asset/Font/";
            OutfitBlack = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Black.ttf")).deriveFont(16f);
            OutfitBold = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Bold.ttf")).deriveFont(16f);
            OutfitExtraBold = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-ExtraBold.ttf")).deriveFont(16f);
            OutfitExtraLight = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-ExtraLight.ttf")).deriveFont(16f);
            OutfitLight = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Light.ttf")).deriveFont(16f);
            OutfitMedium = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Medium.ttf")).deriveFont(16f);
            OutfitRegular = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Regular.ttf")).deriveFont(16f);
            OutfitSemiBold = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-SemiBold.ttf")).deriveFont(16f);
            OutfitThin = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(prefix+"Outfit-Thin.ttf")).deriveFont(16f);
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(OutfitBold);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font OutfitBlack           = new Font("sansserif", Font.PLAIN, 16);
    public static Font OutfitBold            = new Font("sansserif", Font.PLAIN, 16);
    public static Font OutfitExtraBold       = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitExtraLight      = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitLight           = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitMedium          = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitRegular         = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitSemiBold        = new Font("sansserif", Font.PLAIN, 16);;
    public static Font OutfitThin            = new Font("sansserif", Font.PLAIN, 16);;
}
