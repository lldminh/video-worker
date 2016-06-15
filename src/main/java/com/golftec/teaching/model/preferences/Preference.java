package com.golftec.teaching.model.preferences;

import java.io.Serializable;

public interface Preference extends Serializable {

    public PreferenceType getPreferenceType();

    public void setPreferred(boolean preferred);

    public Boolean isPreferred();

    public void setHidden(boolean hidden);

    public Boolean isHidden();
}
