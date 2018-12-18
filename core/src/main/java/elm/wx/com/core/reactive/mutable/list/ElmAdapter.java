package elm.wx.com.core.reactive.mutable.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import xin.banana.base.Supplier;

/**
 * Created by wangwei on 2018/12/18.
 */
public class ElmAdapter<E> implements ListBind.Adapter<E> {

    private final Supplier<IHolder<E>> elmHolderSupplier;

    public ElmAdapter(Supplier<IHolder<E>> elmHolderSupplier) {
        this.elmHolderSupplier = elmHolderSupplier;
    }

    @Override
    public void notifyDataSetChanged(ListView listView, List<E> data) {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public E getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                IHolder<E> holder;
                if (convertView == null) {
                    holder = elmHolderSupplier.get();
                    convertView = holder.onCreateView(parent);
                    convertView.setTag(holder);
                } else {
                    //noinspection unchecked
                    holder = ((IHolder<E>) convertView.getTag());
                }

                holder.onBind(position, getItem(position));
                return convertView;
            }
        });
    }

    /**
     * Created by wangwei on 2018/12/18.
     */
    public interface IHolder<E> {

        View onCreateView(ViewGroup parent);

        void onBind(int position, E data);
    }
}
