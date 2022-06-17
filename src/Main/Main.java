package Main;

import DButil.DBManager;
import Forms.*;
import Forms.AddForm;

public class Main {
    public static LoginForm loginform;

    public static void main(String[] args) {
        loginform=new LoginForm();
        loginform.setVisible(true);
    }
}
