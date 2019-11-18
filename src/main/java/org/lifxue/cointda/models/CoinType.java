/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lifxue.cointda.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author xuelf
 */
public class CoinType {

    private StringProperty shortName;
    private StringProperty fullName;
    private StringProperty cnName;

    public CoinType() {
        this(null, null, null);
    }

    public CoinType(String shortName, String fullName, String cnName) {
        this.shortName = new SimpleStringProperty(shortName);
        this.fullName = new SimpleStringProperty(fullName);
        this.cnName = new SimpleStringProperty(cnName);
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    /**
     * @return the cnName
     */
    public String getCnName() {
        return cnName.get();
    }

    public StringProperty cnNameProperty() {
        return cnName;
    }

    /**
     * @param cnName the cnName to set
     */
    public void setCnName(String cnName) {
        this.cnName.set(cnName);
    }

}
