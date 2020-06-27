package royalstacks.app.model.dataGenerator;

import royalstacks.app.model.Employee;



public class EmployeeGenerator {
    final private static String HB_USERNAME = "headbusiness";
    final private static String HB_F_NAME = "Anna";
    final private static String HB_L_NAME = "Werner";
    final private static String HB_POS = "headbusiness";

    final private static String HP_USERNAME = "headprivate";
    final private static String HP_F_NAME = "Peter";
    final private static String HD_L_NAME = "Hamers";
    final private static String HP_POS = "headprivate";

    final private static String HP_STATIC = "Auto!12345";

    public static Employee headBusinessGenerator() {
        return new Employee(HB_USERNAME, HP_STATIC, HB_F_NAME, HB_L_NAME, HB_POS);
    }
    public static Employee headPrivateGenerator() {
        return  new Employee(HP_USERNAME, HP_STATIC, HP_F_NAME, HD_L_NAME, HP_POS);
    }
}
