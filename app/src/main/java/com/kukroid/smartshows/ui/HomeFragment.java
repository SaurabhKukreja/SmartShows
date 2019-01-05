package com.kukroid.smartshows.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.kukroid.smartshows.R;
import com.kukroid.smartshows.Util.Utils;
import com.kukroid.smartshows._Model.Result;
import com.kukroid.smartshows.adapter.MovieListAdapter;
import com.kukroid.smartshows.contract.HomeFragmentContract;
import com.kukroid.smartshows._Model.MovieData;
import com.kukroid.smartshows.interfaces.MovieClick;
import com.kukroid.smartshows.presenter.HomeFragmentPresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeFragmentContract, MovieClick {

    View view;
    RecyclerView recyclerView;
    MovieListAdapter movieListAdapter;
    private HomeFragmentPresenter presenter;
    private List<Result> movieDataList;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_list,container,false);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        presenter = new HomeFragmentPresenter(this);
        presenter.loadNowPlayingMovieList(1);
    }

    private void initView() {
        toolbar.setSubtitle( "Now Playing");
        movieDataList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),mNoOfColumns));
        recyclerView.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(getContext(),this,movieDataList);
        recyclerView.setAdapter(movieListAdapter);

    }

    @Override
    public void filterTopRatedMovies() {

    }

    @Override
    public void filterMostPopularMovies() {

    }

    @Override
    public boolean checkInternetConnectivity() {

        if(Utils.isNetworkAvailable(getActivity())){
            return true;
        }
        return false;

    }

    @Override
    public void setDataToRecyclerView(List<Result> body) {
        movieDataList.clear();
        movieDataList.addAll(body);
        movieListAdapter.notifyDataSetChanged();
    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onOffline() {

        Toast.makeText(getContext(), "You are Offline !!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String localizedMessage) {

        Toast.makeText(getContext(), "Something went wrong, Plsease try again later !!!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "Something went wrong, Plsease try again later !!!", Toast.LENGTH_SHORT).show();
        Utils.log(localizedMessage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_topRated) {
           presenter.loadTopRatedMovies();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Top Rated");
           Utils.log("Top Rated ");
        }else if(id==R.id.action_mostPopular){
           presenter.loadMostPopularMovies();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Popular");
           Utils.log("Most Popular");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),mNoOfColumns));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onMovieClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("Movie",movieDataList.get(position));

        Fragment fragment = new MovieDetailsFragment();
        fragment.setArguments(bundle);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,fragment,"DetailsFragment");
        transaction.addToBackStack("Fragment");
        transaction.commit();
    }
}
