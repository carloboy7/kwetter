package services.backend;

import shared.restModels.Kweet;
import shared.restModels.User;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.lang.reflect.Type;

import static services.helpers.RestCommuncationHelper.getResponseString;
import static services.helpers.RestCommuncationHelper.postRequest;

abstract class BackendCommunication {
    protected Jsonb jsonb = JsonbBuilder.create();
    Object callUrlAndCastResult(Type type, String url, int userId) throws IOException {
        return callUrlAndCastResultMethode(type, String.format(url, userId), "GET");
    }
    Object callUrlAndCastResult(Type type, String url, String param) throws IOException {
        return callUrlAndCastResultMethode(type, String.format(url, param), "GET");
    }
    private Object callUrlAndCastResultMethode(Type type, String url, String requestMethode) throws IOException {
        String stringResult = getResponseString(url, requestMethode);

        if (stringResult != null) {
            return jsonb.fromJson(stringResult, type);
        } else {
            return null;
        }
    }

}
