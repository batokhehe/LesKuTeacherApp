package id.co.leskuteacher.views.adapters;

import android.content.Context;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BindingAdapter {
    @android.databinding.BindingAdapter("imageStudentUrl")
    public static void setImageStudentUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        byte[] decodedString = Base64.decode(url, Base64.DEFAULT);
        Glide.with(context).load(decodedString).apply(RequestOptions.circleCropTransform()).thumbnail(0.5f).into(imageView);
    }
}
