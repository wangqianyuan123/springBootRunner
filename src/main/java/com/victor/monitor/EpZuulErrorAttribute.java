package com.victor.monitor;


import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@Component
public class EpZuulErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes,
                                                  boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(requestAttributes, includeStackTrace);
        result.put("statusCode", result.get("status"));
        result.put("msg", result.get("message"));
        result.remove("status");
        result.remove("message");
        result.remove("timestamp");
        result.remove("error");
        result.remove("errors");
        result.remove("exception");
        result.remove("trace");
        return result;
    }

}

