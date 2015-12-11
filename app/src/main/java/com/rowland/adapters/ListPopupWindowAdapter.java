package com.rowland.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.objects.ListPopupMenu;

import java.util.ArrayList;

/**
 * Created by Rowland on 7/13/2015.
 */
public class ListPopupWindowAdapter extends BaseAdapter {

        // ----------------------------------------
        // Variables
        // ----------------------------------------
        private Context context;
        private ArrayList<ListPopupMenu> personItem;
        // ----------------------------------------
        // Methods
        // ----------------------------------------

        public ListPopupWindowAdapter(Context context, ArrayList<ListPopupMenu> personItem)
        {
            this.context = context;
            this.personItem = personItem;
        }

        // ----------------------------------------

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView icon;
            TextView name;
            boolean isWithPicture = (personItem.get(position).getProfilePic() != 0);

                // Small List View , no need to recycle views
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Is this the row with the p.picture
                if(isWithPicture)
                {
                    //Layout for the top row with profile picture /Avatar
                    convertView = inflater.inflate(R.layout.toolbar_overflow_item_row, parent, false);

                    icon = (ImageView) convertView .findViewById(R.id.imageProfilePic);
                    icon.setImageResource(personItem.get(position).getProfilePic());
                }
                else
                {
                    //Layout for the other layout without an images
                    convertView = inflater.inflate(R.layout.toolbar_overflow_item_row_text, parent, false);
                }


            name = (TextView) convertView.findViewById(R.id.textViewName);
            name.setText(personItem.get(position).getName());


            return convertView ;
        }


        // ----------------------------------------
        //  Implemented
        // ----------------------------------------
        @Override
        public Object getItem(int index)
        {
            return personItem.get(index);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public int getCount()
        {
            return personItem.size();
        }

    }
