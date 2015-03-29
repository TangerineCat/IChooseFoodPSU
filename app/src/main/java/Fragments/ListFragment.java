package Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.hackpsu.ichoosefood.R;

import java.util.ArrayList;

/**
 * Created by john on 3/29/2015.
 */
public class ListFragment extends Fragment
{
    public static final String ARG_SECTION_NUMBER = "section_number";

    static private ArrayList<String> names;
    ListView mListView;
    View rootView;

    public static ListFragment newInstance(int sectionNumber)
    {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    public ListFragment() {}

    //this creates the view that we will actually see when the application starts
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView  = inflater.inflate(R.layout.fragment_main, container, false);

        mListView = (ListView) rootView.findViewById(R.id.tasks_list_view);

        if(names == null) {
            names = new ArrayList<String>();
            names.add("You");
        }

        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            names
        );

        mListView.setAdapter(mArrayAdapter);

        return rootView;
    }

    public void addMember(String name)
    {
        names.add(name);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                names
        );

        mListView.setAdapter(mArrayAdapter);
    }

}
