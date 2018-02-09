package comn.example.apurva.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

/**
 * Created by apurva on 29-01-2018.
 */

class SectionsPageAdapter extends FragmentPagerAdapter {
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
         switch (position) {
            case 0:
                RequestsFragment requestsFragment=new RequestsFragment();
                return requestsFragment;

            case 1:
                ChatsFragment chatsFragment=new ChatsFragment();
                return chatsFragment;

            case 2:
                FriendsFragment friendsFragment=new FriendsFragment();
                return friendsFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "REQUETS";

            case 1:
                return "CHATS";

            case 2:
                return "FRIENDS";

            default:
                return null;
        }
    }
}
