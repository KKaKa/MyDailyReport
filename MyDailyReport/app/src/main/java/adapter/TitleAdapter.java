package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.laizexin.mydailyreport.R;

import java.util.List;

import bean.ZhBaseBean;

/**
 * Created by Administrator on 2017/9/10 0010.
 */
public class TitleAdapter extends BaseAdapter {
    private Context context;
    private List<ZhBaseBean.StoriesBean> stories;

    public TitleAdapter(Context context, List<ZhBaseBean.StoriesBean> stories) {
        this.context = context;
        this.stories = stories;
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int i) {
        return stories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_title,null);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_desc.setSingleLine(true);
            holder.tv_desc.setEllipsize(TextUtils.TruncateAt.END);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        if(stories != null && stories.get(i).getImages() != null){
            Glide.with(context)
                    .load(stories.get(i).getImages().get(0))
                    .placeholder(R.mipmap.loading_4)
                    .error(R.mipmap.error)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.iv_image);
        }else{
            holder.iv_image.setImageResource(R.mipmap.error);
        }

        if(stories != null && stories.get(i).getTitle() != null){
            holder.tv_desc.setText(stories.get(i).getTitle());
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_image;
        private TextView tv_desc;
    }

    public void update(List<ZhBaseBean.StoriesBean> stories){
        this.stories = stories;
        this.notifyDataSetChanged();
    }
}
