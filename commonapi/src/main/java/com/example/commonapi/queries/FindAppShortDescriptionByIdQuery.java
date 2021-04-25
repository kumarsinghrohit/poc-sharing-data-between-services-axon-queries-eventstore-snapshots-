package com.example.commonapi.queries;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Axon query that the client sends when all {@link App}s in the read model
 * are to be requested for the currently logged in user.
 */

@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FindAppShortDescriptionByIdQuery {

    private final String appId;
    private final String versionId;
}
