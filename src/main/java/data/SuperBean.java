/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.math.BigDecimal;

/**
 *
 * @author jpcasati
 */
public class SuperBean {
    
    private int id;
    
    private String fullName;
    
    private String superName;
    
    private BigDecimal wage;

    public SuperBean(int id, String fullName, String superName, BigDecimal wage) {
        this.id = id;
        this.fullName = fullName;
        this.superName = superName;
        this.wage = wage;
    }
    
    public SuperBean() {
        this.id = -1;
        this.fullName = "";
        this.superName = "";
        this.wage = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "SuperBean{" + "id=" + id + ", fullName=" + fullName + ", superName=" + superName + ", wage=" + wage + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }
    
}
