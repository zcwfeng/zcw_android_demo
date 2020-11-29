package top.zcwfeng.common.views.databinding;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public
class CommonAdapters {

    @BindingAdapter("loadImageUrl")
    public static void loadImagUrl(ImageView imageView, String pictureUrl){
        if(!TextUtils.isEmpty(pictureUrl)){
            Glide.with(imageView.getContext())
                    .load(pictureUrl)
                    .transition(withCrossFade())
                    .into(imageView);
        }
    }
}
