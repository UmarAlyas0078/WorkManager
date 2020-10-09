package com.example.workmanager.ArticleAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workmanager.Article.Article;
import com.example.workmanager.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private Context mContext;
    private List<Article> articleList;

    public ArticleAdapter(Context mContext, List<Article> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rcv_item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvDescription.setText(article.getDescription());
        holder.tvAuthorAnd_Published_At.setText("-" + article.getAuthor() + " | " + "Published At: " + article.getPublishedAt());
        Glide.with(mContext)
                .load(article.getUrlToImage())
                .into(holder.imgViewCover);

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewCover;
        private TextView tvTitle;
        private TextView tvAuthorAnd_Published_At;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewCover = itemView.findViewById(R.id.img_View_Cover);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvAuthorAnd_Published_At = itemView.findViewById(R.id.tv_Author_And_Published_At);
            tvDescription = itemView.findViewById(R.id.tv_Description);
        }
    }
}
