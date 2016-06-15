package com.golftec.teaching.model.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalPreferenceCompositeModel implements Serializable{
	
	private List<Preference> preferences;
	
	public LocalPreferenceCompositeModel() {
		preferences = new ArrayList<Preference>();
	}
	
	public void setPreferences(Preference preference) {
		preferences.add(preference);
	}
	
	public List<Preference> getPreferrences() {
		return preferences;
	}

}
