package rest.utilities;
import com.jayway.restassured.response.ValidatableResponse;

import java.util.*;

public class ApiUtils {

    public static List<HashMap<String, String>> getListFromResponse(ValidatableResponse response, String array) {
        return response.extract().body().jsonPath().getList(array);
    }

    public static Map<String, String> randomObjectFromList(List<HashMap<String, String>> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static List<String> getValuesFromObjectList(List<HashMap<String, String>> list, String key) {
        List<String> values = new ArrayList<>();
        for(HashMap<String, String> obj : list) {
            values.add(obj.get(key));
        }
        return values;
    }
}
