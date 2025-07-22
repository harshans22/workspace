package org.example.analytics_service.model;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ActivityEvent {
    private String type;      // e.g., "USER_LOGIN", "MESSAGE_SENT"
    private String userId;
    private String workspaceId;
    private long timestamp;

    //getter
    public String getType() {
        return type;
    }

    // Constructors, getters, setters
}
