package dev.sorn.autoworker;

import static dev.sorn.autoworker.Node.Builder.node;
import static java.util.UUID.randomUUID;

public interface NodeTestData extends EnvelopeTestData {

    default Node.Builder aNode() {
        return node()
            .id(randomUUID().toString())
            .envelope(anEnvelope().build());
    }

}
