package component;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 * 可以直接应用的滚轮轴类，基于ModernScrollBarUI生成。
 * @author liujiwei
 * @see ModernScrollBarUI
 */
public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
        setBackground(new Color(242, 242, 242));
        setUnitIncrement(20);
    }
}
