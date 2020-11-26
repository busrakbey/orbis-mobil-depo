package AdapterLayer.Fidanlik;

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

import EntityLayer.Fidanlik.FidTohumUretim;

public class FidTohumUretimAdapter  extends ArrayAdapter<FidTohumUretim> {

    Context context;

    public List<FidTohumUretim> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public FidTohumUretimAdapter(Context context, int layoutResourceID, List<FidTohumUretim> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final FidTohumUretim dItem = (FidTohumUretim) this.faaliyet_detay_tablo_list.get(position);
        final FidTohumUretimAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new FidTohumUretimAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);
            drawerHolder.dorduncu_item = (TextView) view.findViewById(R.id.dorduncu_item);
            drawerHolder.besinci_item = (TextView) view.findViewById(R.id.besinci_item);
            drawerHolder.altinci_item = (TextView) view.findViewById(R.id.altinci_item);
            drawerHolder.yedinci_item = (TextView) view.findViewById(R.id.yedinci_item);
            drawerHolder.sekizinci_item = (TextView) view.findViewById(R.id.sekizinci_item);

            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (FidTohumUretimAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getBolgeAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getIsletmeAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getIsletmeAdi().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getSeflikAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getSeflikAdi().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getAgacTuru() != null)
            drawerHolder.dorduncu_item.setText(dItem.getAgacTuru().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getCetvelTuru() != null)
            drawerHolder.besinci_item.setText(dItem.getCetvelTuru().toString());
        else
            drawerHolder.besinci_item.setText("");


        if(dItem.getMiktar() != null)
            drawerHolder.altinci_item.setText(decimalFormat(dItem.getMiktar()).toString());
        else
            drawerHolder.altinci_item.setText("");


        if(dItem.getOrijin() != null)
            drawerHolder.yedinci_item.setText(dItem.getOrijin().toString());
        else
            drawerHolder.yedinci_item.setText("");

        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item ,     altinci_item , yedinci_item, sekizinci_item,
                onuncu_item , on_birinci_item, on_ikinci_item;




    }


    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }
}
