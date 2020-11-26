package AdapterLayer.DestekHizmetleri;

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

import EntityLayer.DestekHizmetleri.MaliHesap;

public class MaliHesapAdapter extends ArrayAdapter<MaliHesap> {

    Context context;

    public List<MaliHesap> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public MaliHesapAdapter(Context context, int layoutResourceID, List<MaliHesap> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final MaliHesap dItem = (MaliHesap) this.faaliyet_detay_tablo_list.get(position);
        final MaliHesapAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new MaliHesapAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);
            drawerHolder.dorduncu_item = (TextView) view.findViewById(R.id.dorduncu_item);
            drawerHolder.besinci_item = (TextView) view.findViewById(R.id.besinci_item);
            drawerHolder.altinci_item = (TextView) view.findViewById(R.id.altinci_item);

            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (MaliHesapAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getHesapAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getHesapAdi());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getHesapAdiEski() != null)
            drawerHolder.ikinci_item.setText(dItem.getHesapAdiEski());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getTutar() != null)
            drawerHolder.ucuncu_item.setText(dItem.getTutar().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getTutarEski() != null)
            drawerHolder.dorduncu_item.setText(decimalFormat(dItem.getTutarEski()).toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getYili() != null)
            drawerHolder.besinci_item.setText(dItem.getYili());
        else
            drawerHolder.besinci_item.setText("");


        if(dItem.getYiliEski() != null)
            drawerHolder.altinci_item.setText(decimalFormat(dItem.getYiliEski()).toString());
        else
            drawerHolder.altinci_item.setText("");






        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item , altinci_item  ;




    }

    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }
}
