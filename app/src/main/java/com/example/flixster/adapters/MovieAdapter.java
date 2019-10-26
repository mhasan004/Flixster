package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

//  PART 2 HAS 'S' COMMENTS
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>     //4.3) After making the ViewHolder class, expand MovieAdapter Class to "extends RecyclerView.Adapter<MovieAdapter.ViewHolder>". the ViewHolder is the one i made here. That's why u make the ViewHolder first!
{
    Context context;                                                                //4.1.1) This a required member variables need. constructor will give us these
    List<Movie> movies;                                                             //4.1.2) This is the data. A List of Movie objects. Constructor will define this

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {                             //4.3.1) Will inflate the layout (item_movie.xml) and will return it inside a ViewHolder
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);  //4.3.2) Takes in a context and inflates the item_movie.XML. Returns a View.
        return new ViewHolder(movieView);                                                                       //4.3.3) Wrap the View into a ViewHolder
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {        //4.4.1) Will populate data into the View through the ViewHolder
        Log.d("MovieAdapter","onBindViewHolder " + position);
        Movie movie = movies.get(position);                                         //4.4.2) Get the movie at that position. Remember 'movies' is a List of movie objects. Each element has a index. 'position' is simply the index of the List's elements
        holder.bind(movie);                                                         //4.4.3) Bind the movie data into the ViewHolder. Need to make bind() in ViewHolder class

    }
    @Override
    public int getItemCount() {
        return movies.size();
    }


    public MovieAdapter(Context context, List<Movie> movies) {                      //4.1.3E) Constructor defines the two memebr variables
        this.context = context;
        this.movies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder                         //4.2.1) Define a inner ViewHolder class. Remember: ViewHolder is a representation of the rows in a RecyclerView (which is 'item_movie.xml')
    {
        TextView tvTitle;                                                           //4.2.2) Define the view components
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout rvContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);                          //4.2.3E) Find the components from the view
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rvContainer = itemView.findViewById(R.id.rvContainer);
        }
        public void bind(final Movie movie) {                                             //4.4.4 or 4.2.4) Connect the single Movie object data to a ViewHolder
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);         //4.4.5E or 4.2.5E) using the Glide library to post an image

            rvContainer.setOnClickListener(new View.OnClickListener() {                 //S 1) setting a onclick listener on the RecyclerView so we know when user clicks it
                @Override
                public void onClick(View v) {                                               //S 1.1) Want to register a onClickListener on the whole row, not just the title TextView
                    Toast.makeText(context, "Showing '" + movie.getTitle()+"'", Toast.LENGTH_SHORT).show();   //S 2.1) Go to a new activity on a tap (create the activity now: project view -> src -> main -> right click -> new>Activity>Empty>"DetailActivity"
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("title", movie.getTitle());              //S 2.2) passing the movie title to the DetailActivity using the key "title"
                    i.putExtra("movie", Parcels.wrap(movie));                               //***S 2.2) passing each element of a movie item will get tedious. so will pass the activity the entire movie. ISSUE: java has no idea what our custom element Movie is. SOL: Use "Parceler" see the guide. go to Movie.java and add @Parcel and empty constructor: https://guides.codepath.org/android/Using-Parceler
                    context.startActivity(i);

                }
            });
        }
    }

}

//After 4.4.5E its time to go back to MainActivity.java to connect the views

