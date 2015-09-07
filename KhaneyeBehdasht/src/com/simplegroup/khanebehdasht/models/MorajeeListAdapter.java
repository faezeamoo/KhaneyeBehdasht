package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MorajeeListAdapter extends ArrayAdapter<MorajeeItem> {

	public MorajeeListAdapter(Activity ac, int resource,
			ArrayList<MorajeeItem> objects) {
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
					(TextView) convertView.findViewById(id.textView_Name));
			convertView.setTag(holder);
		} else
			holder = (Holder) convertView.getTag();

		holder.Name.setText(this.list.get(position).getDate());

		return convertView;
	}

	/*
	 * #########################################################################
	 * ############################ holder class ###############################
	 * #########################################################################
	 */
	private class Holder {
		public Holder(TextView Name) {
			this.Name = Name;
		}

		public TextView Name;
	}

	/*
	 * #########################################################################
	 * ############################# Attributes ################################
	 * #########################################################################
	 */
	private Activity ac;
	private int resource;
	private ArrayList<MorajeeItem> list;

}
