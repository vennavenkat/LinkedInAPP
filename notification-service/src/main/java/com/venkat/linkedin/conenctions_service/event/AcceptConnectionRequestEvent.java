package com.venkat.linkedin.conenctions_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class AcceptConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
