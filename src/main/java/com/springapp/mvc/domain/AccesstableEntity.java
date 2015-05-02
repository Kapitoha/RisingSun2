package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * Created by Alex on 30.04.2015.
 */
@Entity
@Table(name = "accesstable")
public class AccesstableEntity {
    private int id;
    private int rule;
    private RulesEntity rulesByIdRules;
    private TablenameEntity tablenameByIdTable;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Rule", nullable = false, insertable = true, updatable = true)
    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccesstableEntity that = (AccesstableEntity) o;

        if (id != that.id) return false;
        if (rule != that.rule) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rule;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Rules", referencedColumnName = "ID", nullable = false)
    public RulesEntity getRulesByIdRules() {
        return rulesByIdRules;
    }

    public void setRulesByIdRules(RulesEntity rulesByIdRules) {
        this.rulesByIdRules = rulesByIdRules;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Table", referencedColumnName = "ID", nullable = false)
    public TablenameEntity getTablenameByIdTable() {
        return tablenameByIdTable;
    }

    public void setTablenameByIdTable(TablenameEntity tablenameByIdTable) {
        this.tablenameByIdTable = tablenameByIdTable;
    }
}
