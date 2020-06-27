package royalstacks.app.model.dataGenerator;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public abstract class Gen {

    public static char randomChar(char maxChar){
        return (char)('A' + Math.random() * (maxChar - 'A' + 1));
    }

    public static boolean generateRandomTrueFalse(int percentageTrue) {
        final int MAX = 100;
        return (Math.random() * MAX < percentageTrue);
    }

    public static int randomInt(int min, int max){
        return (int)((Math.random()*(max-min+1))+min);
    }

    public static JSONArray createJsonArrayFromFile(String filePath) {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
            JSONArray jsonArray = (org.json.simple.JSONArray) jsonParser.parse(fileReader);
            return jsonArray;
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static BigDecimal partiallyRandomAmount(int min, int maxCommon, int maxUncommon, int percentageCommon){
        double randomAmount;
        final int INT_100 = 100;
        final double DOUBLE_100 = 100.0;

        if(Gen.generateRandomTrueFalse(percentageCommon)){
            randomAmount =(Gen.randomInt(min, maxCommon * INT_100)) / DOUBLE_100;
        }
        else {
            randomAmount = (Gen.randomInt(min, maxUncommon * INT_100)) / DOUBLE_100;
        }
        return BigDecimal.valueOf(randomAmount);
    }


}
