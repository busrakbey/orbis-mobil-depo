package AdapterLayer.Oduh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> drawerItemList;
    int layoutResID;
    int NameID;
    int fromMenu = 0;


    public MenuAdapter(Context context, int layoutResourceID, List<String> listItems, int fromMenu) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.fromMenu = fromMenu;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final String item = drawerItemList.get(position);
        final altMenuItemHolder drawerHolder;
        View view = convertView;
        TextView txtTitle;
        ImageView menu_img;
        Button btn_Sync;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        drawerHolder = new altMenuItemHolder();


        view = inflater.inflate(R.layout.ana_menu_list_item, parent, false);
        txtTitle = (TextView) view.findViewById(R.id.fire_alt_menu_list_item_text_title);
        menu_img = (ImageView) view.findViewById(R.id.fire_image);

        StringBuilder sbTitle = new StringBuilder();


        sbTitle.append(item + "\n");

        if (item != null)
            txtTitle.setText(sbTitle.toString());


        if (fromMenu == 0) {  // oduh
            if (position == 0) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 1) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 2) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 3) {
                menu_img.setImageResource(R.drawable.list_icon);
            }
        }

        if (fromMenu == 1) {  // ozm
            if (position == 0) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 1) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 2) {
                menu_img.setImageResource(R.drawable.list_icon);
            } else if (position == 3) {
                menu_img.setImageResource(R.drawable.list_icon);
            }
        }
        return view;

    }


    private static class altMenuItemHolder {

        TextView txt_Title;
        TextView txt_bottomDef;
        TextView txt_RightDef;
        ImageView img_damga;
    }

}
