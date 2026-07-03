package com.dmsacad.store.services.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class WebhookRequest {
    Map<String, String> headers;
    String payload;
}
