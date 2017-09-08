package com.example.shalhan4.sqmint.ui.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.job_detail.JobDetailActivity;
import com.example.shalhan4.sqmint.ui.user.add_user.AddUserActivity;
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
    private FloatingActionButton mFab;

    public UserFragment() {
        this.mUserPresenter = new UserPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user, container, false);
        //Setup floating button and list voew
        setUp(v);

        mUserPresenter.setUserContext(getActivity());
        mUserPresenter.startApi();

        this.mFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAddUser();
            }
        });

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mUserPresenter.getUserDetail(position);
                User mUserList = (User) mUserAdapter.getItem(position);
                Log.i("USER LIST BY ID ====> ", mUserList.getId() + "");


                mUserPresenter.getUserDetail(mUserList.getId());
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

    @Override
    public void openAddUser()
    {
        Intent intent = new Intent(getActivity(), AddUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void setUserListAdapter(List<User> mUserList) {
        this.mUserAdapter = new UserListAdapter(getActivity(), mUserList);
        this.mListView.setAdapter(mUserAdapter);
    }

    private void setUp(View v)
    {
        //Floating Button
        this.mFab = (FloatingActionButton) v.findViewById(R.id.fb_add_user);
        //List View
        this.mListView = (ListView) v.findViewById(R.id.user_list);
    }



}
