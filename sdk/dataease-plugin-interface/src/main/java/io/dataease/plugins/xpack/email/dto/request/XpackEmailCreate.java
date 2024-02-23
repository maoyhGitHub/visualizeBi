package io.dataease.plugins.xpack.email.dto.request;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import lombok.Data;

@Data
public class XpackEmailCreate implements Serializable{

    private XpackEmailTaskRequest request;

    private String emailContent;


    public XpackEmailTaskRequest fillContent() {
        if (StringUtils.isBlank(emailContent)) {
            return request;
        }
        String htmlEscape = HtmlUtils.htmlEscape(emailContent);

        byte[] bytes;
        bytes = htmlEscape.getBytes(StandardCharsets.UTF_8);
        request.setContent(bytes);
        return request;
    }
    
}
