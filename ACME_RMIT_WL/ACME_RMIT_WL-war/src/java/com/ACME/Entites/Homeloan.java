/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ACME.Entites;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WEIQIANGLIANG
 */
@Entity
@Table(name = "HOMELOAN", catalog = "ACME_BANK", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Homeloan.findAll", query = "SELECT h FROM Homeloan h"),
    @NamedQuery(name = "Homeloan.findByCId", query = "SELECT h FROM Homeloan h WHERE h.cId = :cId"),
    @NamedQuery(name = "Homeloan.findByAccNum", query = "SELECT h FROM Homeloan h WHERE h.accNum = :accNum"),
    @NamedQuery(name = "Homeloan.findByAmountBorrowed", query = "SELECT h FROM Homeloan h WHERE h.amountBorrowed = :amountBorrowed"),
    @NamedQuery(name = "Homeloan.findByAmountRepayed", query = "SELECT h FROM Homeloan h WHERE h.amountRepayed = :amountRepayed")})
public class Homeloan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "C_ID")
    private Integer cId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccNum")
    private Integer accNum;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AmountBorrowed")
    private BigDecimal amountBorrowed;
    @Column(name = "AmountRepayed")
    private BigDecimal amountRepayed;

    public Homeloan() {
    }

    public Homeloan(Integer accNum) {
        this.accNum = accNum;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public Integer getAccNum() {
        return accNum;
    }

    public void setAccNum(Integer accNum) {
        this.accNum = accNum;
    }

    public BigDecimal getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(BigDecimal amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public BigDecimal getAmountRepayed() {
        return amountRepayed;
    }

    public void setAmountRepayed(BigDecimal amountRepayed) {
        this.amountRepayed = amountRepayed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accNum != null ? accNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Homeloan)) {
            return false;
        }
        Homeloan other = (Homeloan) object;
        if ((this.accNum == null && other.accNum != null) || (this.accNum != null && !this.accNum.equals(other.accNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ACME.Entites.Homeloan[ accNum=" + accNum + " ]";
    }
    
}
