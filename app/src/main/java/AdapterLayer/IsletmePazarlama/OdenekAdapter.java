package AdapterLayer.IsletmePazarlama;

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

import EntityLayer.IsletmePazarlama.Odenek;

public class OdenekAdapter extends ArrayAdapter<Odenek> {

    Context context;

    public List<Odenek> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public OdenekAdapter(Context context, int layoutResourceID, List<Odenek> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Odenek dItem = (Odenek) this.faaliyet_detay_tablo_list.get(position);
        final OdenekAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new OdenekAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);



            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (OdenekAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }



        if(dItem.getUrunAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getUrunAdi().toString());
        else
            drawerHolder.birinci_item.setText("");

        if(dItem.getProgram() != null)
            drawerHolder.ikinci_item.setText(decimalFormat(dItem.getProgram()));
        else
            drawerHolder.ikinci_item.setText("");



        //if(dItem.getKtutar() != null)
            drawerHolder.ucuncu_item.setText(decimalFormat((dItem.getKtutar() != null ? dItem.getKtutar() : new BigDecimal(0))
                    .add(dItem.getStutar() != null ? dItem.getStutar() : new BigDecimal(0))
                    .add(dItem.getTtutar() != null ? dItem.getTtutar() : new BigDecimal(0))
                    .add(dItem.getYtutar() != null ? dItem.getYtutar() : new BigDecimal(0))
                    .add(dItem.getItutar()!= null ? dItem.getItutar() : new BigDecimal(0))));
      /*  else
            drawerHolder.ucuncu_item.setText("");*/




        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item ,
                altinci_item , yedinci_item, sekizinci_item, dokuzuncu_item,
                onuncu_item , on_birinci_item, on_ikinci_item;

    }

    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }


}

