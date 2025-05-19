package guru.ga.country.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ErrorJson {

    private final String apiVersion;
    private final Error error;

    public ErrorJson(String apiVersion, int code, String message, String domain, String reason) {
        this.apiVersion = apiVersion;
        this.error = new Error(
                code,
                message,
                List.of(new Error.ErrorDetail(domain, reason, message)));
    }

    public static ErrorJson fromAttributesMap(String apiVersion, Map<String, Object> attributesMap) {
        return new ErrorJson(
                apiVersion,
                (int) attributesMap.get("status"),
                (String) attributesMap.getOrDefault("error", "No message found"),
                (String) attributesMap.getOrDefault("path", "No path found"),
                (String) attributesMap.getOrDefault("error", "No message found"));
    }

    public Map<String, Object> toAttributesMap() {
        return Map.of(
                "apiVersion", apiVersion,
                "error", error);
    }

    private record Error(
            int code,
            String message,
            List<ErrorDetail> errors) {

        private record ErrorDetail(
                String domain,
                String reason,
                String message){
        }
    }
}
