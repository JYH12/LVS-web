package com.unnet.triangle.lvs.master.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigVrrpInstanceExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public ConfigVrrpInstanceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andConfig_idIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andConfig_idIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfig_idEqualTo(Long value) {
            addCriterion("config_id =", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idNotEqualTo(Long value) {
            addCriterion("config_id <>", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idGreaterThan(Long value) {
            addCriterion("config_id >", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idGreaterThanOrEqualTo(Long value) {
            addCriterion("config_id >=", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idLessThan(Long value) {
            addCriterion("config_id <", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idLessThanOrEqualTo(Long value) {
            addCriterion("config_id <=", value, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idIn(List<Long> values) {
            addCriterion("config_id in", values, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idNotIn(List<Long> values) {
            addCriterion("config_id not in", values, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idBetween(Long value1, Long value2) {
            addCriterion("config_id between", value1, value2, "config_id");
            return (Criteria) this;
        }

        public Criteria andConfig_idNotBetween(Long value1, Long value2) {
            addCriterion("config_id not between", value1, value2, "config_id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`state` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`state` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("`state` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("`state` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("`state` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("`state` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("`state` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("`state` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("`state` like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("`state` not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("`state` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("`state` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("`state` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("`state` not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceIsNull() {
            addCriterion("network_interface is null");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceIsNotNull() {
            addCriterion("network_interface is not null");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceEqualTo(String value) {
            addCriterion("network_interface =", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceNotEqualTo(String value) {
            addCriterion("network_interface <>", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceGreaterThan(String value) {
            addCriterion("network_interface >", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceGreaterThanOrEqualTo(String value) {
            addCriterion("network_interface >=", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceLessThan(String value) {
            addCriterion("network_interface <", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceLessThanOrEqualTo(String value) {
            addCriterion("network_interface <=", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceLike(String value) {
            addCriterion("network_interface like", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceNotLike(String value) {
            addCriterion("network_interface not like", value, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceIn(List<String> values) {
            addCriterion("network_interface in", values, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceNotIn(List<String> values) {
            addCriterion("network_interface not in", values, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceBetween(String value1, String value2) {
            addCriterion("network_interface between", value1, value2, "network_interface");
            return (Criteria) this;
        }

        public Criteria andNetwork_interfaceNotBetween(String value1, String value2) {
            addCriterion("network_interface not between", value1, value2, "network_interface");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idIsNull() {
            addCriterion("virtual_router_id is null");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idIsNotNull() {
            addCriterion("virtual_router_id is not null");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idEqualTo(Integer value) {
            addCriterion("virtual_router_id =", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idNotEqualTo(Integer value) {
            addCriterion("virtual_router_id <>", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idGreaterThan(Integer value) {
            addCriterion("virtual_router_id >", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("virtual_router_id >=", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idLessThan(Integer value) {
            addCriterion("virtual_router_id <", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idLessThanOrEqualTo(Integer value) {
            addCriterion("virtual_router_id <=", value, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idIn(List<Integer> values) {
            addCriterion("virtual_router_id in", values, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idNotIn(List<Integer> values) {
            addCriterion("virtual_router_id not in", values, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idBetween(Integer value1, Integer value2) {
            addCriterion("virtual_router_id between", value1, value2, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andVirtual_router_idNotBetween(Integer value1, Integer value2) {
            addCriterion("virtual_router_id not between", value1, value2, "virtual_router_id");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andAdvertIsNull() {
            addCriterion("advert is null");
            return (Criteria) this;
        }

        public Criteria andAdvertIsNotNull() {
            addCriterion("advert is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertEqualTo(Integer value) {
            addCriterion("advert =", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertNotEqualTo(Integer value) {
            addCriterion("advert <>", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertGreaterThan(Integer value) {
            addCriterion("advert >", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertGreaterThanOrEqualTo(Integer value) {
            addCriterion("advert >=", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertLessThan(Integer value) {
            addCriterion("advert <", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertLessThanOrEqualTo(Integer value) {
            addCriterion("advert <=", value, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertIn(List<Integer> values) {
            addCriterion("advert in", values, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertNotIn(List<Integer> values) {
            addCriterion("advert not in", values, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertBetween(Integer value1, Integer value2) {
            addCriterion("advert between", value1, value2, "advert");
            return (Criteria) this;
        }

        public Criteria andAdvertNotBetween(Integer value1, Integer value2) {
            addCriterion("advert not between", value1, value2, "advert");
            return (Criteria) this;
        }

        public Criteria andNo_preemptIsNull() {
            addCriterion("no_preempt is null");
            return (Criteria) this;
        }

        public Criteria andNo_preemptIsNotNull() {
            addCriterion("no_preempt is not null");
            return (Criteria) this;
        }

        public Criteria andNo_preemptEqualTo(String value) {
            addCriterion("no_preempt =", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptNotEqualTo(String value) {
            addCriterion("no_preempt <>", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptGreaterThan(String value) {
            addCriterion("no_preempt >", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptGreaterThanOrEqualTo(String value) {
            addCriterion("no_preempt >=", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptLessThan(String value) {
            addCriterion("no_preempt <", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptLessThanOrEqualTo(String value) {
            addCriterion("no_preempt <=", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptLike(String value) {
            addCriterion("no_preempt like", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptNotLike(String value) {
            addCriterion("no_preempt not like", value, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptIn(List<String> values) {
            addCriterion("no_preempt in", values, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptNotIn(List<String> values) {
            addCriterion("no_preempt not in", values, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptBetween(String value1, String value2) {
            addCriterion("no_preempt between", value1, value2, "no_preempt");
            return (Criteria) this;
        }

        public Criteria andNo_preemptNotBetween(String value1, String value2) {
            addCriterion("no_preempt not between", value1, value2, "no_preempt");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_vrrp_instance
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}