package com.example.securitybasicflow2.core.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseData<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime processTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private ResponseCode responseCode;

    private String desc;

    public static <T> ResponseData<T> OK() {
        return ResponseData.<T>builder()
                .desc(ResponseCode.OK.getDesc())
                .processTime(LocalDateTime.now())
                .build();
    }

    public static <T> ResponseData<T> OK(T data) {
        return ResponseData.<T>builder()
                .processTime(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static <T> ResponseData<T> ERROR(String desc) {
        return ResponseData.<T>builder()
                .processTime(LocalDateTime.now())
                .desc(desc)
                .build();
    }
}
