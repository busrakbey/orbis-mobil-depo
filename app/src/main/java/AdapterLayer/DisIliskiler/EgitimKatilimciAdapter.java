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

import EntityLayer.DisIliskiler.EgitimKatilimci;

public class EgitimKatilimciAdapter extends ArrayAdapter<EgitimKatilimci> {

    Context context;

    public List<EgitimKatilimci> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[]{0x23755383, 0x22369620};
    private int[] colors2 = new int[]{0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public EgitimKatilimciAdapter(Context context, int layoutResourceID, List<EgitimKatilimci> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final EgitimKatilimci dItem = (EgitimKatilimci) this.faaliyet_detay_tablo_list.get(position);
        final EgitimKatilimciAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new EgitimKatilimciAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);
           
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (EgitimKatilimciAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if (dItem.getBirimAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBirimAdi().toString());
        else
            drawerHolder.birinci_item.setText("");


        if (dItem.getEgitimTanim() != null)
            drawerHolder.ikinci_item.setText(dItem.getEgitimTanim().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if (dItem.getKatilimciAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getKatilimciAdi().toString());
        else
            drawerHolder.ucuncu_item.setText("");



        return view;
    }


    private static class DetayBilgiOzetItemHolder {
        TextView birinci_item, ikinci_item, ucuncu_item;


    }
}

