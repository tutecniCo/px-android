package com.mercadopago.model;

public class IdentificationType {

    private String id;
    private String name;
    private String type;
    private Integer minLength;
    private Integer maxLength;

    public IdentificationType() {
    }

    public IdentificationType(String id, String name, String type,
                              Integer minLength, Integer maxLength) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer MinLength) {
        this.minLength = MinLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer MaxLength) {
        this.maxLength = MaxLength;
    }


    public boolean validateIdentificationNumber(Identification identification) {

        if ((identification != null) && (identification.getNumber() != null) && identification.getType() != null) {

            int len = identification.getNumber().length();
            Integer min = minLength;
            Integer max = maxLength;
            if ((min != null) && (max != null)) {
                return ((len <= max) && (len >= min));
            } else {
                return identification.validateIdentificationNumber();
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentificationType that = (IdentificationType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (minLength != null ? !minLength.equals(that.minLength) : that.minLength != null)
            return false;
        return maxLength != null ? maxLength.equals(that.maxLength) : that.maxLength == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (minLength != null ? minLength.hashCode() : 0);
        result = 31 * result + (maxLength != null ? maxLength.hashCode() : 0);
        return result;
    }
}
