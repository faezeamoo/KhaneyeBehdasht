package com.simplegroup.khanebehdasht.models;

import java.util.ArrayList;

import com.simplegroup.khanebehdasht.R.id;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PersonListAdapter extends ArrayAdapter<PersonItem> {

	public PersonListAdapter(Activity context, int resource,
			ArrayList<PersonItem> objects) {
		super(context, resource, objects);
		this.ac = context;
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
					(TextView) convertView
							.findViewById(id.textView_item),
					(TextView) convertView
							.findViewById(id.textView_subItem));

			convertView.setTag(holder);
		} else
			holder = (Holder) convertView.getTag();

		holder.nameAndFamily.setText(this.list.get(position).getNameAndFamily());
		holder.nationalCode.setText(this.list.get(position).getNationalCode());
		return convertView;
	}

	/*
	 * #########################################################################
	 * ############################ holder class ###############################
	 * #########################################################################
	 */
	private class Holder {
		public Holder(TextView nameAndFamily, TextView nationalCode) {
			this.nameAndFamily = nameAndFamily;
			this.nationalCode = nationalCode;
		}

		public TextView nameAndFamily;
		public TextView nationalCode;
	}

	/*
	 * #########################################################################
	 * ############################# Attributes ################################
	 * #########################################################################
	 */
	private Activity ac;
	private int resource;
	private ArrayList<PersonItem> list;
}
