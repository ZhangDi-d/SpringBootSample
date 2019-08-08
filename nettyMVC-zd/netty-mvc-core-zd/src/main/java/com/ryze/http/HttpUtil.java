package com.ryze.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Created by xueLai on 2019/8/8.
 * 响应处理工具类
 * MIME TYPE
 * https://stackoverflow.com/questions/477816/what-is-the-correct-json-content-type?page=1&tab=votes#tab-top
 */
public class HttpUtil {
    private static String CONTENT_TYPE = "Content-Type";
    private static String CONTENT_LENGTH = "Conteng-Length";

    /**
     * 输出纯Json字符串
     */

    public static FullHttpResponse constructJSON(String json) {
        return construct(json, "test/x-json;charset=UTF-8", HttpResponseStatus.OK);
    }

    /**
     * 输出纯字符串
     */
    public static FullHttpResponse constructText(String text) {
        return construct(text, "text/plain;charset=UTF-8", HttpResponseStatus.OK);
    }

    /**
     * 输出纯XML
     */
    public static FullHttpResponse constructXML(String xml) {
        return construct(xml, "text/xml;charset=UTF-8", HttpResponseStatus.OK);
    }

    /**
     * 输出纯HTML
     */
    public static FullHttpResponse constructHTML(String html) {
        return construct(html, "text/html;charset=UTF-8", HttpResponseStatus.OK);
    }

    public static FullHttpResponse getErroResponse() {
        return construct("Server error", "text/plain;charset=UTF-8", HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    public static FullHttpResponse getNotFoundResponse() {
        return construct("Request not found", "text/plain;charset=UTF-8", HttpResponseStatus.NOT_FOUND);
    }


    public static FullHttpResponse construct(String text, String contentType, HttpResponseStatus status) {
        if (null == text) {
            text = "";
        }
        ByteBuf byteBuf = Unpooled.wrappedBuffer(text.getBytes());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
        response.headers().add(CONTENT_TYPE, contentType);
        response.headers().add(CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
        return response;
    }
}
