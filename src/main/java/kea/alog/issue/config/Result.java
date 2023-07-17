package kea.alog.issue.config;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Result {
    private Boolean isSuccess;
    private String message;
    private Object data;

    @Builder
    public Result(Boolean isSuccess, String message, Object data){
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }
}
