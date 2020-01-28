/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adaptive.common.lov.paging;

import com.adaptive.common.vo.SearchObject;

/**
 *
 * @author destri.hs
 */
public class DefaultSearchObject implements SearchObject<Object> {
    private String column;
    private Object value;

    public DefaultSearchObject(String column, Object value) {
        this.column = column;
        this.value = value;
    }
    
    
    public String getSearchColumn() {
        return this.column;
    }

    public Object getSearchValue() {
        return this.value;
    }

    public boolean isEmpty() {
        return false;
    }

    public String getSearchValueAsString() {
        return this.value.toString();
    }
    
}
