package top.zcwfeng.glide

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import top.zcwfeng.glide.databinding.ActivityGlideTestBinding

class GlideTest : AppCompatActivity() {
    lateinit var binding: ActivityGlideTestBinding
    val imgUrl = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_glide_test)
        binding.showImg.setOnClickListener {

            Glide.with(this)
                    .load(imgUrl)
//                    .skipMemoryCache(false)
//                    .thumbnail()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.contentImg)


            /**
             * 源码拆分
             */
            val requestManager:RequestManager = Glide.with(this)
            val requestBuilder:RequestBuilder<Drawable> = requestManager.load(imgUrl)
            val target = requestBuilder.into(binding.contentImg)
        }





    }
}

val doc1 = """
    Glide 加载图片的方式
    // 加载本地图片
    File file = new File(getExternalCacheDir() + "/image.jpg");
    Glide.with(this).load(file).into(imageView);

    // 加载应用资源
    int resource = R.drawable.image;
    Glide.with(this).load(resource).into(imageView);

    // 加载二进制流
    byte[] image = getImageBytes();
    Glide.with(this).load(image).into(imageView);

    // 加载Uri对象
    Uri imageUri = getImageUri();
    Glide.with(this).load(imageUri).into(imageView);
"""

val doc2 = """
    Glide 占位图，和一些属性实例
    Glide.with(this)
     .load(url)
     .asBitmap()
     .placeholder(R.drawable.loading)
     .error(R.drawable.error)
     .diskCacheStrategy(DiskCacheStrategy.NONE)
     .into(imageView);
     
    Glide.with(this).load(url)
     .asGif()
     .placeholder(R.drawable.loading).error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE)
     .into(imageView);
     
    Glide.with(this).load(url).placeholder(R.drawable.loading.error(R.drawable.error).diskCacheStrategy(DiskCacheStrategy.NONE)
     .override(100, 100)
     .into(imageView);
"""

