import com.monster.message.DingRobotMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <a href="https://open.dingtalk.com/document/robots/custom-robot-access"/>
 * 钉钉机器人 API
 * <p>
 * Create by monster gan on 2023/3/12 21:24
 */
public class DingRobotClient {

    private static final String DEFAULT_URL = "https://oapi.dingtalk.com/robot/send";

    private final RestOperations restOperations;

    private static final HttpHeaders HEADERS;

    static {
        // 钉钉请求头为 application/json
        HEADERS = new HttpHeaders();
        HEADERS.setContentType(MediaType.APPLICATION_JSON);
    }

    public DingRobotClient() {
        restOperations = new RestTemplate();
    }

    public DingRobotClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    /**
     * 钉钉接口签名
     *
     * @param timestamp 时间戳
     * @param secret    签名密钥
     * @return 签名
     */
    public static String sign(long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        try {
            String algorithm = "HmacSHA256";
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8.name());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            throw new UnsupportedOperationException(e);
        }
    }


    /**
     * 调用钉钉机器人接口
     *
     * @param message     钉钉机器人消息
     * @param accessToken accessToken
     * @param signKey     signKey
     */
    public ResponseEntity<String> callDingRobot(DingRobotMessage message, String accessToken, String signKey) {
        return callDingRobot(DEFAULT_URL, message, accessToken, signKey);
    }

    /**
     * 调用钉钉机器人接口
     *
     * @param url         接口URL
     * @param message     钉钉机器人消息
     * @param accessToken accessToken
     * @param signKey     signKey
     */
    public ResponseEntity<String> callDingRobot(String url, DingRobotMessage message, String accessToken, String signKey) {
        try {
            HttpEntity<DingRobotMessage> entity = new HttpEntity<>(message, HEADERS);
            long timeStamp = System.currentTimeMillis();
            return restOperations.postForEntity(String.format("%s?access_token=%s&timestamp=%s&sign=%s", url, accessToken, timeStamp, sign(timeStamp, signKey)), entity, String.class);
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }
    }


}
