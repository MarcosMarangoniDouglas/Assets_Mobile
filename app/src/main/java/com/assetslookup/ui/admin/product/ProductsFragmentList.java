package com.assetslookup.ui.admin.product;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.assetslookup.R;
import com.assetslookup.data.internal.FragmentManagerHelper;
import com.assetslookup.data.internal.IFragmentManagerHelper;
import com.assetslookup.data.network.IAssetsService;
import com.assetslookup.data.network.AssetsService;
import com.assetslookup.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragmentList extends Fragment implements FragmentInteraction,
        SwipeRefreshLayout.OnRefreshListener{

    private ProgressBar progressBar;
    RecyclerView productsListRecyclerView;
    IFragmentManagerHelper fragmentManagerHelper;
    private FloatingActionButton floatingActionButton;
    List<Product> products = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    final ProductsListAdapter productsListAdapter = new ProductsListAdapter(products, this);
    IAssetsService warehouseService = AssetsService.getInstance()
            .create(IAssetsService.class);



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_admin_products_list, container, false);
        productsListRecyclerView = view.findViewById(R.id.productsList);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container_admin_products);
        swipeRefreshLayout.setOnRefreshListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.productsFragmentContainer);
        progressBar = view.findViewById(R.id.progress_loader);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                fragmentManagerHelper.attach(AddProductsFragment.class,bundle);
            }
        });

        getData();
        productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productsListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        productsListRecyclerView.setAdapter(productsListAdapter);
        return view;
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        warehouseService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products.clear();
                products.addAll(response.body());
                productsListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                //Log.d("DBG", "Data Changed");
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendMessage(Message message) {
        Product product = (Product) message.obj;
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        switch (message.what) {
            case 1: // Add/Edit Product
                fragmentManagerHelper.attach(AddProductsFragment.class,bundle);
                break;
            case 2:
                fragmentManagerHelper.attach(MoveProductsFragment.class,bundle);
                break;
        }


    }

    @Override
    public void onRefresh() {
        getData();
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000); // Delay in millis
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            onRefresh();
        }
    }
}
