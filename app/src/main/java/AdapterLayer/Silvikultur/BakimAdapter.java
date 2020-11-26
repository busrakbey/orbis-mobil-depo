package AdapterLayer.Silvikultur;

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

import EntityLayer.SilviKultur.SilUygulama;

public class BakimAdapter  extends ArrayAdapter<SilUygulama> {

    Context context;

    public List<SilUygulama> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public BakimAdapter(Context context, int layoutResourceID, List<SilUygulama> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final SilUygulama dItem = (SilUygulama) this.faaliyet_detay_tablo_list.get(position);
        final BakimAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new BakimAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);
            drawerHolder.dorduncu_item = (TextView) view.findViewById(R.id.dorduncu_item);
            drawerHolder.besinci_item = (TextView) view.findViewById(R.id.besinci_item);
            drawerHolder.altinci_item = (TextView) view.findViewById(R.id.altinci_item);
            drawerHolder.yedinci_item = (TextView) view.findViewById(R.id.yedinci_item);
            drawerHolder.sekizinci_item = (TextView) view.findViewById(R.id.sekizinci_item);
            drawerHolder.dokuzuncu_item = (TextView) view.findViewById(R.id.dokuzuncu_item);
            drawerHolder.onuncu_item = (TextView) view.findViewById(R.id.onuncu_item);
            drawerHolder.on_birinci_item = (TextView) view.findViewById(R.id.onbirinci_item);

            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (BakimAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getBolgeMudurlukAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeMudurlukAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getIsletmeMudurlukAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getIsletmeMudurlukAdi().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getSeflikAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getSeflikAdi().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getDonemYil() != null)
            drawerHolder.dorduncu_item.setText(dItem.getDonemYil().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getDonemAy() != null)
            drawerHolder.besinci_item.setText(dItem.getDonemAy().toString());
        else
            drawerHolder.besinci_item.setText("");


        if(dItem.getGenclikBakimHarcama() != null)
            drawerHolder.altinci_item.setText(decimalFormat(dItem.getGenclikBakimHarcama()).toString());
        else
            drawerHolder.altinci_item.setText("");


        if(dItem.getGenclikBakimMiktar() != null)
            drawerHolder.yedinci_item.setText(dItem.getGenclikBakimMiktar().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if(dItem.getKulturBakimHarcama() != null)
            drawerHolder.sekizinci_item.setText(dItem.getKulturBakimHarcama().toString());
        else
            drawerHolder.sekizinci_item.setText("");


        if(dItem.getKulturBakimMiktar() != null)
            drawerHolder.dokuzuncu_item.setText(dItem.getKulturBakimMiktar().toString());
        else
            drawerHolder.dokuzuncu_item.setText("");



        if(dItem.getRehabilitasyonHarcama() != null)
            drawerHolder.onuncu_item.setText(dItem.getRehabilitasyonHarcama().toString());
        else
            drawerHolder.onuncu_item.setText("");



        if(dItem.getRehabilitasyonMiktar() != null)
            drawerHolder.on_birinci_item.setText(dItem.getRehabilitasyonMiktar().toString());
        else
            drawerHolder.on_birinci_item.setText("");



        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item ,     altinci_item , yedinci_item, sekizinci_item, dokuzuncu_item, onuncu_item, on_birinci_item;




    }


    String decimalFormat(BigDecimal sayi){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        return new DecimalFormat("#,##0.00", decimalFormatSymbols).format(new BigDecimal(String.valueOf(sayi)));
    }
}