package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ui.kason_zhang.sharefiles.R;

import java.util.List;

/**
 * Created by kason_zhang on 8/31/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private List<String> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public interface RecyclerViewOnClickListener{
        public void onClickListener(View view,int postion);
        public void onLongClickListener(View view,int postion);
    }
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    public void setmRecyclerViewOnClickListener(RecyclerViewOnClickListener
                                                        mRecyclerViewOnClickListener) {
        this.mRecyclerViewOnClickListener = mRecyclerViewOnClickListener;
    }
    public RecyclerViewAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = mLayoutInflater.inflate(R.layout.item_files_recyclerview,
                parent, false);
        MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.filename_text.setText(mList.get(position));
        if(mLayoutInflater!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mRecyclerViewOnClickListener.onClickListener(v,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mRecyclerViewOnClickListener.onLongClickListener(v,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView filename_text;
        public MyHolder(View itemView) {
            super(itemView);
            filename_text = (TextView)itemView.findViewById(R.id.filename_text);
        }
    }
}
