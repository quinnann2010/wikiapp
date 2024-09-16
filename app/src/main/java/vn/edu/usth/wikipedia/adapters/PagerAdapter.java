package vn.edu.usth.wikipedia.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.wikipedia.fragments.ExploreFragment;
import vn.edu.usth.wikipedia.fragments.HistoryFragment;
import vn.edu.usth.wikipedia.fragments.SaveFragment;
import vn.edu.usth.wikipedia.fragments.SearchFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ExploreFragment();
            case 1:
                return new SaveFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new SearchFragment();
            default:
                return new ExploreFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
