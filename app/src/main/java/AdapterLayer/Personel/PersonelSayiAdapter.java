package AdapterLayer.Personel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.konumsal.orbisozetmobil.R;

import java.util.List;

import EntityLayer.Personel.PersonelSayi;

public class PersonelSayiAdapter extends ArrayAdapter<PersonelSayi> {

    Context context;

    public List<PersonelSayi> faaliyet_detay_tablo_list;
    int layoutResID;
    int NameID;
    private int[] colors = new int[] { 0x23755383, 0x22369620};
    private int[] colors2 = new int[] { 0x93755383, 0x10369620};
    View view;
    int mes_or_surutme_or_sevk;

    public PersonelSayiAdapter(Context context, int layoutResourceID, List<PersonelSayi> listItems)
    {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.faaliyet_detay_tablo_list = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final PersonelSayi dItem = (PersonelSayi) this.faaliyet_detay_tablo_list.get(position);
        final PersonelSayiAdapter.DetayBilgiOzetItemHolder drawerHolder;
        view = convertView;


        if (view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new PersonelSayiAdapter.DetayBilgiOzetItemHolder();

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
            drawerHolder = (PersonelSayiAdapter.DetayBilgiOzetItemHolder) view.getTag();
        }


        if(dItem.getBolgeAdi() != null)
            drawerHolder.birinci_item.setText(dItem.getBolgeAdi());
        else
            drawerHolder.birinci_item.setText("");


        if(dItem.getIsletmeAdi() != null)
            drawerHolder.ikinci_item.setText(dItem.getIsletmeAdi());
        else
            drawerHolder.ikinci_item.setText("");


        if(dItem.getSeflikAdi() != null)
            drawerHolder.ucuncu_item.setText(dItem.getSeflikAdi());
        else
            drawerHolder.ucuncu_item.setText("");


        if(dItem.getYil() != null)
            drawerHolder.dorduncu_item.setText(dItem.getYil().toString());
        else
            drawerHolder.dorduncu_item.setText("");


        if(dItem.getMemur4a() != null)
            drawerHolder.besinci_item.setText(dItem.getMemur4a().toString());
        else
            drawerHolder.besinci_item.setText("");


        if(dItem.getMemur4b() != null)
            drawerHolder.altinci_item.setText(dItem.getMemur4b().toString());
        else
            drawerHolder.altinci_item.setText("");


          if(dItem.getIsciDaimi() != null)
            drawerHolder.yedinci_item.setText(dItem.getIsciDaimi().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if(dItem.getIsciGecici() != null)
            drawerHolder.yedinci_item.setText(dItem.getIsciGecici().toString());
        else
            drawerHolder.yedinci_item.setText("");



        if(dItem.getIsciToplam() != null)
            drawerHolder.yedinci_item.setText(dItem.getIsciToplam().toString());
        else
            drawerHolder.yedinci_item.setText("");


        if(dItem.getMemurToplam() != null)
            drawerHolder.yedinci_item.setText(dItem.getMemurToplam().toString());
        else
            drawerHolder.yedinci_item.setText("");




        return view;
    }



    private static class DetayBilgiOzetItemHolder
    {
        TextView birinci_item , ikinci_item , ucuncu_item , dorduncu_item , besinci_item , altinci_item , yedinci_item ;




    }
}
