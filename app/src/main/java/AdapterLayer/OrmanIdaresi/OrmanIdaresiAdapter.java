package AdapterLayer.OrmanIdaresi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import EntityLayer.OrmanIdaresi.OrmanIdaresi;

public class OrmanIdaresiAdapter extends ArrayAdapter<OrmanIdaresi> {

    Context context;

    public List<OrmanIdaresi> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public OrmanIdaresiAdapter(Context context, int layoutResourceID, List<OrmanIdaresi> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final OrmanIdaresi dItem = (OrmanIdaresi) this.faaliyet_detay_tablo_list.get(position);
        final OrmanIdaresiAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new OrmanIdaresiAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);


            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (OrmanIdaresiAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getOrmanlikAlan() != null)
            drawerHolder.birinci_item.setText(decimalFormat(dItem.getOrmanlikAlan()).toString());
        else
            drawerHolder.birinci_item.setText("");





       /* if(dItem.getToplam() != null)
            drawerHolder.besinci_item.setText(dItem.getToplam().toString());
        else
            drawerHolder.besinci_item.setText("");*/


       /* if(dItem.ge() != null)
            drawerHolder.altinci_item.setText(dItem.getOrmanDisiAlan().toString());
        else
            drawerHolder.altinci_item.setText("");


        if(dItem.getCikarilanAlan() != null)
            drawerHolder.yedinci_item.setText(dItem.getCikarilanAlan().toString());
        else
            drawerHolder.yedinci_item.setText("");*/



        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item , altinci_item , yedinci_item ;

    }

    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }}


