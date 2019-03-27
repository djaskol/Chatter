import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chatter.ChatsFragemnets;
import com.example.chatter.ContactsFragment;
import com.example.chatter.GroupFragment;

public class TabsAccessAdapter extends FragmentPagerAdapter {

    public TabsAccessAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ChatsFragemnets chatsFragemnets = new ChatsFragemnets();
                return chatsFragemnets;
            case 0:
                GroupFragment groupFragment = new GroupFragment();
                return groupFragment;
            case 0:
                ContactsFragment contactsFragment = new ContactsFragment();
                return contactsFragment;
            default:
                return null,
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}