package AdapterLayer.Kadastro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

import EntityLayer.Kadastro.KdmGerceklesme;

public class KdmGerceklesmeAdapter extends ArrayAdapter<KdmGerceklesme> {

    Context context;

    public List<KdmGerceklesme> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[]{0x23755383, 0x22369620};
    private int[] colors2 = new int[]{0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public KdmGerceklesmeAdapter(Context context, int layoutResourceID, List<KdmGerceklesme> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final KdmGerceklesme dItem = (KdmGerceklesme) this.faaliyet_detay_tablo_list.get(position);
        final KdmGerceklesmeAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new KdmGerceklesmeAdapter.DetayBilgiOzetItemHolder();

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


            view.setTag(drawerHolder);

        } else {
            drawerHolder = (KdmGerceklesmeAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if (dItem.getBolgeAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if (dItem.getIlkProgramAdet() != null)
            drawerHolder.ikinci_item.setText(dItem.getIlkProgramAdet().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if (dItem.getIlkGerceklesmeAdet() != null)
            drawerHolder.ucuncu_item.setText(dItem.getIlkGerceklesmeAdet().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if (dItem.getIkinciProgramAdet() != null)
            drawerHolder.dorduncu_item.setText(dItem.getIkinciProgramAdet().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if (dItem.getIkinciGerceklesmeAdet() != null)
            drawerHolder.besinci_item.setText(dItem.getIkinciGerceklesmeAdet().toString());
        else
            drawerHolder.besinci_item.setText("");


        if (dItem.getUcuncuProgramAdet() != null)
            drawerHolder.altinci_item.setText(dItem.getUcuncuProgramAdet().toString());
        else
            drawerHolder.altinci_item.setText("");


        if (dItem.getUcuncuGerceklesmeAdet() != null)
            drawerHolder.yedinci_item.setText(dItem.getUcuncuGerceklesmeAdet().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if (dItem.getProgramToplamAdet() != null)
            drawerHolder.sekizinci_item.setText(dItem.getProgramToplamAdet().toString());
        else
            drawerHolder.sekizinci_item.setText("");


        if (dItem.getGerceklesmeToplamAdet() != null)
            drawerHolder.dokuzuncu_item.setText(dItem.getGerceklesmeToplamAdet().toString());
        else
            drawerHolder.dokuzuncu_item.setText("");


        if (dItem.getYil() != null)
            drawerHolder.onuncu_item.setText(dItem.getYil().toString());
        else
            drawerHolder.onuncu_item.setText("");

        return view;
    }


    private static class DetayBilgiOzetItemHolder {
        TextView birinci_item, ikinci_item, ucuncu_item, dorduncu_item, besinci_item, altinci_item, yedinci_item, sekizinci_item, dokuzuncu_item,
                onuncu_item, on_birinci_item, on_ikinci_item;

    }


}