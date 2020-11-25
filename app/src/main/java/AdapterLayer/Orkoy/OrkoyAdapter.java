package AdapterLayer.Orkoy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

import EntityLayer.Orkoy.Orkoy;

public class OrkoyAdapter extends ArrayAdapter<Orkoy> {

    Context context;

    public List<Orkoy> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[]{0x23755383, 0x22369620};
    private int[] colors2 = new int[]{0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public OrkoyAdapter(Context context, int layoutResourceID, List<Orkoy> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Orkoy dItem = (Orkoy) this.faaliyet_detay_tablo_list.get(position);
        final OrkoyAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new OrkoyAdapter.DetayBilgiOzetItemHolder();

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

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (OrkoyAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if (dItem.getBolgeMudurlukAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeMudurlukAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if (dItem.getIsletmeMudurlukAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getIsletmeMudurlukAdi().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if (dItem.getIsletmeSeflikAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getIsletmeSeflikAdi().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if (dItem.getPlanlananHibe() != null)
            drawerHolder.dorduncu_item.setText(dItem.getPlanlananHibe().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if (dItem.getPlanlananKredi() != null)
            drawerHolder.besinci_item.setText(dItem.getPlanlananKredi().toString());
        else
            drawerHolder.besinci_item.setText("");


        if (dItem.getPlanlananAdet() != null)
            drawerHolder.altinci_item.setText(dItem.getPlanlananAdet().toString());
        else
            drawerHolder.altinci_item.setText("");


        if (dItem.getPlanlananToplam() != null)
            drawerHolder.yedinci_item.setText(dItem.getPlanlananToplam().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if (dItem.getGerceklesenAdet() != null)
            drawerHolder.sekizinci_item.setText(dItem.getGerceklesenAdet().toString());
        else
            drawerHolder.sekizinci_item.setText("");


        if (dItem.getGerceklesenToplam() != null)
            drawerHolder.dokuzuncu_item.setText(dItem.getGerceklesenToplam().toString());
        else
            drawerHolder.dokuzuncu_item.setText("");

        return view;
    }


    private static class DetayBilgiOzetItemHolder {
        TextView birinci_item, ikinci_item, ucuncu_item, dorduncu_item, besinci_item, altinci_item, yedinci_item, sekizinci_item, dokuzuncu_item,
                onuncu_item, on_birinci_item, on_ikinci_item;


    }

}