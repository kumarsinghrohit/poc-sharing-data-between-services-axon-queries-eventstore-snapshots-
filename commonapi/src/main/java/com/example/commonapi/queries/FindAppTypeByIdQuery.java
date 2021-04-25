package com.example.commonapi.queries;

import lombok.Value;

/**
 * The Axon query that the client sends when the {@link App}s in the read model
 * are to be requested by a specific ID.
 */
@Value
public final class FindAppTypeByIdQuery {

    private String appId;

}
