package com.yu.init.websocket;

import lombok.Data;

import java.util.Date;

@Data
public class RequestMessage {
    private Long id;

    private String identification;

    private Date time;

    private String message;

}
