package AdapterLayer.IsletmePazarlama;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

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
            drawerHolder.dorduncu_item = (TextView) view.findViewById(R.id.dorduncu_item);
            drawerHolder.besinci_item = (TextView) view.findViewById(R.id.besinci_item);
            drawerHolder.altinci_item = (TextView) view.findViewById(R.id.altinci_item);
            drawerHolder.yedinci_item = (TextView) view.findViewById(R.id.yedinci_item);
            drawerHolder.sekizinci_item = (TextView) view.findViewById(R.id.sekizinci_item);
            drawerHolder.dokuzuncu_item = (TextView) view.findViewById(R.id.dokuzuncu_item);
            drawerHolder.onuncu_item = (TextView) view.findViewById(R.id.onuncu_item);
            drawerHolder.on_birinci_item = (TextView) view.findViewById(R.id.onbirinci_item);
            drawerHolder.on_ikinci_item = (TextView) view.findViewById(R.id.onikinci_item);


            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (OdenekAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getImiktar() != null)
            drawerHolder.birinci_item.setText(dItem.getImiktar().toString());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getItutar() != null)
            drawerHolder.ikinci_item.setText(dItem.getItutar().toString());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getKmiktar() != null)
            drawerHolder.ucuncu_item.setText(dItem.getKmiktar().toString());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getKtutar() != null)
            drawerHolder.dorduncu_item.setText(dItem.getKtutar().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getSmiktar() != null)
            drawerHolder.besinci_item.setText(dItem.getSmiktar().toString());
        else
            drawerHolder.besinci_item.setText("");


       if(dItem.getStutar() != null)
            drawerHolder.altinci_item.setText(dItem.getStutar().toString());
        else
            drawerHolder.altinci_item.setText("");


        if(dItem.getTmiktar() != null)
            drawerHolder.yedinci_item.setText(dItem.getTmiktar().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if(dItem.getTtutar() != null)
            drawerHolder.sekizinci_item.setText(dItem.getTtutar().toString());
        else
            drawerHolder.yedinci_item.setText("");

        if(dItem.getYmiktar() != null)
            drawerHolder.dokuzuncu_item.setText(dItem.getYmiktar().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if(dItem.getYtutar() != null)
            drawerHolder.onuncu_item.setText(dItem.getYtutar().toString());
        else
            drawerHolder.onuncu_item.setText("");



        if(dItem.getUrunAdi() != null)
            drawerHolder.on_birinci_item.setText(dItem.getUrunAdi().toString());
        else
            drawerHolder.on_birinci_item.setText("");

        if(dItem.getProgram() != null)
            drawerHolder.on_ikinci_item.setText(dItem.getProgram().toString());
        else
            drawerHolder.on_ikinci_item.setText("");




        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item ,
                altinci_item , yedinci_item, sekizinci_item, dokuzuncu_item,
                onuncu_item , on_birinci_item, on_ikinci_item;




    }
}

