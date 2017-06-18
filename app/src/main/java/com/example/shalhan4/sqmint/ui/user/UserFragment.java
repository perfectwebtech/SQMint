package com.example.shalhan4.sqmint.ui.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;
import com.example.shalhan4.sqmint.ui.user.user_detail.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserView {
    private ListView mListView;
    private List<User> mUserList;
    private UserListAdapter mUserAdapter;
    private UserPresenter mUserPresenter;

    public UserFragment() {
        this.mUserPresenter = new UserPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user, container, false);

        this.mListView = (ListView) v.findViewById(R.id.user_list);

        this.mUserList = new ArrayList<>();
        this.mUserList.add(new User(1, "Shalhan Radifan", "16/07/2018", "12:05:11"));
        this.mUserList.add(new User(2, "M ALif", "12/06/2017", "11:05:11"));

        this.mUserAdapter = new UserListAdapter(getActivity(), mUserList);
        this.mListView.setAdapter(mUserAdapter);

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mUserPresenter.getUserDetail(position);
            }
        });

        return v;
    }

    @Override
    public void openUserDetail(int id)
    {
        Intent intent = new Intent(getActivity(), UserDetailActivity.class);
        startActivity(intent);
    }

}
