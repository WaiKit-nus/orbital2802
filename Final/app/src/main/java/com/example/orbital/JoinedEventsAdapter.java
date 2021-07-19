package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class JoinedEventsAdapter extends FragmentStateAdapter {
    public JoinedEventsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 1:
                return new pasteventfragment();
        }
        return new upcomingeventfragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
