package shared.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseHelper {
    static Logger LOG = Logger.getLogger(ResponseHelper.class.getName());
    public static String getResponseFromConnection(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();

        LOG.log(Level.INFO,"Made response to : " + con.getURL());
        LOG.log(Level.INFO,"Made response with : " + con.getRequestMethod());
        LOG.log(Level.INFO,"Response code : " + responseCode);


        System.out.println("Response Code made to url :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();

            LOG.log(Level.INFO,"Response string : " + result);
            return result;
        } else {
            return null;
        }
    }
}
