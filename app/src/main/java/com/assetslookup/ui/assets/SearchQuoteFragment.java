package com.assetslookup.ui.assets;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.SearchQuote;
import com.assetslookup.data.internal.APIError;
import com.assetslookup.data.internal.ErrorUtils;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.ui.shared.BaseChildNestedFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchQuoteFragment extends BaseChildNestedFragment
  implements IFragmentInteraction {

  private SearchView searchView;
  private ProgressBar searchProgressBar;

  private RecyclerView searchList;
  private SearchQuoteListAdapter searchQuoteListAdapter;
  private List<SearchQuote> searchQuotes;

  private Timer timer;
  private SearchTask searchTask;
  private String searchString;

  private Handler handler;

  public SearchQuoteFragment() {
    searchQuotes = new ArrayList<>();
    searchQuoteListAdapter = new SearchQuoteListAdapter(searchQuotes, this);
    timer = new Timer("SEARCH_TIMER");
    searchTask = new SearchTask("");
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_search_quote, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    searchProgressBar = view.findViewById(R.id.searchProgressBar);
    searchView = view.findViewById(R.id.searchView);
    searchList = view.findViewById(R.id.searchList);
    searchList.setAdapter(searchQuoteListAdapter);
    searchList.setLayoutManager(new LinearLayoutManager(getContext()));

    handler = new Handler(Looper.getMainLooper()) {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(msg.what == 1) {
          clearSearchList();
          hideProgressBar();
        } else if(msg.what == 2) {
          hideProgressBar();
          List<SearchQuote> tmpSearchQuotes = (List<SearchQuote>) msg.obj;
          populateSearchList(tmpSearchQuotes);
        } else {
          String message = (String) msg.obj;
          Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
          hideProgressBar();
        }
      }
    };

    if(getArguments() != null) {
      if(getArguments().getString("SEARCH_CODE") != null) {
        String code = getArguments().getString("SEARCH_CODE");
        searchView.setQuery(code, false);
      }
    }

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        showProgressBar();
        searchString = s;
        if(s.isEmpty()) {
          handler.sendEmptyMessage(1);
          return false;
        }
        searchTask = new SearchTask(s);
        timer.schedule(searchTask, 2000);
        return false;
      }
    });
  }

  private void showProgressBar() {
    searchProgressBar.setVisibility(View.VISIBLE);
  }

  private void hideProgressBar() {
    searchProgressBar.setVisibility(View.INVISIBLE);
  }

  private void clearSearchList() {
    searchQuotes.clear();
    searchQuoteListAdapter.notifyDataSetChanged();
  }

  private void populateSearchList(List<SearchQuote> searchQuotes) {
    this.searchQuotes.clear();
    this.searchQuotes.addAll(searchQuotes);
    searchQuoteListAdapter.notifyDataSetChanged();
  }

  @Override
  public void onDestroy() {
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    timer.cancel();
    super.onDestroy();
  }

  @Override
  public void sendMessage(Message message) {
    if (message.what == 1) {
      fragmentManagerHelper.sendMessage(AssetsCreateFragment.class, message);
      fragmentManagerHelper.goBack();
    }
  }

  class SearchTask extends TimerTask {

    private String search;

    public SearchTask(String search) {
      this.search = search;
    }

    @Override
    public void run() {
      if(!search.equals(searchString)) {
        return;
      }
      assetsService.searchQuote(search).enqueue(new Callback<List<SearchQuote>>() {
        @Override
        public void onResponse(Call<List<SearchQuote>> call, Response<List<SearchQuote>> response) {
          if(response.code() == 200) {
            Message m = new Message();
            m.what = 2;
            m.obj = response.body();
            handler.sendMessage(m);
          } else {
            APIError apiError = ErrorUtils.parseError(response);
            Message m = new Message();
            m.what = 3;
            m.obj = apiError.message();
            handler.sendMessage(m);
          }
        }

        @Override
        public void onFailure(Call<List<SearchQuote>> call, Throwable t) {
          Message m = new Message();
          m.what = 3;
          m.obj = t.getMessage();
          handler.sendMessage(m);
        }
      });
      Log.i("SEARCH", search);
    }
  }
}
