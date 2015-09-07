package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.simplegroup.khanebehdasht.R.id;

public class KhaneBehdashtListAdapter extends ArrayAdapter<KhaneBehdashtItem> {

	public KhaneBehdashtListAdapter(Activity ac, int resource,
			ArrayList<KhaneBehdashtItem> objects) {
		super(ac, resource, objects);

		this.ac = ac;
		this.resource = resource;
		this.khaneBehdashtList = objects;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = this.ac.getLayoutInflater().inflate(this.resource,
					null);

			holder = new Holder(
					(TextView) convertView.findViewById(id.textView_Name));
			convertView.setTag(holder);
		} else
			holder = (Holder) convertView.getTag();

		holder.khaneBehdashtName.setText(this.khaneBehdashtList.get(position)
				.getName());

		return convertView;
	}

	/*
	 * #########################################################################
	 * ############################ holder class ###############################
	 * #########################################################################
	 */
	private class Holder {
		public Holder(TextView khaneBehdashtName) {
			this.khaneBehdashtName = khaneBehdashtName;
		}

		public TextView khaneBehdashtName;
	}

	/*
	 * #########################################################################
	 * ############################# Attributes ################################
	 * #########################################################################
	 */
	private Activity ac;
	private int resource;
	private ArrayList<KhaneBehdashtItem> khaneBehdashtList;

}
