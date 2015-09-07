package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FamilyListAdapter extends ArrayAdapter<FamilyItem> {

	public FamilyListAdapter(Activity ac, int resource,
			ArrayList<FamilyItem> objects) {
		super(ac, resource, objects);

		this.ac = ac;
		this.resource = resource;
		this.list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = this.ac.getLayoutInflater().inflate(this.resource,
					null);

			holder = new Holder(
					(TextView) convertView.findViewById(id.textView_item),
					(TextView) convertView.findViewById(id.textView_subItem));
			convertView.setTag(holder);
		} else
			holder = (Holder) convertView.getTag();

		holder.fatherName.setText(this.list.get(position).getFatherNameAndFamily());
		holder.khaneBehdashtName.setText(this.list.get(position).getKhaneBehdashtName());
		
		return convertView;
	}

	/*
	 * #########################################################################
	 * ############################ holder class ###############################
	 * #########################################################################
	 */
	private class Holder {
		public Holder(TextView fatherName, TextView khaneBehdashtName) {
			this.fatherName = fatherName;
			this.khaneBehdashtName = khaneBehdashtName;
		}

		public TextView fatherName;
		public TextView khaneBehdashtName;
	}

	/*
	 * #########################################################################
	 * ############################# Attributes ################################
	 * #########################################################################
	 */
	private Activity ac;
	private int resource;
	private ArrayList<FamilyItem> list;
}
