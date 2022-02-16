package jclipper.common.enums;

import lombok.Getter;

import java.awt.*;

/**
 * 颜色代码
 *
 * @author wf2311
 * @time 2016 /08/12 21:16.
 */
@Getter
public enum ColorCode implements BaseEnum<Integer, String> {
    /**
     * The White.
     */
    White("纯白", 0xFFFFFF, new Color(255, 255, 255)),
    /**
     * The Lavender blush.
     */
    LavenderBlush("脸红的淡紫色", 0xFFF0F5, new Color(255, 240, 245)),
    /**
     * The Pink.
     */
    Pink("粉红", 0xFFC0CB, new Color(255, 192, 203)),
    /**
     * The Light pink.
     */
    LightPink("浅粉色", 0xFFB6C1, new Color(255, 182, 193)),
    /**
     * The Hot pink.
     */
    HotPink("热情的粉红", 0xFF69B4, new Color(255, 105, 180)),
    /**
     * The Deep pink.
     */
    DeepPink("深粉色", 0xFF1493, new Color(255, 20, 147)),
    /**
     * The Magenta.
     */
    Magenta("洋红", 0xFF00FF, new Color(255, 0, 255)),
    /**
     * The Fuchsia.
     */
    Fuchsia("灯笼海棠(紫红色)", 0xFF00FF, new Color(255, 0, 255)),
    /**
     * The Lavender.
     */
    Lavender("薰衣草花的淡紫色", 0xE6E6FA, new Color(230, 230, 250)),
    /**
     * The Plum.
     */
    plum("李子", 0xDDA0DD, new Color(221, 160, 221)),
    /**
     * The Crimson.
     */
    Crimson("猩红", 0xDC143C, new Color(220, 20, 60)),
    /**
     * The Pale violet red.
     */
    PaleVioletRed("苍白的紫罗兰红色", 0xDB7093, new Color(219, 112, 147)),
    /**
     * The Orchid.
     */
    Orchid("兰花的紫色", 0xDA70D6, new Color(218, 112, 214)),
    /**
     * The Thistle.
     */
    Thistle("蓟", 0xD8BFD8, new Color(216, 191, 216)),
    /**
     * The Violet.
     */
    Violet("紫罗兰", 0xEE82EE, new Color(238, 130, 238)),
    /**
     * The Medium violet red.
     */
    MediumVioletRed("适中的紫罗兰红色", 0xC71585, new Color(199, 21, 133)),
    /**
     * The Medium orchid.
     */
    MediumOrchid("适中的兰花紫", 0xBA55D3, new Color(186, 85, 211)),
    /**
     * The Dark orchid.
     */
    DarkOrchid("深兰花紫", 0x9932CC, new Color(153, 50, 204)),
    /**
     * The Dark voilet.
     */
    DarkVoilet("深紫罗兰色", 0x9400D3, new Color(148, 0, 211)),
    /**
     * The Medium purple.
     */
    MediumPurple("适中的紫色", 0x9370DB, new Color(147, 112, 219)),
    /**
     * The Dark magenta.
     */
    DarkMagenta("深洋红色", 0x8B008B, new Color(139, 0, 139)),
    /**
     * The Blue violet.
     */
    BlueViolet("深紫罗兰的蓝色", 0x8A2BE2, new Color(138, 43, 226)),
    /**
     * The Purple.
     */
    Purple("紫色", 0x800080, new Color(128, 0, 128)),
    /**
     * The Medium slate blue.
     */
    MediumSlateBlue("适中的板岩暗蓝灰色", 0x7B68EE, new Color(123, 104, 238)),
    /**
     * The Slate blue.
     */
    SlateBlue("板岩暗蓝灰色", 0x6A5ACD, new Color(106, 90, 205)),
    /**
     * The Indigo.
     */
    Indigo("靛青", 0x4B0082, new Color(75, 0, 130)),
    /**
     * The Dark slate blue.
     */
    DarkSlateBlue("深岩暗蓝灰色", 0x483D8B, new Color(72, 61, 139)),
    /**
     * The Ghost white.
     */
    GhostWhite("幽灵的白色", 0xF8F8FF, new Color(248, 248, 255)),
    /**
     * The Blue.
     */
    Blue("纯蓝", 0x0000FF, new Color(0, 0, 255)),
    /**
     * The Medium blue.
     */
    MediumBlue("适中的蓝色", 0x0000CD, new Color(0, 0, 205)),
    /**
     * The Midnight blue.
     */
    MidnightBlue("午夜的蓝色", 0x191970, new Color(25, 25, 112)),
    /**
     * The Dark blue.
     */
    DarkBlue("深蓝色", 0x00008B, new Color(0, 0, 139)),
    /**
     * The Navy.
     */
    Navy("海军蓝", 0x000080, new Color(0, 0, 128)),
    /**
     * The Royal blue.
     */
    RoyalBlue("皇军蓝", 0x4169E1, new Color(65, 105, 225)),
    /**
     * The Cornflower blue.
     */
    CornflowerBlue("矢车菊的蓝色", 0x6495ED, new Color(100, 149, 237)),
    /**
     * The Light steel blue.
     */
    LightSteelBlue("淡钢蓝", 0xB0C4DE, new Color(176, 196, 222)),
    /**
     * The Light slate gray.
     */
    LightSlateGray("浅石板灰", 0x778899, new Color(119, 136, 153)),
    /**
     * The Slate gray.
     */
    SlateGray("石板灰", 0x708090, new Color(112, 128, 144)),
    /**
     * The Doder blue.
     */
    DoderBlue("道奇蓝", 0x1E90FF, new Color(30, 144, 255)),
    /**
     * The Alice blue.
     */
    AliceBlue("爱丽丝蓝", 0xF0F8FF, new Color(240, 248, 255)),
    /**
     * The Steel blue.
     */
    SteelBlue("钢蓝", 0x4682B4, new Color(70, 130, 180)),
    /**
     * The Light sky blue.
     */
    LightSkyBlue("淡蓝色", 0x87CEFA, new Color(135, 206, 250)),
    /**
     * The Sky blue.
     */
    SkyBlue("天蓝色", 0x87CEEB, new Color(135, 206, 235)),
    /**
     * The Deep sky blue.
     */
    DeepSkyBlue("深天蓝", 0x00BFFF, new Color(0, 191, 255)),
    /**
     * The Light b lue.
     */
    LightBLue("淡蓝", 0xADD8E6, new Color(173, 216, 230)),
    /**
     * The Pow der blue.
     */
    PowDerBlue("火药蓝", 0xB0E0E6, new Color(176, 224, 230)),
    /**
     * The Cadet blue.
     */
    CadetBlue("军校蓝", 0x5F9EA0, new Color(95, 158, 160)),
    /**
     * The Azure.
     */
    Azure("蔚蓝色", 0xF0FFFF, new Color(240, 255, 255)),
    /**
     * The Light cyan.
     */
    LightCyan("淡青色", 0xE1FFFF, new Color(225, 255, 255)),
    /**
     * The Pale turquoise.
     */
    PaleTurquoise("苍白的绿宝石", 0xAFEEEE, new Color(175, 238, 238)),
    /**
     * The Cyan.
     */
    Cyan("青色", 0x00FFFF, new Color(0, 255, 255)),
    /**
     * The Aqua.
     */
    Aqua("水绿色", 0x00FFFF, new Color(0, 255, 255)),
    /**
     * The Dark turquoise.
     */
    DarkTurquoise("深绿宝石", 0x00CED1, new Color(0, 206, 209)),
    /**
     * The Dark slate gray.
     */
    DarkSlateGray("深石板灰", 0x2F4F4F, new Color(47, 79, 79)),
    /**
     * The Dark cyan.
     */
    DarkCyan("深青色", 0x008B8B, new Color(0, 139, 139)),
    /**
     * The Teal.
     */
    Teal("水鸭色", 0x008080, new Color(0, 128, 128)),
    /**
     * The Medium turquoise.
     */
    MediumTurquoise("适中的绿宝石", 0x48D1CC, new Color(72, 209, 204)),
    /**
     * The Light sea green.
     */
    LightSeaGreen("浅海洋绿", 0x20B2AA, new Color(32, 178, 170)),
    /**
     * The Turquoise.
     */
    Turquoise("绿宝石", 0x40E0D0, new Color(64, 224, 208)),
    /**
     * The Auqamarin.
     */
    Auqamarin("绿玉碧绿色", 0x7FFFAA, new Color(127, 255, 170)),
    /**
     * The Medium aquamarine.
     */
    MediumAquamarine("适中的碧绿色", 0x00FA9A, new Color(0, 250, 154)),
    /**
     * The Medium spring green.
     */
    MediumSpringGreen("适中的春天的绿色", 0xF5FFFA, new Color(245, 255, 250)),
    /**
     * The Mint cream.
     */
    MintCream("薄荷奶油", 0x00FF7F, new Color(0, 255, 127)),
    /**
     * The Spring green.
     */
    SpringGreen("春天的绿色", 0x3CB371, new Color(60, 179, 113)),
    /**
     * The Sea green.
     */
    SeaGreen("海洋绿", 0x2E8B57, new Color(46, 139, 87)),
    /**
     * The Honeydew.
     */
    Honeydew("蜂蜜", 0xF0FFF0, new Color(240, 255, 240)),
    /**
     * The Light green.
     */
    LightGreen("淡绿色", 0x90EE90, new Color(144, 238, 144)),
    /**
     * The Pale green.
     */
    PaleGreen("苍白的绿色", 0x98FB98, new Color(152, 251, 152)),
    /**
     * The Dark sea green.
     */
    DarkSeaGreen("深海洋绿", 0x8FBC8F, new Color(143, 188, 143)),
    /**
     * The Lime green.
     */
    LimeGreen("酸橙绿", 0x32CD32, new Color(50, 205, 50)),
    /**
     * The Lime.
     */
    Lime("酸橙色", 0x00FF00, new Color(0, 255, 0)),
    /**
     * The Forest green.
     */
    ForestGreen("森林绿", 0x228B22, new Color(34, 139, 34)),
    /**
     * The Green.
     */
    Green("纯绿", 0x008000, new Color(0, 128, 0)),
    /**
     * The Dark green.
     */
    DarkGreen("深绿色", 0x006400, new Color(0, 100, 0)),
    /**
     * The Chartreuse.
     */
    Chartreuse("查特酒绿", 0x7FFF00, new Color(127, 255, 0)),
    /**
     * The Lawn green.
     */
    LawnGreen("草坪绿", 0x7CFC00, new Color(124, 252, 0)),
    /**
     * The Green yellow.
     */
    GreenYellow("绿黄色", 0xADFF2F, new Color(173, 255, 47)),
    /**
     * The Olive drab.
     */
    OliveDrab("橄榄土褐色", 0x556B2F, new Color(85, 107, 47)),
    /**
     * The Beige.
     */
    Beige("米色(浅褐色)", 0x6B8E23, new Color(107, 142, 35)),
    /**
     * The Light goldenrod yellow.
     */
    LightGoldenrodYellow("浅秋麒麟黄", 0xFAFAD2, new Color(250, 250, 210)),
    /**
     * The Ivory.
     */
    Ivory("象牙", 0xFFFFF0, new Color(255, 255, 240)),
    /**
     * The Light yellow.
     */
    LightYellow("浅黄色", 0xFFFFE0, new Color(255, 255, 224)),
    /**
     * The Yellow.
     */
    Yellow("纯黄", 0xFFFF00, new Color(255, 255, 0)),
    /**
     * The Olive.
     */
    Olive("橄榄", 0x808000, new Color(128, 128, 0)),
    /**
     * The Dark khaki.
     */
    DarkKhaki("深卡其布", 0xBDB76B, new Color(189, 183, 107)),
    /**
     * The Lemon chiffon.
     */
    LemonChiffon("柠檬薄纱", 0xFFFACD, new Color(255, 250, 205)),
    /**
     * The Pale godenrod.
     */
    PaleGodenrod("灰秋麒麟", 0xEEE8AA, new Color(238, 232, 170)),
    /**
     * The Khaki.
     */
    Khaki("卡其布", 0xF0E68C, new Color(240, 230, 140)),
    /**
     * The Gold.
     */
    Gold("金", 0xFFD700, new Color(255, 215, 0)),
    /**
     * The Cornislk.
     */
    Cornislk("玉米色", 0xFFF8DC, new Color(255, 248, 220)),
    /**
     * The Gold enrod.
     */
    GoldEnrod("秋麒麟", 0xDAA520, new Color(218, 165, 32)),
    /**
     * The Floral white.
     */
    FloralWhite("花的白色", 0xFFFAF0, new Color(255, 250, 240)),
    /**
     * The Old lace.
     */
    OldLace("老饰带", 0xFDF5E6, new Color(253, 245, 230)),
    /**
     * The Wheat.
     */
    Wheat("小麦色", 0xF5DEB3, new Color(245, 222, 179)),
    /**
     * The Moccasin.
     */
    Moccasin("鹿皮鞋", 0xFFE4B5, new Color(255, 228, 181)),
    /**
     * The Orange.
     */
    Orange("橙色", 0xFFA500, new Color(255, 165, 0)),
    /**
     * The Papaya whip.
     */
    PapayaWhip("番木瓜", 0xFFEFD5, new Color(255, 239, 213)),
    /**
     * The Blanched almond.
     */
    BlanchedAlmond("漂白的杏仁", 0xFFEBCD, new Color(255, 235, 205)),
    /**
     * The Navajo white.
     */
    NavajoWhite("Navajo白", 0xFFDEAD, new Color(255, 222, 173)),
    /**
     * The Antique white.
     */
    AntiqueWhite("古代的白色", 0xFAEBD7, new Color(250, 235, 215)),
    /**
     * The Tan.
     */
    Tan("晒黑", 0xD2B48C, new Color(210, 180, 140)),
    /**
     * The Bruly wood.
     */
    BrulyWood("结实的树", 0xDEB887, new Color(222, 184, 135)),
    /**
     * The Bisque.
     */
    Bisque("(浓汤)乳脂,番茄等", 0xFFE4C4, new Color(255, 228, 196)),
    /**
     * The Dark orange.
     */
    DarkOrange("深橙色", 0xFF8C00, new Color(255, 140, 0)),
    /**
     * The Linen.
     */
    Linen("亚麻布", 0xFAF0E6, new Color(250, 240, 230)),
    /**
     * The Peru.
     */
    Peru("秘鲁", 0xCD853F, new Color(205, 133, 63)),
    /**
     * The Peach puff.
     */
    PeachPuff("桃色", 0xFFDAB9, new Color(255, 218, 185)),
    /**
     * The Sandy brown.
     */
    SandyBrown("沙棕色", 0xF4A460, new Color(244, 164, 96)),
    /**
     * The Chocolate.
     */
    Chocolate("巧克力", 0xD2691E, new Color(210, 105, 30)),
    /**
     * The Saddle brown.
     */
    SaddleBrown("马鞍棕色", 0x8B4513, new Color(139, 69, 19)),
    /**
     * The Sea shell.
     */
    SeaShell("海贝壳", 0xFFF5EE, new Color(255, 245, 238)),
    /**
     * The Sienna.
     */
    Sienna("黄土赭色", 0xA0522D, new Color(160, 82, 45)),
    /**
     * The Light salmon.
     */
    LightSalmon("浅鲜肉(鲑鱼)色", 0xFFA07A, new Color(255, 160, 122)),
    /**
     * The Coral.
     */
    Coral("珊瑚", 0xFF7F50, new Color(255, 127, 80)),
    /**
     * The Orange red.
     */
    OrangeRed("橙红色", 0xFF4500, new Color(255, 69, 0)),
    /**
     * The Dark salmon.
     */
    DarkSalmon("深鲜肉(鲑鱼)色", 0xE9967A, new Color(233, 150, 122)),
    /**
     * The Tomato.
     */
    Tomato("番茄", 0xFF6347, new Color(255, 99, 71)),
    /**
     * The Light coral.
     */
    LightCoral("淡珊瑚色", 0xF08080, new Color(240, 128, 128)),
    /**
     * The Rosy brown.
     */
    RosyBrown("玫瑰棕色", 0xBC8F8F, new Color(188, 143, 143)),
    /**
     * The Indian red.
     */
    IndianRed("印度红", 0xCD5C5C, new Color(205, 92, 92)),
    /**
     * The Snow.
     */
    Snow("雪", 0xFFFAFA, new Color(255, 250, 250)),
    /**
     * The Salmon.
     */
    Salmon("鲜肉(鲑鱼)色", 0xFA8072, new Color(250, 128, 114)),
    /**
     * The Misty rose.
     */
    MistyRose("薄雾玫瑰", 0xFFE4E1, new Color(255, 228, 225)),
    /**
     * The Red.
     */
    Red("纯红", 0xFF0000, new Color(255, 0, 0)),
    /**
     * The White smoke.
     */
    WhiteSmoke("白烟", 0xF5F5F5, new Color(245, 245, 245)),
    /**
     * The Gainsboro.
     */
    Gainsboro("Gainsboro", 0xDCDCDC, new Color(220, 220, 220)),
    /**
     * The Light grey.
     */
    LightGrey("浅灰色", 0xD3D3D3, new Color(211, 211, 211)),
    /**
     * The Silver.
     */
    Silver("银白色", 0xC0C0C0, new Color(192, 192, 192)),
    /**
     * The Fire brick.
     */
    FireBrick("耐火砖", 0xB22222, new Color(178, 34, 34)),
    /**
     * The Dark gray.
     */
    DarkGray("深灰色", 0xA9A9A9, new Color(169, 169, 169)),
    /**
     * The Brown.
     */
    Brown("棕色", 0xA52A2A, new Color(165, 42, 42)),
    /**
     * The Dark red.
     */
    DarkRed("深红色", 0x8B0000, new Color(139, 0, 0)),
    /**
     * The Gray.
     */
    Gray("灰色", 0x808080, new Color(128, 128, 128)),
    /**
     * The Maroon.
     */
    Maroon("栗色", 0x800000, new Color(128, 0, 0)),
    /**
     * The Dim gray.
     */
    DimGray("暗淡的灰色", 0x696969, new Color(105, 105, 105)),
    /**
     * The Black.
     */
    Black("纯黑", 0x000000, new Color(0, 0, 0));

    /**
     * The Name.
     */
    private final String name;
    /**
     * The Code.
     */
    private final Integer code;
    /**
     * The Color.
     */
    private final Color color;

    ColorCode(String name, int code, Color color) {
        this.name = name;
        this.code = code;
        this.color = color;
    }

}
