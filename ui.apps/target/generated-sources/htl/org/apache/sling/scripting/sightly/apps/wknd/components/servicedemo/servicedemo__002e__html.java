/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.wknd.components.servicedemo;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class servicedemo__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_service = null;
Collection var_collectionvar0_list_coerced$ = null;
out.write("<h1>Service Demo</h1>\r\n\r\n");
_global_service = renderContext.call("use", com.adobe.aem.guides.wknd.core.models.ModelDemoService.class.getName(), obj());
out.write("<div></div>\r\n\r\n\r\n\r\n");
{
    Object var_collectionvar0 = renderContext.getObjectModel().resolveProperty(_global_service, "pageList");
    {
        long var_size1 = ((var_collectionvar0_list_coerced$ == null ? (var_collectionvar0_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar0)) : var_collectionvar0_list_coerced$).size());
        {
            boolean var_notempty2 = (var_size1 > 0);
            if (var_notempty2) {
                {
                    long var_end5 = var_size1;
                    {
                        boolean var_validstartstepend6 = (((0 < var_size1) && true) && (var_end5 > 0));
                        if (var_validstartstepend6) {
                            out.write("<div>");
                            if (var_collectionvar0_list_coerced$ == null) {
                                var_collectionvar0_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar0);
                            }
                            long var_index7 = 0;
                            for (Object item : var_collectionvar0_list_coerced$) {
                                {
                                    long itemlist_field$_count = (renderContext.getObjectModel().toNumber(org.apache.sling.scripting.sightly.compiler.expression.nodes.BinaryOperator.ADD.eval(var_index7, 1)).longValue());
                                    {
                                        boolean var_traversal9 = (((var_index7 >= 0) && (var_index7 <= var_end5)) && true);
                                        if (var_traversal9) {
                                            out.write("\r\n\r\n    <p>");
                                            {
                                                String var_10 = ((" Page " + renderContext.getObjectModel().toString(renderContext.call("xss", itemlist_field$_count, "text"))) + " Title : ");
                                                out.write(renderContext.getObjectModel().toString(var_10));
                                            }
                                            out.write("<b>");
                                            {
                                                String var_11 = (renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(item, "title"), "text")) + " ");
                                                out.write(renderContext.getObjectModel().toString(var_11));
                                            }
                                            out.write("</b></p>\r\n\r\n");
                                        }
                                    }
                                }
                                var_index7++;
                            }
                            out.write("</div>");
                        }
                    }
                }
            }
        }
    }
    var_collectionvar0_list_coerced$ = null;
}
out.write("\r\n\r\n\r\n<form method=\"get\" action=\"../../../../../../../../../../../bin/savedatainnode\" enctype=\"multipart/form-data\">\r\n    <input type=\"text\" name=\"fname\"/>\r\n    <input type=\"text\" name=\"lname\"/>\r\n    <input type=\"file\" name=\"file\"/>\r\n    <input type=\"submit\" value=\"Upload\"/>\r\n  </form>");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

