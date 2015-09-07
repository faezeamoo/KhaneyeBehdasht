package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BimeListAdapter extends ArrayAdapter<BimeItem> {

	public BimeListAdapter(Activity ac, int resource,
			ArrayList<BimeItem> objects) {
		super(ac, resource, objects);

		this.ac = ac;
		this.resource = resource;
		this.bimeList = objects;
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

		holder.bimeName.setText(this.bimeList.get(position).getName());

		return convertView;
	}

	/*
	 * #########################################################################
	 * ############################ holder class ###############################
	 * #########################################################################
	 */
	private class Holder {
		public Holder(TextView bimeName) {
			this.bimeName = bimeName;
		}

		public TextView bimeName;
	}

	/*
	 * #########################################################################
	 * ############################# Attributes ################################
	 * #########################################################################
	 */
	private Activity ac;
	private int resource;
	private ArrayList<BimeItem> bimeList;

}
