package com.briandidthat.priceserver.domain;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public final class BatchRequest {
    @Size(min = 2, max = 5)
    private List<Request> requests;

    public BatchRequest() {
    }

    public BatchRequest(List<Request> requests) {
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchRequest that = (BatchRequest) o;
        return Objects.equals(requests, that.requests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requests);
    }

}
