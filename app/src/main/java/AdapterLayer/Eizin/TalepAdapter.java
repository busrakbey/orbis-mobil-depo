package AdapterLayer.Eizin;

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

import EntityLayer.Eizin.Talep;

public class TalepAdapter extends ArrayAdapter<Talep> {

    Context context;

    public List<Talep> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[]{0x23755383, 0x22369620};
    private int[] colors2 = new int[]{0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public TalepAdapter(Context context, int layoutResourceID, List<Talep> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Talep dItem = (Talep) this.faaliyet_detay_tablo_list.get(position);
        final TalepAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new TalepAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (TalepAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if (dItem.getKanunMaddesi() != null)
            drawerHolder.birinci_item.setText(dItem.getKanunMaddesi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if (dItem.getSayi() != null)
            drawerHolder.ikinci_item.setText(dItem.getSayi().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if (dItem.getAlanHa() != null)
            drawerHolder.ucuncu_item.setText(decimalFormat(dItem.getAlanHa()).toString());
        else
            drawerHolder.ucuncu_item.setText("");



        return view;
    }


    private static class DetayBilgiOzetItemHolder {
        TextView birinci_item, ikinci_item, ucuncu_item;


    }

    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }

}
