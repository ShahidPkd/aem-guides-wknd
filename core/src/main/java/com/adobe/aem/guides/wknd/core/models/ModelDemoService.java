package com.adobe.aem.guides.wknd.core.models;

import java.util.Iterator;

import com.day.cq.wcm.api.Page;

public interface ModelDemoService {

    public Iterator<Page> getPageList();

}