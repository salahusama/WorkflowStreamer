package com.workflowstreamer.resources;

import com.workflowstreamer.WorkflowStreamerConstants;
import com.workflowstreamer.core.analytics.ImmutableAnalyticsEvent;
import com.workflowstreamer.manager.AnalyticsManager;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/analytics")
@Produces(MediaType.APPLICATION_JSON)
public class AnalyticsResource {
    private AnalyticsManager analyticsManager;

    public AnalyticsResource(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    @GET
    public List<ImmutableAnalyticsEvent> getAnalyticsEvents() {
        return analyticsManager.getAnalyticsEvents();
    }

    @POST
    @Path("/example")
    public void fireAnalyticsEvent() {
        analyticsManager.fireEvent(ImmutableAnalyticsEvent.builder()
                .eventName(WorkflowStreamerConstants.Events.TASK_INTERACTION)
                .eventType(WorkflowStreamerConstants.Types.CREATED_TASK)
                .time(new Date())
                .build()
        );
    }
}
