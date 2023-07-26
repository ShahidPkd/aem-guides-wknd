package com.adobe.aem.guides.wknd.core.servlets;
// servlet neccessory import step by step

import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Reference;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;

import javax.servlet.ServletException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

//workflow imports
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;

@Component(service = Servlet.class)
@SlingServletPaths(value = {"/bin/callwf/for/versioning"})
public class CallWorkflowFromServlet extends SlingAllMethodsServlet {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        String workFLowExecuting = "";
        String payload = req.getParameter("query");
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            if(StringUtils.isNotBlank(payload)){
                WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
                WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/shahid-version");
                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);
                resp.getWriter().write("Executing Workflow");
                workFLowExecuting=workflowSession.startWorkflow(workflowModel, workflowData).getState();
                resp.getWriter().write(workFLowExecuting + " Workflow");
                resp.getWriter().write("Completed Workflow");
            }
        } catch (Exception e) {
            resp.getWriter().write("Sorry some error occurred and error is: " + e.toString());
        }
    }

}
