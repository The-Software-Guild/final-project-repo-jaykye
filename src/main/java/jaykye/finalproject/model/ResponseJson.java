package jaykye.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseJson {
    private ResponseContentJson response;

    public ResponseContentJson getResponse() {
        return response;
    }

    public void setResponse(ResponseContentJson response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseContent='" + response + '\'' +
                '}';
    }
}
