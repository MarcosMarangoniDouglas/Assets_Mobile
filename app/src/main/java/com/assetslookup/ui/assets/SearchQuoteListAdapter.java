package com.assetslookup.ui.assets;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.SearchQuote;
import com.assetslookup.data.internal.IFragmentInteraction;

import java.util.List;

public class SearchQuoteListAdapter extends RecyclerView.Adapter<SearchQuoteListAdapter.SearchQuoteListViewHolder> {

  private List<SearchQuote> searchQuotes;
  private Fragment fragment;

  public SearchQuoteListAdapter(List<SearchQuote> searchQuotes, Fragment fragment) {
    this.searchQuotes = searchQuotes;
    this.fragment = fragment;
  }

  class SearchQuoteListViewHolder extends RecyclerView.ViewHolder {

    TextView txtCode, txtName;

    public SearchQuoteListViewHolder(@NonNull View itemView) {
      super(itemView);
      txtCode = itemView.findViewById(R.id.txtCode);
      txtName = itemView.findViewById(R.id.txtName);
    }
  }

  @NonNull
  @Override
  public SearchQuoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
      .inflate(R.layout.fragment_search_quote_list_item, viewGroup, false);
    return new SearchQuoteListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchQuoteListViewHolder searchQuoteListViewHolder, final int i) {
    searchQuoteListViewHolder.txtCode.setText(searchQuotes.get(i).getCode());
    searchQuoteListViewHolder.txtName.setText(searchQuotes.get(i).getName());
    searchQuoteListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Message m = new Message();
        m.what = 1;
        m.obj = searchQuotes.get(i);
        ((IFragmentInteraction)fragment).sendMessage(m);
      }
    });
  }


  @Override
  public int getItemCount() {
    return this.searchQuotes.size();
  }
}
