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
package org.apache.sling.scripting.sightly.apps.wknd.components.customheader;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class customheader__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_model = null;
Object _global_templates = null;
Collection var_collectionvar6_list_coerced$ = null;
_global_model = renderContext.call("use", com.adobe.aem.guides.wknd.core.models.CustomHeader.class.getName(), obj());
_global_templates = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div data-cmp-customheader=\"customheader\" class=\"cmp-mainheader\">\r\n  ");
{
    boolean var_testvariable0 = (!renderContext.getObjectModel().toBoolean(renderContext.getObjectModel().resolveProperty(_global_model, "isEmpty")));
    if (var_testvariable0) {
        out.write("\r\n    \r\n    <div class=\"cmp-mainheader__logo\">\r\n\r\n    <a");
        {
            String var_attrcontent1 = (("http://localhost:4502/" + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_model, "homeLink"), "uri"))) + ".html?query=disabled");
            out.write(" href=\"");
            out.write(renderContext.getObjectModel().toString(var_attrcontent1));
            out.write("\"");
        }
        out.write("><img");
        {
            Object var_attrvalue2 = renderContext.getObjectModel().resolveProperty(_global_model, "logo");
            {
                Object var_attrcontent3 = renderContext.call("xss", var_attrvalue2, "uri");
                {
                    boolean var_shoulddisplayattr5 = (((null != var_attrcontent3) && (!"".equals(var_attrcontent3))) && ((!"".equals(var_attrvalue2)) && (!((Object)false).equals(var_attrvalue2))));
                    if (var_shoulddisplayattr5) {
                        out.write(" src");
                        {
                            boolean var_istrueattr4 = (var_attrvalue2.equals(true));
                            if (!var_istrueattr4) {
                                out.write("=\"");
                                out.write(renderContext.getObjectModel().toString(var_attrcontent3));
                                out.write("\"");
                            }
                        }
                    }
                }
            }
        }
        out.write(" alt=\"rightpoint\"/></a>\r\n\r\n    </div>\r\n    ");
        {
            Object var_collectionvar6 = renderContext.getObjectModel().resolveProperty(_global_model, "category");
            {
                long var_size7 = ((var_collectionvar6_list_coerced$ == null ? (var_collectionvar6_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar6)) : var_collectionvar6_list_coerced$).size());
                {
                    boolean var_notempty8 = (var_size7 > 0);
                    if (var_notempty8) {
                        {
                            long var_end11 = var_size7;
                            {
                                boolean var_validstartstepend12 = (((0 < var_size7) && true) && (var_end11 > 0));
                                if (var_validstartstepend12) {
                                    out.write("<div class=\"cmp-mainheader__navbar\">");
                                    if (var_collectionvar6_list_coerced$ == null) {
                                        var_collectionvar6_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar6);
                                    }
                                    long var_index13 = 0;
                                    for (Object item : var_collectionvar6_list_coerced$) {
                                        {
                                            boolean var_traversal15 = (((var_index13 >= 0) && (var_index13 <= var_end11)) && true);
                                            if (var_traversal15) {
                                                out.write("\r\n      <a");
                                                {
                                                    String var_attrcontent16 = (("http://localhost:4502/" + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(item, "link"), "uri"))) + ".html?query=disabled");
                                                    out.write(" href=\"");
                                                    out.write(renderContext.getObjectModel().toString(var_attrcontent16));
                                                    out.write("\"");
                                                }
                                                {
                                                    String var_attrcontent17 = ("cmp-mainheader__navbar-link " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(item, "id"), "attribute")));
                                                    out.write(" class=\"");
                                                    out.write(renderContext.getObjectModel().toString(var_attrcontent17));
                                                    out.write("\"");
                                                }
                                                out.write(">");
                                                {
                                                    Object var_18 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(item, "text"), "text");
                                                    out.write(renderContext.getObjectModel().toString(var_18));
                                                }
                                                out.write("</a>\r\n\r\n    ");
                                            }
                                        }
                                        var_index13++;
                                    }
                                    out.write("</div>");
                                }
                            }
                        }
                    }
                }
            }
            var_collectionvar6_list_coerced$ = null;
        }
        out.write("\r\n    <div class=\"cmp-mainheader__right\">\r\n      <div class=\"cmp-mainheader__right-webrtc\"> <a");
        {
            Object var_attrvalue19 = renderContext.getObjectModel().resolveProperty(_global_model, "weblink");
            {
                Object var_attrcontent20 = renderContext.call("xss", var_attrvalue19, "uri");
                {
                    boolean var_shoulddisplayattr22 = (((null != var_attrcontent20) && (!"".equals(var_attrcontent20))) && ((!"".equals(var_attrvalue19)) && (!((Object)false).equals(var_attrvalue19))));
                    if (var_shoulddisplayattr22) {
                        out.write(" href");
                        {
                            boolean var_istrueattr21 = (var_attrvalue19.equals(true));
                            if (!var_istrueattr21) {
                                out.write("=\"");
                                out.write(renderContext.getObjectModel().toString(var_attrcontent20));
                                out.write("\"");
                            }
                        }
                    }
                }
            }
        }
        out.write("> <i class=\"fa-solid fa-globe my-font\"></i>\r\n        </a>\r\n      </div>\r\n\r\n      <div class=\"cmp-mainheader__right-myspan1\"> <span> | </span></div>\r\n\r\n      <div class=\"cmp-mainheader__right-websearch\"> <i class=\"fa-solid fa-magnifying-glass my-font\" id=\"customSearch\"> </i>\r\n      </div>\r\n      <div class=\"cmp-mainheader__right-myspan2\"> <span> | </span></div>\r\n\r\n      <div class=\"cmp-mainheader__right-webmenu\"><a href=\"#\"><i class=\"fa-solid fa-bars my-font\"></i></a>\r\n\r\n      </div>\r\n\r\n    </div>\r\n  ");
    }
}
out.write("\r\n</div>\r\n");
{
    Object var_templatevar23 = renderContext.getObjectModel().resolveProperty(_global_templates, "placeholder");
    {
        Object var_templateoptions24_field$_isempty = renderContext.getObjectModel().resolveProperty(_global_model, "isEmpty");
        {
            java.util.Map var_templateoptions24 = obj().with("isEmpty", var_templateoptions24_field$_isempty);
            callUnit(out, renderContext, var_templatevar23, var_templateoptions24);
        }
    }
}


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

