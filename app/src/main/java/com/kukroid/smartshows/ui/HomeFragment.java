package com.kukroid.smartshows.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.kukroid.smartshows.R;
import com.kukroid.smartshows.Util.Utils;
import com.kukroid.smartshows._Model.Result;
import com.kukroid.smartshows.adapter.MovieListAdapter;
import com.kukroid.smartshows.contract.HomeFragmentContract;
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
    private GridLayoutManager mGridLayoutManager;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Utils.log("onCreateView");
        view = inflater.inflate(R.layout.movie_list,container,false);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        initView();
        presenter.loadNowPlayingMovieList(1);
        return view;
    }


    private void initView() {

        movieDataList = new ArrayList<>();
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setSubtitle( R.string.now_playing);
        recyclerView = view.findViewById(R.id.recycler_view);
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext());
        mGridLayoutManager = new GridLayoutManager(getContext(),mNoOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(getContext(),this,movieDataList);
        recyclerView.setAdapter(movieListAdapter);
        relativeLayout = view.findViewById(R.id.mainLayout);
        progressBar = view.findViewById(R.id.progressBar);
        presenter = new HomeFragmentPresenter(this);
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

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onOffline() {

        showSnackBar(R.string.offline);
    }

    @Override
    public void onFailure(String localizedMessage) {

        showSnackBar(R.string.failure_message);
        Utils.log(localizedMessage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_topRated) {
            presenter.loadTopRatedMovies();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.top_rated));
            Utils.log("Top Rated ");
        }else if(id==R.id.action_mostPopular){
            presenter.loadMostPopularMovies();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.popular));
            Utils.log("Most Popular");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Utils.log("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext());
        mGridLayoutManager.setSpanCount(mNoOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);
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
    private void showSnackBar(int message){
        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
