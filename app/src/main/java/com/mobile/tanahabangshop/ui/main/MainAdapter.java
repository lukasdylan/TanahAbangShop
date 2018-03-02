package com.mobile.tanahabangshop.ui.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.tanahabangshop.R;
import com.mobile.tanahabangshop.data.model.MainMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lukas Dylan Adisurya on 24/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MenuViewHolder> {

    private final List<MainMenu> mainMenuList;
    private MainImplementer.MainAdapter listener;

    MainAdapter(List<MainMenu> mainMenuList, MainImplementer.MainAdapter listener) {
        this.mainMenuList = mainMenuList;
        this.listener = listener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MainMenu mainMenu = mainMenuList.get(position);
        holder.menuTitleTV.setText(mainMenu.getTitle());
        holder.menuTitleTV.setCompoundDrawablesWithIntrinsicBounds(null, mainMenu.getIcon(), null, null);
        holder.menuCardView.setOnClickListener(view -> listener.onSelectedMenu(position));
    }

    @Override
    public int getItemCount() {
        return mainMenuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.menuCardView)
        CardView menuCardView;
        @BindView(R.id.menuTitleTV)
        TextView menuTitleTV;

        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
