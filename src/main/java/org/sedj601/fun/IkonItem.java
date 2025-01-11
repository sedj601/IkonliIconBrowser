package org.sedj601.fun;

import java.util.Objects;

public class IkonItem
{
    int index;
    String name;
    String enumName;
    String provider;

    public IkonItem(int index, String name, String enumName, String provider)
    {
        this.index = index;
        this.name = name;
        this.enumName = enumName;
        this.provider = provider;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "IkonItem{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", enumName='" + enumName + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IkonItem ikonItem = (IkonItem) o;
        return index == ikonItem.index && Objects.equals(name, ikonItem.name) && Objects.equals(enumName, ikonItem.enumName) && Objects.equals(provider, ikonItem.provider);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(index, name, enumName, provider);
    }
}
