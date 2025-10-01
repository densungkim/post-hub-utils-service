package com.post.hub.utilsservice.utils.enums.converter;

import com.post.hub.utilsservice.model.enums.PostHubService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PostHubServiceConverter implements AttributeConverter<PostHubService, String> {
    @Override
    public String convertToDatabaseColumn(PostHubService postHubService) {
        return postHubService.getValue();
    }

    @Override
    public PostHubService convertToEntityAttribute(String s) {
        return PostHubService.fromValue(s);
    }
}
