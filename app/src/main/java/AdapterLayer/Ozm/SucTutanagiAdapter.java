package AdapterLayer.Ozm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

import EntityLayer.Ozm.SucTutanagi;

public class SucTutanagiAdapter  extends ArrayAdapter<SucTutanagi> {

    Context context;

    public List<SucTutanagi> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public SucTutanagiAdapter(Context context, int layoutResourceID, List<SucTutanagi> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final SucTutanagi dItem = (SucTutanagi) this.faaliyet_detay_tablo_list.get(position);
        final SucTutanagiAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new SucTutanagiAdapter.DetayBilgiOzetItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.birinci_item = (TextView) view.findViewById(R.id.birinci_item);
            drawerHolder.ikinci_item = (TextView) view.findViewById(R.id.ikinci_item);
            drawerHolder.ucuncu_item = (TextView) view.findViewById(R.id.ucuncu_item);
            drawerHolder.dorduncu_item = (TextView) view.findViewById(R.id.dorduncu_item);
            drawerHolder.besinci_item = (TextView) view.findViewById(R.id.besinci_item);
            drawerHolder.altinci_item = (TextView) view.findViewById(R.id.altinci_item);
            drawerHolder.yedinci_item = (TextView) view.findViewById(R.id.yedinci_item);

            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (SucTutanagiAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getBolgeMudurlukAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeMudurlukAdi());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getIsletmeMudurlukAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getIsletmeMudurlukAdi());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getSeflikAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getSeflikAdi());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getTarih() != null)
            drawerHolder.dorduncu_item.setText(dItem.getTarih().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getSucSayisi() != null)
            drawerHolder.besinci_item.setText(dItem.getSucSayisi().toString());
        else
            drawerHolder.besinci_item.setText("");


        /*if(dItem.getOrmanDisiAlan() != null)
            drawerHolder.altinci_item.setText(dItem.getOrmanDisiAlan().toString());
        else
            drawerHolder.altinci_item.setText("");


        if(dItem.getCikarilanAlan() != null)
            drawerHolder.yedinci_item.setText(dItem.getCikarilanAlan().toString());
        else
            drawerHolder.yedinci_item.setText("");*/



        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item , altinci_item , yedinci_item ;




    }
}


