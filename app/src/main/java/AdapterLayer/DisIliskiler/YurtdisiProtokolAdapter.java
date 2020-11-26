package AdapterLayer.DisIliskiler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

import EntityLayer.DisIliskiler.YurtdisiProtokol;

public class YurtdisiProtokolAdapter extends ArrayAdapter<YurtdisiProtokol> {

    Context context;

    public List<YurtdisiProtokol> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[]{0x23755383, 0x22369620};
    private int[] colors2 = new int[]{0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public YurtdisiProtokolAdapter(Context context, int layoutResourceID, List<YurtdisiProtokol> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final YurtdisiProtokol dItem = (YurtdisiProtokol) this.faaliyet_detay_tablo_list.get(position);
        final YurtdisiProtokolAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new YurtdisiProtokolAdapter.DetayBilgiOzetItemHolder();

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

        } else {
            drawerHolder = (YurtdisiProtokolAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if (dItem.getBirimAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBirimAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if (dItem.getKisiAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getKisiAdi().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if (dItem.getKurum() != null)
            drawerHolder.ucuncu_item.setText(dItem.getKurum().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if (dItem.getGorevTuru() != null)
            drawerHolder.dorduncu_item.setText(dItem.getGorevTuru().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if (dItem.getKonu() != null)
            drawerHolder.besinci_item.setText(dItem.getKonu().toString());
        else
            drawerHolder.besinci_item.setText("");


        if (dItem.getUlkeAdi() != null)
            drawerHolder.altinci_item.setText(dItem.getUlkeAdi().toString());
        else
            drawerHolder.altinci_item.setText("");


        if (dItem.getGidisTarihi() != null)
            drawerHolder.yedinci_item.setText(dItem.getGidisTarihi().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if (dItem.getGelisTarihi() != null)
            drawerHolder.sekizinci_item.setText(dItem.getGelisTarihi().toString());
        else
            drawerHolder.sekizinci_item.setText("");




        return view;
    }


    private static class DetayBilgiOzetItemHolder {
        TextView birinci_item, ikinci_item, ucuncu_item, dorduncu_item, besinci_item,
                altinci_item, yedinci_item, sekizinci_item, dokuzuncu_item;


    }
}
