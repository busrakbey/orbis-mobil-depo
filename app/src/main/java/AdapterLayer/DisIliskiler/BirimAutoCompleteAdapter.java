package AdapterLayer.DisIliskiler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.ArrayList;
import java.util.List;

import EntityLayer.Sistem.SOrgBirim;

public class BirimAutoCompleteAdapter extends ArrayAdapter<SOrgBirim> {

    Context context;
    int resource, textViewResourceId;
    List<SOrgBirim> items, tempItems, suggestions;

    public BirimAutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<SOrgBirim> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<SOrgBirim>(items); // this makes the difference.
        suggestions = new ArrayList<SOrgBirim>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row, parent, false);
        }
        SOrgBirim people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getAdi());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((SOrgBirim) resultValue).getAdi();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SOrgBirim SOrgBirim : tempItems) {
                    if (SOrgBirim.getAdi().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(SOrgBirim);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            List<SOrgBirim> filterList = (ArrayList<SOrgBirim>) results.values;
            if (results != null && results.count > 0) {
                addAll(filterList);
               /* for (SOrgBirim SOrgBirim : filterList) {
                    add(SOrgBirim);
                }*/
            }
            notifyDataSetChanged();

        }
    };
}