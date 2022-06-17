package Forms;

import java.awt.*;

public class SwingUtil {
    /*
     * 根据容器的大小，计算居中显示时左上角坐标
     * @return 容器左上角坐标
     */
    public static Point centreContainer(Dimension size) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获得屏幕大小
        int x = (screenSize.width - size.width) / 2;// 计算左上角的x坐标
        int y = (screenSize.height - size.height) / 2;// 计算左上角的y坐标
        return new Point(x, y);// 返回左上角坐标
    }
}
